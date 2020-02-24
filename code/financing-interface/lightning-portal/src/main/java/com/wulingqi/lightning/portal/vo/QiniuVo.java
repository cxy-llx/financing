package com.wulingqi.lightning.portal.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("七牛云存储返回实体")
public class QiniuVo {
	
	@ApiModelProperty(value = "token")
	private String token;
	
	@ApiModelProperty(value = "七牛云上传地址")
	private String host;
	
	@ApiModelProperty(value = "指定zone的具体区域")
	private String zone;
	
	@ApiModelProperty(value = "七牛云访问图片的CDN地址")
	private String cdnUrl;

}
