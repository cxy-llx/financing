package com.wulingqi.lightning.portal.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("获取团队信息参数")
@Data
public class TeamInfoDto {
	
	@ApiModelProperty(value = "手机号")
	private String phone;

	@ApiModelProperty(value = "分页条数")
	private Integer limit = 5;
	
	@ApiModelProperty(value = "当前页数")
	private Integer page = 1;
	
}
