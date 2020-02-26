package com.wulingqi.lightning.portal.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.wulingqi.lightning.api.CommonResult;
import com.wulingqi.lightning.mapper.OfflineRechargeRecordMapper;
import com.wulingqi.lightning.mapper.OrderMapper;
import com.wulingqi.lightning.mapper.PlatformCollectionInfoMapper;
import com.wulingqi.lightning.model.Member;
import com.wulingqi.lightning.model.OfflineRechargeRecord;
import com.wulingqi.lightning.model.Order;
import com.wulingqi.lightning.model.PlatformCollectionInfo;
import com.wulingqi.lightning.portal.dto.AutomaticPayDto;
import com.wulingqi.lightning.portal.dto.CollectionInfoDto;
import com.wulingqi.lightning.portal.dto.ManualPayDto;
import com.wulingqi.lightning.portal.dto.PageableDto;
import com.wulingqi.lightning.portal.dto.RechargeDto;
import com.wulingqi.lightning.portal.mapper.PortalMapper;
import com.wulingqi.lightning.portal.mapper.PortalMemberMapper;
import com.wulingqi.lightning.portal.service.CommonService;
import com.wulingqi.lightning.portal.service.FinanceService;
import com.wulingqi.lightning.portal.service.MemberService;
import com.wulingqi.lightning.portal.vo.CollectionInfoVo;
import com.wulingqi.lightning.portal.vo.IntegrationDetailVo;
import com.wulingqi.lightning.portal.vo.RechargeDetailVo;
import com.wulingqi.lightning.utils.LightningConstant;
import com.wulingqi.lightning.utils.StringUtils;

