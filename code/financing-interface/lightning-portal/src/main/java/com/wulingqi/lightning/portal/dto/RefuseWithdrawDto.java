package com.wulingqi.lightning.portal.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("拒绝商户提现参数")
@Data
public class RefuseWithdrawDto {

	@ApiModelProperty(value = "提现ID")
	private Long withdrawId;
	
	@ApiModelProperty(value = "失败原因")
	private String failedReason;
	
}
