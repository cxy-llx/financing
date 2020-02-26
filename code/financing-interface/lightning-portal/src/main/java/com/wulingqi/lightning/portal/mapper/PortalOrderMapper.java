package com.wulingqi.lightning.portal.mapper;

import org.apache.ibatis.annotations.Param;

import com.wulingqi.lightning.model.Order;

public interface PortalOrderMapper {
	
	Order selectOrderByMemberIdAndPayAmount(@Param("memberId") Long memberId, @Param("payAmount") String payAmount);
	
}
