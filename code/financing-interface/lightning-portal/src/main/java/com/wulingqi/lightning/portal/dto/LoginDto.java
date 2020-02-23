package com.wulingqi.lightning.portal.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("登录参数")
public class LoginDto {

	@ApiModelProperty(value = "账号")
	private String account;
	
	@ApiModelProperty(value = "密码")
	private String password;

}
