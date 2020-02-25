package com.wulingqi.lightning.portal.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("会员注册参数")
public class RegisterDto {

	@ApiModelProperty(value = "账号")
	private String account;
	
	@ApiModelProperty(value = "验证码")
	private String authCode;
	
	@ApiModelProperty(value = "邀请码")
	private String inviteCode;
	
	@ApiModelProperty(value = "代理比例(测试邀请码可以不传代理比例)")
	private String agentRatio;
	
	@ApiModelProperty(value = "参数")
	private String param;
	
	@ApiModelProperty(value = "密码")
	private String password;
	
	@ApiModelProperty(value = "重复密码")
	private String rePassword;

}
