package com.wulingqi.lightning.portal.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("线下充值参数")
public class RechargeDto {
	
	@ApiModelProperty(value = "收款信息id")
	private Long collectionId;
	
	@ApiModelProperty(value = "充值金额")
	private Double value;
	
	@ApiModelProperty(value = "支付账户名")
	private String paymentAccountName;
	
	@ApiModelProperty(value = "支付银行名称")
	private String paymentBankName;
	
	@ApiModelProperty(value = "支付银行卡号")
	private String paymentBankCardNo;
	
	@ApiModelProperty(value = "支付截图")
	private String paymentScreenshot;
	
}
