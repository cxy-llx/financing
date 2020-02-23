package com.wulingqi.lightning.portal.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("分页参数")
public class PageableDto {
	
	@ApiModelProperty(value = "分页条数")
	private Integer limit = 5;
	
	@ApiModelProperty(value = "当前页数")
	private Integer page = 1;

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

}
