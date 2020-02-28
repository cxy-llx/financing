package com.wulingqi.lightning.portal.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("审核失败接口参数")
public class RefuseRechargeDto {

	@ApiModelProperty("充值记录id")
	@NotNull(message="请输入充值记录id")
	private Long id;
	
	@ApiModelProperty("审核失败原因")
	@NotNull(message="请输入审核失败原因")
	private String failedReason;
	
}
