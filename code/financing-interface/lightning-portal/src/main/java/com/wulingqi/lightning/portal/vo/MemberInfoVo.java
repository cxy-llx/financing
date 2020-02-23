package com.wulingqi.lightning.portal.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("会员信息")
public class MemberInfoVo {
	
	@ApiModelProperty(value = "会员id")
	private String memberId;
	
	@ApiModelProperty(value = "姓名")
	private String name;

	@ApiModelProperty(value = "会员昵称")
	private String nickname;
	
	@ApiModelProperty(value = "头像")
	private String avatarUrl;
	
	@ApiModelProperty(value = "账号")
	private String account;
	
	@ApiModelProperty(value = "邀请人姓名")
	private String inviter;
	
	@ApiModelProperty(value = "邀请码")
	private String inviteCode;
	
	@ApiModelProperty(value = "会员有效状态: 0->无效; 1->有效")
	private String effectiveStatus;
	
	@ApiModelProperty(value = "帐号启用状态:0->禁用；1->启用")
	private String status;
	
	@ApiModelProperty(value = "支付宝uid")
	private String alipayUid;
	
	@ApiModelProperty(value = "代理比例")
	private String agentRatio;
	
	@ApiModelProperty(value = "总积分")
	private String integration;
	
	@ApiModelProperty(value = "可用积分")
	private String availableIntegration;
	
	@ApiModelProperty(value = "冻结积分")
	private String freezeIntegration;
	
	@ApiModelProperty(value = "注册时间")
	private String createTime;
	
}
