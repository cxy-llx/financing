package com.wulingqi.lightning.portal.component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.wulingqi.lightning.mapper.MerchantMapper;
import com.wulingqi.lightning.mapper.OrderCallbackMapper;
import com.wulingqi.lightning.mapper.OrderMapper;
import com.wulingqi.lightning.model.Merchant;
import com.wulingqi.lightning.model.Order;
import com.wulingqi.lightning.model.OrderCallback;
import com.wulingqi.lightning.portal.mapper.PortalOrderMapper;
import com.wulingqi.lightning.portal.service.RedisService;
import com.wulingqi.lightning.utils.LightningConstant;
import com.wulingqi.lightning.utils.MD5Util;

import lombok.extern.slf4j.Slf4j;

/**
 * redis key过期监听，主要用于监听订单过期后调用第三方接口
 */
@Component
@Slf4j
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
	
	@Autowired
	private PortalOrderMapper portalOrderMapper;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private OrderCallbackMapper orderCallbackMapper;
	
	@Autowired
	private MerchantMapper merchantMapper;
	
	@Autowired
    private RedisService redisService;
	
	@Autowired
	private RestTemplate restTemplate;

	public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
		super(listenerContainer);
	}

	/**
	 * 针对redis数据失效事件，进行数据处理
	 * 
	 * @param message
	 * @param pattern
	 */
	@Override
	public void onMessage(Message message, byte[] pattern) {
		// 用户做自己的业务处理即可,注意message.toString()可以获取失效的key
		String expiredKey = message.toString();
		if (expiredKey.startsWith("orderNo:")) {
			// 如果是order:开头的key，进行处理
			String orderNo = expiredKey.substring(8); // 去掉orderNo
			log.info("orderNo:" + orderNo);
			Order order = portalOrderMapper.selectOrderByOrderNo(orderNo);
			if(LightningConstant.CALLBACK_STATUS_NO.equals(order.getCallbackStatus())) {
				OrderCallback callback = portalOrderMapper.selectOrderCallbackByOrderId(order.getId());
				Merchant merchant = merchantMapper.selectByPrimaryKey(order.getMerchantId());
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//调用回调接口
				Map<String, String> request = new HashMap<>();
				String payment = sdf.format(order.getPayTime());
				request.put("code", "0000");
				request.put("mchorderno", order.getMerchantOrderNo());
				request.put("system_orderno", order.getOrderNo());
				request.put("price", order.getPayAmount()); //付款金额
				request.put("realprice", order.getAmount()); //实付金额
				request.put("mchno", merchant.getMchId());
				request.put("payment", payment);
				request.put("remark", callback.getRemark());
				
				StringBuilder content = new StringBuilder();
				content.append("code=").append("0000").append("&mchno=").append(merchant.getMchId());
				content.append("&mchorderno=").append(order.getMerchantOrderNo()).append("&payment=").append(payment);
				content.append("&price=").append(order.getPayAmount()).append("&realprice=").append(order.getAmount());
				content.append("&remark=").append(callback.getRemark()).append("&system_orderno=").append(order.getOrderNo());
				content.append("&key=").append(merchant.getMchKey());
				request.put("sign", MD5Util.MD5Encode(content.toString(), "GBK"));
				
				//code=0000&mchno=M201801010001&mchorderno=K20190629201431197826&payment=2019-07-23 15:52:00&
						//price=11.00&realprice=11.00&remark=123456&system_orderno=1561816469455&userid=1&
						//key=12345678901234567890123456789012
				
				ResponseEntity<String> response = restTemplate.postForEntity(callback.getUrl(), request, String.class);
				if("success".equals(response.getBody())) {
					order.setCallbackStatus(LightningConstant.CALLBACK_STATUS_YES); //回调状态: 1->已回调
					orderMapper.updateByPrimaryKey(order);
				} else {
					//如果回调接口调用返回失败，根据当前回调次数设置redis key过期时间。过期时间到期重新发起回调
					String key = LightningConstant.REDIS_KEY_ORDER_NO + order.getOrderNo();
					redisService.set(key, String.valueOf(order.getId()));
					
					//最多只调用5次
					if(callback.getCount() == 5) {
						return;
					}
					
					if(callback.getCount() == 0) {
						//设置过期时间为10s
						redisService.expire(key, 10);
					} else if(callback.getCount() == 1) {
						//设置过期时间为10min
						redisService.expire(key, 600);
					} else if(callback.getCount() == 2) {
						//设置过期时间为15min
						redisService.expire(key, 900);
					} else if(callback.getCount() == 3) {
						//设置过期时间为1h
						redisService.expire(key, 3600);
					} else {
						//设置过期时间为6h
						redisService.expire(key, 21600);
					}
				}
				
				callback.setCount(callback.getCount() + 1);
				callback.setCallbackTime(new Date());
				orderCallbackMapper.updateByPrimaryKey(callback);
				
			}
			
		}
	}

}
