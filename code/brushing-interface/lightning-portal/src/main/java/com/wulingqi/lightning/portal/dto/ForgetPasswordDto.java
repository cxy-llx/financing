package com.wulingqi.lightning.portal.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("忘记密码参数")
public class ForgetPasswordDto {
	
	@ApiModelProperty(value = "手机号码")
	private String phone;
	
	@ApiModelProperty(value = "验证码")
	private String authCode;
	
	@ApiModelProperty(value = "密码")
	private String password;
	
	@ApiModelProperty(value = "重复密码")
	private String rePassword;
	
}
