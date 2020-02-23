package com.wulingqi.lightning.portal.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("版本更新返回实体")
@Data
public class VersionUpgradeVo {

	@ApiModelProperty(value = "版本号")
	private String version;
	
	@ApiModelProperty(value = "更新说明")
	private String msg;
	
	@ApiModelProperty(value = "下载URL")
	private String url;
	
	@ApiModelProperty(value = "更新时间")
	private String addTime;
	
}
