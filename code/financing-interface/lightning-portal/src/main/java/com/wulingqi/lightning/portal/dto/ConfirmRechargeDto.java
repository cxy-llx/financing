package com.wulingqi.lightning.portal.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("充值审通过接口参数")
@Data 
public class ConfirmRechargeDto {

	@NotNull(message="请输入充值记录id")
	@ApiModelProperty("充值记录id")
	private Long id;
	
}
