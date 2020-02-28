package com.wulingqi.lightning.portal.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("确认商户提现参数")
@Data
public class ConfirmWithdrawDto {

	@ApiModelProperty(value = "提现ID")
	private Long withdrawId;
	
}