@Service
public class FinanceServiceImpl implements FinanceService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceServiceImpl.class);
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PortalMemberMapper portalMemberMapper;
	
	@Autowired
	private PortalMapper portalMapper;
	
	@Autowired
	private PlatformCollectionInfoMapper platformCollectionInfoMapper;
	
	@Autowired
	private OfflineRechargeRecordMapper offlineRechargeRecordMapper;
	
	@Autowired
	private OrderMapper orderMapper;
	
	/**
	 * 获取公司收款信息
	 */
	@Override
	public CommonResult<CollectionInfoVo> getCollectionInfo(CollectionInfoDto requestDto) {
		List<PlatformCollectionInfo> list = portalMapper.selectPlatformCollectionInfoByCollectionType(requestDto.getRechargeType());
		
		if(list.isEmpty()) {
			return CommonResult.failed("未获取到充值方式");
		}
		
		PlatformCollectionInfo bankInfo = list.get(0);
		for(PlatformCollectionInfo info : list) {
			
			if(LightningConstant.DEFAULT_FLAG_YES.equals(info.getDefaultFlag())) {
				bankInfo = info;
				break;
			}
		}
		
		CollectionInfoVo bankInfoVo = new CollectionInfoVo();
		BeanUtils.copyProperties(bankInfo, bankInfoVo);
		bankInfoVo.setCollectionId(String.valueOf(bankInfo.getId()));
		
		return CommonResult.success(bankInfoVo);
	}

	/**
	 * 线下充值
	 */
	@Override
	public CommonResult<String> recharge(RechargeDto requestDto) {
		
		Long memberId = memberService.getCurrentMember().getId();
		
		if(requestDto.getCollectionId() == null
				|| StringUtils.isEmpty(requestDto.getPaymentScreenshot())
				|| requestDto.getValue() == null
				|| requestDto.getValue() <= 0) {
			return CommonResult.failed(LightningConstant.SERVER_ERROR);
		}
		
		PlatformCollectionInfo collectionInfo = platformCollectionInfoMapper.selectByPrimaryKey(requestDto.getCollectionId());

		if(collectionInfo == null) {
			return CommonResult.failed(LightningConstant.SERVER_ERROR);
		}
		
		//如果收款方式为银行类型，判断支付银行信息为必填
		if(LightningConstant.COLLECTION_TYPE_BANK.equals(collectionInfo.getCollectionType())
				&& (StringUtils.isEmpty(requestDto.getPaymentBankName())
						|| StringUtils.isEmpty(requestDto.getPaymentAccountName())
						|| StringUtils.isEmpty(requestDto.getPaymentBankCardNo()))
				) {
			return CommonResult.failed(LightningConstant.SERVER_ERROR);
		}
		
		//写入线下充值记录表
		OfflineRechargeRecord rechargeRecord = new OfflineRechargeRecord();
		rechargeRecord.setCollectionId(collectionInfo.getId());
		rechargeRecord.setMemberId(memberId);
		rechargeRecord.setRechargeType(collectionInfo.getCollectionType());
		
		BigDecimal b = new BigDecimal(requestDto.getValue());
		rechargeRecord.setValue(b.setScale(2, BigDecimal.ROUND_DOWN).toPlainString());
		
		if(LightningConstant.COLLECTION_TYPE_BANK.equals(collectionInfo.getCollectionType())) {
			rechargeRecord.setPaymentAccountName(requestDto.getPaymentAccountName());
			rechargeRecord.setPaymentBankName(requestDto.getPaymentBankName());
			rechargeRecord.setPaymentBankCardNo(requestDto.getPaymentBankCardNo());
		}
		rechargeRecord.setPaymentScreenshot(requestDto.getPaymentScreenshot());
		rechargeRecord.setCreateTime(new Date());
		
		offlineRechargeRecordMapper.insert(rechargeRecord);
		
		return CommonResult.success(null, "提交成功");
	}

	/**
	 * 获取充值明细
	 */
	@Override
	public CommonResult<RechargeDetailVo> getRechargeDetail(PageableDto requestDto) {
		Long memberId = memberService.getCurrentMember().getId();
		
		RechargeDetailVo result = new RechargeDetailVo();
		result.setTotal(portalMemberMapper.selectMemberTotalRecharge(memberId).toPlainString());
		result.setSucceed(portalMemberMapper.selectMemberRechargeByRechargeStatus(memberId, LightningConstant.RECHARGE_STATUS_SUCCEED).toPlainString());
		result.setAudit(portalMemberMapper.selectMemberRechargeByRechargeStatus(memberId, LightningConstant.RECHARGE_STATUS_RECHARGE).toPlainString());
		
		PageHelper.startPage(requestDto.getPage(), requestDto.getLimit());
		
		result.setList(portalMemberMapper.selectMemberRechargeDetail(memberId));
		
		return CommonResult.success(result);
	}

	/**
	 * 获取积分明细
	 */
	@Override
	public CommonResult<IntegrationDetailVo> getIntegrationDetail(PageableDto requestDto) {
		
		Long memberId = memberService.getCurrentMember().getId();
		Member member = memberService.getMemberById(memberId);
		
		IntegrationDetailVo result = new IntegrationDetailVo();
		result.setIntegration(member.getIntegration());
		result.setFreezeIntegration(member.getFreezeIntegration());
		result.setIncome(portalMemberMapper.selectMemberTotalIncome(memberId).toPlainString());
		result.setExpend(portalMemberMapper.selectMemberTotalExpend(memberId).toPlainString());
		
		return CommonResult.success(result);
	}

	/**
	 * 订单支付-手动
	 */
	@Override
	public CommonResult<String> manualPay(ManualPayDto requestDto) {
		
		Long orderId = requestDto.getOrderId();
		Long memberId = memberService.getCurrentMember().getId();
		
		if(orderId == null) {
			return CommonResult.failed(LightningConstant.SERVER_ERROR);
		}
		
		//判断订单信息是否与会员信息匹配
		Order order = orderMapper.selectByPrimaryKey(orderId);
		if(order == null || !memberId.equals(order.getMemberId())) {
			return CommonResult.failed(LightningConstant.SERVER_ERROR);
		}
		
		//判断订单状态是否为已支付状态
		if(LightningConstant.ORDER_STATUS_PAID.equals(order.getOrderStatus())) {
			return CommonResult.failed("订单已支付，请勿重复支付");
		}
		
		
		
		return CommonResult.success(null, "支付成功");
	}

	/**
	 * 订单支付-自动
	 */
	@Override
	public CommonResult<String> automaticPay(AutomaticPayDto requestDto) {
		return null;
	}

}
