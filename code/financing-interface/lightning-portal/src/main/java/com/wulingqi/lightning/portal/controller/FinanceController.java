package com.wulingqi.lightning.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wulingqi.lightning.api.CommonResult;
import com.wulingqi.lightning.portal.dto.AutomaticPayDto;
import com.wulingqi.lightning.portal.dto.CollectionInfoDto;
import com.wulingqi.lightning.portal.dto.ManualPayDto;
import com.wulingqi.lightning.portal.dto.PageableDto;
import com.wulingqi.lightning.portal.dto.RechargeDto;
import com.wulingqi.lightning.portal.service.FinanceService;
import com.wulingqi.lightning.portal.vo.CollectionInfoVo;
import com.wulingqi.lightning.portal.vo.IntegrationDetailVo;
import com.wulingqi.lightning.portal.vo.RechargeDetailVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "FinanceController", description = "财务管理")
@RestController
@RequestMapping("api/finance")
public class FinanceController {
	
	@Autowired
	private FinanceService financeService;
	
	@ApiOperation("获取公司收款信息")
    @RequestMapping(value = "/getCollectionInfo", method = RequestMethod.POST)
    public CommonResult<CollectionInfoVo> getCollectionInfo(@RequestBody CollectionInfoDto requestDto) {
		return financeService.getCollectionInfo(requestDto);
    }
	
	@ApiOperation("线下充值")
    @RequestMapping(value = "/recharge", method = RequestMethod.POST)
    public CommonResult<String> recharge(@RequestBody RechargeDto requestDto) {
		return financeService.recharge(requestDto);
    }
	
	@ApiOperation("获取充值明细")
    @RequestMapping(value = "/getRechargeDetail", method = RequestMethod.POST)
    public CommonResult<RechargeDetailVo> getRechargeDetail(@RequestBody PageableDto requestDto) {
		return financeService.getRechargeDetail(requestDto);
    }
	
	@ApiOperation("获取积分明细")
    @RequestMapping(value = "/getIntegrationDetail", method = RequestMethod.POST)
    public CommonResult<IntegrationDetailVo> getIntegrationDetail(@RequestBody PageableDto requestDto) {
		return financeService.getIntegrationDetail(requestDto);
    }
	
	@ApiOperation("订单支付-手动")
    @RequestMapping(value = "/manualPay", method = RequestMethod.POST)
    public CommonResult<String> manualPay(@RequestBody ManualPayDto requestDto) {
		return financeService.manualPay(requestDto);
    }
	
	@ApiOperation("订单支付-自动")
    @RequestMapping(value = "/automaticPay", method = RequestMethod.POST)
    public CommonResult<String> automaticPay(@RequestBody AutomaticPayDto requestDto) {
		return financeService.automaticPay(requestDto);
    }

}
