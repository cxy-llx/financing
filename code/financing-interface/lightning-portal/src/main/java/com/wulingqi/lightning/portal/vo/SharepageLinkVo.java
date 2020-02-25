package com.wulingqi.lightning.portal.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("创建邀请链接返回实体")
public class SharepageLinkVo {

	@ApiModelProperty(value = "分享地址")
	private String url;
	
}
