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
	
	@ApiModelProperty(value = "支付宝绑定状态: 0->未绑定; 1->已绑定")
	private String alipayBindingStatus;

}
