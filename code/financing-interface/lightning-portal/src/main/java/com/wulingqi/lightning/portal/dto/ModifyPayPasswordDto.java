package com.wulingqi.lightning.portal.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("修改支付密码参数")
public class ModifyPayPasswordDto {
	
	@ApiModelProperty(value = "验证码")
	private String authCode;
	
	@ApiModelProperty(value = "密码")
	private String password;
	
	@ApiModelProperty(value = "重复密码")
	private String rePassword;
	
}
