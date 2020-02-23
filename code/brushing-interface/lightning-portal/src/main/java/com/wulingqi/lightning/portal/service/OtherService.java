package com.wulingqi.lightning.portal.service;

import com.wulingqi.lightning.api.CommonResult;
import com.wulingqi.lightning.portal.vo.CommonInfoVo;
import com.wulingqi.lightning.portal.vo.VersionUpgradeVo;

public interface OtherService {
	
	/**
	 * 获取App版本更新信息
	 */
	CommonResult<VersionUpgradeVo> getVersionUpgrade(Integer type);
	
	/**
	 * 获取公共信息
	 */
	CommonResult<CommonInfoVo> getCommonInfo();
	
}
