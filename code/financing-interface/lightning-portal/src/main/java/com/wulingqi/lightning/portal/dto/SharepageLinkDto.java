package com.wulingqi.lightning.portal.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("创建邀请链接参数")
public class SharepageLinkDto {

	@ApiModelProperty(value = "代理比例")
	private String agentRatio;
	
}
