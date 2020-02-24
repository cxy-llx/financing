package com.wulingqi.lightning.portal.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("更新头像参数")
public class UpdateAvatarUrlDto {

	@ApiModelProperty(value = "头像")
	private String avatarUrl;
	
}
