package com.wulingqi.lightning.portal.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("会员信息")
public class MemberInfoVo {
	
	@ApiModelProperty(value = "会员ID")
	private String memberId;
	
	@ApiModelProperty(value = "姓名")
	private String name;

	@ApiModelProperty(value = "会员昵称")
	private String nickname;
	
	@ApiModelProperty(value = "头像")
	private String avatarUrl;
	
	@ApiModelProperty(value = "手机号码")
	private String phone;
	
	@ApiModelProperty(value = "邀请人姓名")
	private String inviter;
	
	@ApiModelProperty(value = "邀请码")
	private String inviteCode;
	
	@ApiModelProperty(value = "usdt地址")
	private String usdtAddress;
	
	@ApiModelProperty(value = "激活状态: 0->未激活; 1->已激活")
	private String activeStatus;
	
	@ApiModelProperty(value = "帐号启用状态:0->禁用；1->启用")
	private String status;
	
	@ApiModelProperty(value = "身份证号码")
	private String idCard;
	
	@ApiModelProperty(value = "身份证正面")
	private String idCardFront;
	
	@ApiModelProperty(value = "身份证反面")
	private String idCardBack;
	
	@ApiModelProperty(value = "银行名称")
	private String bankName;
	
	@ApiModelProperty(value = "银行账户名")
	private String bankAccount;
	
	@ApiModelProperty(value = "银行卡号")
	private String bankCardNo;
	
	@ApiModelProperty(value = "USDT余额")
	private String usdt;
	
	@ApiModelProperty(value = "EVEpay余额")
	private String coinOne;
	
	@ApiModelProperty(value = "TBCC余额")
	private String coinTwo;
	
}
