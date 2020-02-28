package com.wulingqi.lightning.portal.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.wulingqi.lightning.api.CommonResult;
import com.wulingqi.lightning.portal.dto.ConfirmWithdrawDto;
import com.wulingqi.lightning.portal.dto.AutomaticPayDto;
import com.wulingqi.lightning.portal.dto.CollectionInfoDto;
import com.wulingqi.lightning.portal.dto.ConfirmRechargeDto;
import com.wulingqi.lightning.portal.dto.ManualPayDto;
import com.wulingqi.lightning.portal.dto.PageableDto;
import com.wulingqi.lightning.portal.dto.RechargeDto;
import com.wulingqi.lightning.portal.dto.RefuseRechargeDto;
import com.wulingqi.lightning.portal.dto.RefuseWithdrawDto;
import com.wulingqi.lightning.portal.dto.WithdrawApplyDto;
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

	/**
	 * 订单支付-手动
	 */
	@Transactional
	CommonResult<String> manualPay(ManualPayDto requestDto);
	
	/**
	 * 订单支付-自动
	 */
	@Transactional
	CommonResult<String> automaticPay(AutomaticPayDto requestDto);
	
	/**
	 * 写入账户交易表
	 * @param id 会员id/商户id
	 * @param userType 用户类型: 0->会员; 1->商户
	 * @param tradeType 交易类型: 0->收入; 1->支出
	 * @param tradeItem 交易项目
	 * @param beforeValue 交易前值
	 * @param value 交易值
	 * @param afterValue 交易后值
	 * @param title 标题
	 * @param note 备注
	 */
	void insertAccountTrade(Long id, Integer userType, Integer tradeType, Integer tradeItem,
			BigDecimal beforeValue, BigDecimal value, BigDecimal afterValue, String title, String note, Date createTime);
	
	/**
	 * 写入账户并发错误表
	 * @param id 会员id/商户id
	 * @param userType 用户类型: 0->会员; 1->商户
	 * @param tradeType 交易类型: 0->收入; 1->支出
	 * @param tradeItem 交易项目
	 * @param value 交易值
	 * @param title 标题
	 * @param note 备注
	 */
	void insertAccountConcurrentError(Long id, Integer userType, Integer tradeType, Integer tradeItem,
			BigDecimal value, String title, String note, Date createTime);
	
	/**
	 * 商户提现申请
	 */
	@Transactional
	CommonResult<String> withdrawApply(WithdrawApplyDto requestDto);
	
	/**
	 * 确认商户提现
	 */
	@Transactional
	CommonResult<String> confirmWithdraw(ConfirmWithdrawDto requestDto);
	
	/**
	 * 拒绝商户提现
	 */
	@Transactional
	CommonResult<String> refuseWithdraw(RefuseWithdrawDto requestDto);

	/**
	  *  充值审核通过
	 * @param confirmTopUpDto
	 * @return
	 */
	CommonResult<String> confirmRecharge(ConfirmRechargeDto confirmTopUpDto);

	/**
	 * 审核失败
	 * @param refuseRechargeDto
	 * @return
	 */
	CommonResult<String> refuseRecharge(RefuseRechargeDto refuseRechargeDto);
	
}
