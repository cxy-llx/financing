package com.wulingqi.lightning.portal.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("登录参数")
public class LoginDto {

	@ApiModelProperty(value = "手机号码")
	private String phone;
	
	@ApiModelProperty(value = "密码")
	private String password;

}
