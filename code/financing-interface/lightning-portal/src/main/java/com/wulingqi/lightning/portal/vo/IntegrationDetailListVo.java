package com.wulingqi.lightning.portal.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("积分明细列表返回实体")
public class IntegrationDetailListVo {
	
	@ApiModelProperty(value = "交易类型: 0->收入; 1->支出")
	private String tradeType;
	
	@ApiModelProperty(value = "交易金额")
	private String value;
	
	@ApiModelProperty(value = "标题")
	private String title;
	
	@ApiModelProperty(value = "备注")
	private String note;
	
	@ApiModelProperty(value = "交易时间")
	private String createTime;

}
