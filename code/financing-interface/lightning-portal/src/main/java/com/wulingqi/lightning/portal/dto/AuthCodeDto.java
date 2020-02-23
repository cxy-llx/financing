package com.wulingqi.lightning.portal.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("获取验证码参数")
public class AuthCodeDto {

	@ApiModelProperty(value = "手机号/邮箱")
	private String receiver;
	
	@ApiModelProperty(value = "验证类型: 0->注册; 1->忘记密码; 2->修改密码")
	private Integer verifyType;

}
