package com.wulingqi.lightning.portal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wulingqi.lightning.model.AppUpgrade;
import com.wulingqi.lightning.model.SmsCode;

public interface PortalMapper {
	
	List<SmsCode> selectSmsCodeByPhoneAndMinute(@Param("phone") String phone, @Param("minute") Integer minute);
	
	AppUpgrade selectAppUpgradeByType(@Param("type") Integer type);
}
