package com.wulingqi.lightning.portal.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("充值明细返回实体")
public class RechargeDetailVo {
	
	@ApiModelProperty(value = "累计充值")
	private String total;
	
	@ApiModelProperty(value = "已完成")
	private String succeed;
	
	@ApiModelProperty(value = "审核中")
	private String audit;
	
	@ApiModelProperty(value = "充值明细列表返回实体")
	private List<RechargeDetailListVo> list;

}
