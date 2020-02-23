package com.wulingqi.lightning.portal.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("获取验证码参数")
public class AuthCodeDto {

	@ApiModelProperty(value = "手机号码")
	private String phone;

}
