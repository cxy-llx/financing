package com.wulingqi.lightning.portal.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("消息返回实体")
@Data
public class MessageVo {
	
	@ApiModelProperty(value = "消息分类名称")
	private String messageCategoryName;
	
	@ApiModelProperty(value = "标题")
	private String title;
	
	@ApiModelProperty(value = "内容")
	private String content;
	
	@ApiModelProperty(value = "创建时间")
	private String createTime;

}
