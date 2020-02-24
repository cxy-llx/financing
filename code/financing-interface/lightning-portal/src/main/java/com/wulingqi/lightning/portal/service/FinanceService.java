package com.wulingqi.lightning.portal.service;

import com.wulingqi.lightning.api.CommonResult;
import com.wulingqi.lightning.portal.dto.CollectionInfoDto;
import com.wulingqi.lightning.portal.dto.RechargeDto;
import com.wulingqi.lightning.portal.vo.CollectionInfoVo;

public interface FinanceService {

	/**
	 * 获取公司收款信息
	 */
	CommonResult<CollectionInfoVo> getCollectionInfo(CollectionInfoDto requestDto);
	
	/**
	 * 线下充值
	 */
	CommonResult<String> recharge(RechargeDto requestDto);
	
}
