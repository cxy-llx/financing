package com.wulingqi.lightning.portal.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("积分明细返回实体")
public class IntegrationDetailVo {
	
	@ApiModelProperty(value = "积分余额")
	private String integration;
	
	@ApiModelProperty(value = "冻结积分")
	private String freezeIntegration;
	
	@ApiModelProperty(value = "收入")
	private String income;
	
	@ApiModelProperty(value = "支出")
	private String expend;
	
	@ApiModelProperty(value = "积分明细列表返回实体")
	private List<IntegrationDetailListVo> list;

}
