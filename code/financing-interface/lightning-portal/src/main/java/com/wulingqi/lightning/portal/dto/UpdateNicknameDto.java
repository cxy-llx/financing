package com.wulingqi.lightning.portal.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("更新昵称参数")
public class UpdateNicknameDto {

	@ApiModelProperty(value = "昵称")
	private String nickname;
	
}
