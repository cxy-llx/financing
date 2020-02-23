package com.wulingqi.lightning.portal.service.impl;

import java.text.SimpleDateFormat;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wulingqi.lightning.api.CommonResult;
import com.wulingqi.lightning.model.AppUpgrade;
import com.wulingqi.lightning.portal.mapper.PortalMapper;
import com.wulingqi.lightning.portal.service.CommonService;
import com.wulingqi.lightning.portal.service.OtherService;
import com.wulingqi.lightning.portal.vo.CommonInfoVo;
import com.wulingqi.lightning.portal.vo.VersionUpgradeVo;

@Service
public class OtherServiceImpl implements OtherService {
	
	@Autowired
	private CommonService commonService;

	@Autowired
	private PortalMapper portalMapper;
	
	/**
	 * 获取App版本更新信息
	 */
	@Override
	public CommonResult<VersionUpgradeVo> getVersionUpgrade(Integer type) {
		VersionUpgradeVo upgradeVo = new VersionUpgradeVo();
		AppUpgrade upgrade = portalMapper.selectAppUpgradeByType(type);
		BeanUtils.copyProperties(upgrade, upgradeVo);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		upgradeVo.setAddTime(sdf.format(upgrade.getCreateTime()));
		
		return CommonResult.success(upgradeVo);
	}

	/**
	 * 获取公共信息
	 */
	@Override
	public CommonResult<CommonInfoVo> getCommonInfo() {
		
		CommonInfoVo result = new CommonInfoVo();
		
		return CommonResult.success(result);
		
	}

}
