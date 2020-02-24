package com.wulingqi.lightning.portal.service;

import com.wulingqi.lightning.api.CommonResult;
import com.wulingqi.lightning.portal.dto.CollectionInfoDto;
import com.wulingqi.lightning.portal.dto.PageableDto;
import com.wulingqi.lightning.portal.dto.RechargeDto;
import com.wulingqi.lightning.portal.vo.CollectionInfoVo;
import com.wulingqi.lightning.portal.vo.IntegrationDetailVo;
import com.wulingqi.lightning.portal.vo.RechargeDetailVo;

public interface FinanceService {

	/**
	 * 获取公司收款信息
	 */
	CommonResult<CollectionInfoVo> getCollectionInfo(CollectionInfoDto requestDto);
	
	/**
	 * 线下充值
	 */
	CommonResult<String> recharge(RechargeDto requestDto);
	
	/**
	 * 获取充值明细
	 */
	CommonResult<RechargeDetailVo> getRechargeDetail(PageableDto requestDto);
	
	/**
	 * 获取积分明细
	 */
	CommonResult<IntegrationDetailVo> getIntegrationDetail(PageableDto requestDto);
	
}
