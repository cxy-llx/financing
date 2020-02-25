package com.wulingqi.lightning.portal.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("订单自动支付参数")
public class AutomaticPayDto {
	
	@ApiModelProperty(value = "金额")
	private String value;

}
