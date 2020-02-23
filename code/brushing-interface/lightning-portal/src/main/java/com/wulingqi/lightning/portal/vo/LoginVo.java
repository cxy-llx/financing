package com.wulingqi.lightning.portal.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("登录返回实体")
public class LoginVo {
	
	@ApiModelProperty(value = "token消息头")
	private String tokenHead;
	
	@ApiModelProperty(value = "token")
	private String token;

}
