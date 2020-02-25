package com.wulingqi.lightning.portal.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("订单手动支付参数")
public class ManualPayDto {
	
	@ApiModelProperty(value = "订单id")
	private Long orderId;

}
