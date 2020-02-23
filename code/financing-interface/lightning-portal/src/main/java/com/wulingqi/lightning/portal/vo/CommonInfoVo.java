package com.wulingqi.lightning.portal.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("公共信息返回实体")
public class CommonInfoVo {

	@ApiModelProperty(value = "USDT兑换TBCC的最小数量(值为0时，不限制)")
	private String usdtExchangeTbccLowerLimit;
	
	@ApiModelProperty(value = "USDT兑换TBCC的整数倍数(值为0时，不限制)")
	private String usdtExchangeTbccMultiple;
	
	@ApiModelProperty(value = "USDT兑换TBCC的比例")
	private String usdtExchangeTbccRate;
	
	@ApiModelProperty(value = "EVEpay兑换TBCC的最小数量(值为0时，不限制)")
	private String evepayExchangeTbccLowerLimit;
	
	@ApiModelProperty(value = "EVEpay兑换TBCC的整数倍数(值为0时，不限制)")
	private String evepayExchangeTbccMultiple;
	
	@ApiModelProperty(value = "EVEpay兑换TBCC的比例")
	private String evepayExchangeTbccRate;
	
	@ApiModelProperty(value = "允许转账的最小数量")
	private String transferLowerLimit;
	
	@ApiModelProperty(value = "允许转账的整数倍数")
	private String transferMultiple;
	
	@ApiModelProperty(value = "允许提现的最小数量")
	private String withdrawLowerLimit;
	
	@ApiModelProperty(value = "允许提现的整数倍数")
	private String withdrawMultiple;
	
	@ApiModelProperty(value = "提币手续费的百分比")
	private String withdrawPoundage;
	
	@ApiModelProperty(value = "分享连接地址")
	private String sharePageUrl;
	
	@ApiModelProperty(value = "邀请好友奖励")
	private String inviteReward;
	
	@ApiModelProperty(value = "客服电话")
	private String customerTelephone;
	
	@ApiModelProperty(value = "公司网址")
	private String companyWebSite;
	
	@ApiModelProperty(value = "交易手续费")
	private String digitalCurrencyTradingPoundage;
	
	@ApiModelProperty(value = "货币交易最低单价")
	private String currencyTradeMinPrice;
	
	@ApiModelProperty(value = "货币交易最高单价")
	private String currencyTradeMaxPrice;
	
}
