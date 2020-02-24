package com.wulingqi.lightning.portal.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("充值明细列表返回实体")
public class RechargeDetailListVo {
	
	@ApiModelProperty(value = "充值方式: 0->银行卡; 1->支付宝; 2->微信; 3->USDT")
	private String rechargeType;
	
	@ApiModelProperty(value = "充值金额")
	private String value;
	
	@ApiModelProperty(value = "充值状态: 0->审核中; 1->已完成; 2->失败")
	private String rechargeStatus;
	
	@ApiModelProperty(value = "充值状态名称")
	private String rechargeStatusName;
	
	@ApiModelProperty(value = "审核时间")
	private String auditTime;
	
	@ApiModelProperty(value = "创建时间")
	private String createTime;

}
