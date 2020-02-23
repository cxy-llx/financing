package com.wulingqi.lightning.portal.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("修改密码参数")
public class EditPasswordDto {
	
	@ApiModelProperty(value = "旧密码")
	private String oldPassword;
	
	@ApiModelProperty(value = "密码")
	private String password;
	
	@ApiModelProperty(value = "重复密码")
	private String rePassword;
	
}
