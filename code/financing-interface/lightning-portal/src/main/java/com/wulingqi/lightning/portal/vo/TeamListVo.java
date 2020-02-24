package com.wulingqi.lightning.portal.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("我的团队列表返回实体")
public class TeamListVo {
	
	@JsonIgnore
	private Long memberId;
	
	@ApiModelProperty(value = "手机号码")
	private String phone;

	@ApiModelProperty(value = "昵称")
	private String nickname;
	
	@ApiModelProperty(value = "有效会员状态: 0->无效; 1->有效")
	private String effectiveStatus;
	
	@ApiModelProperty(value = "头像")
	private String avatarUrl;
	
	@ApiModelProperty(value = "总分享人数")
	private String shareCount;
	
	@ApiModelProperty(value = "分享有效人数")
	private String shareEffectiveCount;
	
	@ApiModelProperty(value = "团队人数-总代理人数")
	private String teamCount;
	
	@ApiModelProperty(value = "团队有效人数-代理有效人数")
	private String teamEffectiveCount;
	
	@ApiModelProperty(value = "代理比例")
	private String agentRatio;
	
	@ApiModelProperty(value = "总收益")
	private String totalIncome;
	
	@ApiModelProperty(value = "注册时间")
	private String registerTime;
	
}
