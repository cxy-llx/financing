package com.wulingqi.lightning.portal.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("我的团队返回实体")
public class TeamInfoVo {
	
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
	
	@ApiModelProperty(value = "总收益")
	private String totalIncome;
	
	@ApiModelProperty(value = "我的团队列表")
	List<TeamListVo> teamList;
	
}
