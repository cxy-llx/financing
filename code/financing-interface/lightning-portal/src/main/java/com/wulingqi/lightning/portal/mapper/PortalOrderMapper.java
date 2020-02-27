package com.wulingqi.lightning.portal.mapper;

import org.apache.ibatis.annotations.Param;

import com.wulingqi.lightning.model.Order;
import com.wulingqi.lightning.model.OrderCallback;

public interface PortalOrderMapper {
	
	Order selectOrderByMemberIdAndPayAmount(@Param("memberId") Long memberId, @Param("payAmount") String payAmount);
	
	OrderCallback selectOrderCallbackByOrderId(@Param("orderId") Long orderId);
	
	Order selectOrderByOrderNo(@Param("orderNo") String orderNo);
	
}
