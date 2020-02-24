package com.wulingqi.lightning.portal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wulingqi.lightning.model.AppUpgrade;
import com.wulingqi.lightning.model.PlatformCollectionInfo;
import com.wulingqi.lightning.model.VerificationCode;

public interface PortalMapper {
	
	List<VerificationCode> selectVerificationCodeByReceiverAndMinute(@Param("receiver") String receiver, @Param("minute") Integer minute);
	
	AppUpgrade selectAppUpgradeByType(@Param("type") Integer type);
	
	List<PlatformCollectionInfo> selectPlatformCollectionInfoByCollectionType(@Param("collectionType") Integer collectionType);
	
}
