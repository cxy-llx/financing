package com.wulingqi.lightning.portal.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("商户提现申请参数")
@Data
public class WithdrawApplyDto {

	@ApiModelProperty(value = "商户主键ID")
	private Long merchantId;
	
	@ApiModelProperty(value = "提现金额")
	private Double value;
	
	@ApiModelProperty(value = "银行名称")
	private String bankName;
	
	@ApiModelProperty(value = "银行账户名")
	private String bankAccount;
	
	@ApiModelProperty(value = "银行卡卡号")
	private String bankCardNo;
	
}
