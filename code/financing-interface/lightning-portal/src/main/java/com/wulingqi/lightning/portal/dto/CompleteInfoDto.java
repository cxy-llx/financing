package com.wulingqi.lightning.portal.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("完善资料参数")
public class CompleteInfoDto {

	@ApiModelProperty(value = "支付宝uid")
	private String alipayUid;
	
}
