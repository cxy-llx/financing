package com.wulingqi.lightning.portal.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("获取公司收款信息参数")
public class CollectionInfoDto {
	
	@ApiModelProperty(value = "充值方式: 0->银行卡; 1->支付宝; 2->微信; 4->USDT")
	private Integer rechargeType;
}
