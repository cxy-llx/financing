package com.wulingqi.lightning.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wulingqi.lightning.api.CommonResult;
import com.wulingqi.lightning.portal.service.OtherService;
import com.wulingqi.lightning.portal.vo.CommonInfoVo;
import com.wulingqi.lightning.portal.vo.VersionUpgradeVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 其它接口管理Controller
 */
@RestController
@Api(tags = "OtherController", description = "其它接口管理")
@RequestMapping("/api/other")
public class OtherController {
	
	@Autowired
	private OtherService otherService;
	
	@ApiOperation("获取Android版本更新信息")
	@PostMapping("/getAndroidUpgrade")
	public CommonResult<VersionUpgradeVo> getAndroidUpgrade() {
		return otherService.getVersionUpgrade(0);
	}
	
	@ApiOperation("获取苹果版本更新信息")
	@PostMapping("/getAppleUpgrade")
	public CommonResult<VersionUpgradeVo> getAppleUpgrade() {
		return otherService.getVersionUpgrade(1);
	}
	
	@ApiOperation("获取公共信息接口")
	@PostMapping("/getCommonInfo")
	public CommonResult<CommonInfoVo> getCommonInfo() {
		return otherService.getCommonInfo();
	}

}
