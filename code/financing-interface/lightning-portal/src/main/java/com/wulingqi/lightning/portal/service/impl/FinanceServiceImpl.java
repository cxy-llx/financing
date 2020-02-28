package com.wulingqi.lightning.portal.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.wulingqi.lightning.api.CommonResult;
import com.wulingqi.lightning.exception.BadRequestException;
import com.wulingqi.lightning.mapper.AccountConcurrentErrorMapper;
import com.wulingqi.lightning.mapper.MemberIntegrationTradeMapper;
import com.wulingqi.lightning.mapper.MemberUnmatchAmountMapper;
import com.wulingqi.lightning.mapper.MerchantBalanceTradeMapper;
import com.wulingqi.lightning.mapper.MerchantMapper;
import com.wulingqi.lightning.mapper.MerchantWithdrawApplyMapper;
import com.wulingqi.lightning.mapper.OfflineRechargeRecordMapper;
import com.wulingqi.lightning.mapper.OrderMapper;
import com.wulingqi.lightning.mapper.PlatformCollectionInfoMapper;
import com.wulingqi.lightning.model.AccountConcurrentError;
import com.wulingqi.lightning.model.Member;
import com.wulingqi.lightning.model.MemberIntegrationTrade;
import com.wulingqi.lightning.model.MemberUnmatchAmount;
import com.wulingqi.lightning.model.Merchant;
import com.wulingqi.lightning.model.MerchantBalanceTrade;
import com.wulingqi.lightning.model.MerchantWithdrawApply;
import com.wulingqi.lightning.model.OfflineRechargeRecord;
import com.wulingqi.lightning.model.Order;
import com.wulingqi.lightning.model.PlatformCollectionInfo;
import com.wulingqi.lightning.portal.dto.AutomaticPayDto;
import com.wulingqi.lightning.portal.dto.CollectionInfoDto;
import com.wulingqi.lightning.portal.dto.ConfirmRechargeDto;
import com.wulingqi.lightning.portal.dto.ConfirmWithdrawDto;
import com.wulingqi.lightning.portal.dto.ManualPayDto;
import com.wulingqi.lightning.portal.dto.PageableDto;
import com.wulingqi.lightning.portal.dto.RechargeDto;
import com.wulingqi.lightning.portal.dto.RefuseRechargeDto;
import com.wulingqi.lightning.portal.dto.RefuseWithdrawDto;
import com.wulingqi.lightning.portal.dto.WithdrawApplyDto;
import com.wulingqi.lightning.portal.mapper.PortalMapper;
import com.wulingqi.lightning.portal.mapper.PortalMemberMapper;
import com.wulingqi.lightning.portal.mapper.PortalMerchantMapper;
import com.wulingqi.lightning.portal.mapper.PortalOrderMapper;
import com.wulingqi.lightning.portal.service.CommonService;
import com.wulingqi.lightning.portal.service.FinanceService;
import com.wulingqi.lightning.portal.service.MemberService;
import com.wulingqi.lightning.portal.service.RedisService;
import com.wulingqi.lightning.portal.vo.CollectionInfoVo;
import com.wulingqi.lightning.portal.vo.IntegrationDetailVo;
import com.wulingqi.lightning.portal.vo.RechargeDetailVo;
import com.wulingqi.lightning.utils.LightningConstant;
import com.wulingqi.lightning.utils.StringUtils;

@Service
public class FinanceServiceImpl implements FinanceService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceServiceImpl.class);
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PortalMemberMapper portalMemberMapper;
	
	@Autowired
	private PortalMapper portalMapper;
	
	@Autowired
	private PlatformCollectionInfoMapper platformCollectionInfoMapper;
	
	@Autowired
	private OfflineRechargeRecordMapper offlineRechargeRecordMapper;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private MerchantMapper merchantMapper;
	
	@Autowired
	private MerchantBalanceTradeMapper merchantBalanceTradeMapper;
	
	@Autowired
	private MemberIntegrationTradeMapper memberIntegrationTradeMapper;
	
	@Autowired
	private AccountConcurrentErrorMapper accountConcurrentErrorMapper;
	
	@Autowired
	private PortalMerchantMapper portalMerchantMapper;
	
	@Autowired
	private PortalOrderMapper portalOrderMapper;
	
	@Autowired
	private MemberUnmatchAmountMapper memberUnmatchAmountMapper;
	
	@Autowired
	private MerchantWithdrawApplyMapper merchantWithdrawApplyMapper;
	
	@Autowired
    private RedisService redisService;
	
	/**
	 * 获取公司收款信息
	 */
	@Override
	public CommonResult<CollectionInfoVo> getCollectionInfo(CollectionInfoDto requestDto) {
		List<PlatformCollectionInfo> list = portalMapper.selectPlatformCollectionInfoByCollectionType(requestDto.getRechargeType());
		
		if(list.isEmpty()) {
			return CommonResult.failed("未获取到充值方式");
		}
		
		PlatformCollectionInfo bankInfo = list.get(0);
		for(PlatformCollectionInfo info : list) {
			
			if(LightningConstant.DEFAULT_FLAG_YES.equals(info.getDefaultFlag())) {
				bankInfo = info;
				break;
			}
		}
		
		CollectionInfoVo bankInfoVo = new CollectionInfoVo();
		BeanUtils.copyProperties(bankInfo, bankInfoVo);
		bankInfoVo.setCollectionId(String.valueOf(bankInfo.getId()));
		
		return CommonResult.success(bankInfoVo);
	}

	/**
	 * 线下充值
	 */
	@Override
	public CommonResult<String> recharge(RechargeDto requestDto) {
		
		Long memberId = memberService.getCurrentMember().getId();
		
		if(requestDto.getCollectionId() == null
				|| StringUtils.isEmpty(requestDto.getPaymentScreenshot())
				|| requestDto.getValue() == null
				|| requestDto.getValue() <= 0) {
			return CommonResult.failed(LightningConstant.SERVER_ERROR);
		}
		
		PlatformCollectionInfo collectionInfo = platformCollectionInfoMapper.selectByPrimaryKey(requestDto.getCollectionId());

		if(collectionInfo == null) {
			return CommonResult.failed(LightningConstant.SERVER_ERROR);
		}
		
		//如果收款方式为银行类型，判断支付银行信息为必填
		if(LightningConstant.COLLECTION_TYPE_BANK.equals(collectionInfo.getCollectionType())
				&& (StringUtils.isEmpty(requestDto.getPaymentBankName())
						|| StringUtils.isEmpty(requestDto.getPaymentAccountName())
						|| StringUtils.isEmpty(requestDto.getPaymentBankCardNo()))
				) {
			return CommonResult.failed(LightningConstant.SERVER_ERROR);
		}
		
		//写入线下充值记录表
		OfflineRechargeRecord rechargeRecord = new OfflineRechargeRecord();
		rechargeRecord.setCollectionId(collectionInfo.getId());
		rechargeRecord.setMemberId(memberId);
		rechargeRecord.setRechargeType(collectionInfo.getCollectionType());
		
		BigDecimal b = new BigDecimal(requestDto.getValue());
		rechargeRecord.setValue(b.setScale(2, BigDecimal.ROUND_DOWN).toPlainString());
		
		if(LightningConstant.COLLECTION_TYPE_BANK.equals(collectionInfo.getCollectionType())) {
			rechargeRecord.setPaymentAccountName(requestDto.getPaymentAccountName());
			rechargeRecord.setPaymentBankName(requestDto.getPaymentBankName());
			rechargeRecord.setPaymentBankCardNo(requestDto.getPaymentBankCardNo());
		}
		rechargeRecord.setPaymentScreenshot(requestDto.getPaymentScreenshot());
		rechargeRecord.setCreateTime(new Date());
		
		offlineRechargeRecordMapper.insert(rechargeRecord);
		
		return CommonResult.success(null, "提交成功");
	}

	/**
	 * 获取充值明细
	 */
	@Override
	public CommonResult<RechargeDetailVo> getRechargeDetail(PageableDto requestDto) {
		Long memberId = memberService.getCurrentMember().getId();
		
		RechargeDetailVo result = new RechargeDetailVo();
		result.setTotal(portalMemberMapper.selectMemberTotalRecharge(memberId).toPlainString());
		result.setSucceed(portalMemberMapper.selectMemberRechargeByRechargeStatus(memberId, LightningConstant.RECHARGE_STATUS_SUCCEED).toPlainString());
		result.setAudit(portalMemberMapper.selectMemberRechargeByRechargeStatus(memberId, LightningConstant.RECHARGE_STATUS_RECHARGE).toPlainString());
		
		PageHelper.startPage(requestDto.getPage(), requestDto.getLimit());
		
		result.setList(portalMemberMapper.selectMemberRechargeDetail(memberId));
		
		return CommonResult.success(result);
	}

	/**
	 * 获取积分明细
	 */
	@Override
	public CommonResult<IntegrationDetailVo> getIntegrationDetail(PageableDto requestDto) {
		
		Long memberId = memberService.getCurrentMember().getId();
		Member member = memberService.getMemberById(memberId);
		
		IntegrationDetailVo result = new IntegrationDetailVo();
		result.setIntegration(member.getIntegration());
		result.setFreezeIntegration(member.getFreezeIntegration());
		result.setIncome(portalMemberMapper.selectMemberTotalIncome(memberId).toPlainString());
		result.setExpend(portalMemberMapper.selectMemberTotalExpend(memberId).toPlainString());
		
		return CommonResult.success(result);
	}

	/**
	 * 订单支付-手动
	 */
	@Override
	public CommonResult<String> manualPay(ManualPayDto requestDto) {
		
		Long orderId = requestDto.getOrderId();
		Member member = memberService.getMemberById(memberService.getCurrentMember().getId());
		
		if(orderId == null) {
			return CommonResult.failed(LightningConstant.SERVER_ERROR);
		}
		
		//判断订单信息是否与会员信息匹配
		Order order = orderMapper.selectByPrimaryKey(orderId);
		if(order == null || !member.getId().equals(order.getMemberId())) {
			return CommonResult.failed(LightningConstant.SERVER_ERROR);
		}
		
		//判断订单状态是否为已支付状态
		if(LightningConstant.ORDER_STATUS_PAID.equals(order.getOrderStatus())) {
			return CommonResult.failed("订单已支付，请勿重复支付");
		}
		
		//查看订单是否存在回调任务，如果不存在，添加回调任务
		String key = LightningConstant.REDIS_KEY_ORDER_NO + order.getOrderNo();
		if(StringUtils.isEmpty(redisService.get(key))) {
			redisService.set(key, String.valueOf(order.getId()));
			//设置过期时间为1s
			redisService.expire(key, 1);
		} else {
			return CommonResult.failed("订单已支付，请勿重复支付");
		}
		
		//调用订单支付
		orderPay(member, order);
		
		return CommonResult.success(null, "支付成功");
	}

	/**
	 * 订单支付-自动
	 */
	@Override
	public CommonResult<String> automaticPay(AutomaticPayDto requestDto) {
		
		if(StringUtils.isEmpty(requestDto.getValue())) {
			return CommonResult.failed(LightningConstant.SERVER_ERROR);
		}
		
		String payAmount = new BigDecimal(requestDto.getValue()).setScale(2, BigDecimal.ROUND_DOWN).toPlainString();
		
		Member member = memberService.getMemberById(memberService.getCurrentMember().getId());
		
		//根据金额查询会员未支付订单
		Order order = portalOrderMapper.selectOrderByMemberIdAndPayAmount(member.getId(), payAmount);
		Date currentDate = new Date();
		if(order == null || currentDate.getTime() > order.getDeadlineTime().getTime()) {
			MemberUnmatchAmount unmatchAmount = new MemberUnmatchAmount();
			unmatchAmount.setMemberId(member.getId());
			unmatchAmount.setPayAmount(payAmount);
			memberUnmatchAmountMapper.insert(unmatchAmount);
		} else {
			
			//查看订单是否存在回调任务，如果不存在，添加回调任务
			String key = LightningConstant.REDIS_KEY_ORDER_NO + order.getOrderNo();
			if(StringUtils.isEmpty(redisService.get(key))) {
				redisService.set(key, String.valueOf(order.getId()));
				//设置过期时间为1s
				redisService.expire(key, 1);
			} else {
				return CommonResult.failed("订单已支付，请勿重复支付");
			}
			
			//调用订单支付
			orderPay(member, order);
			
		}
		
		return CommonResult.success(null, "支付成功");
	}
	
	
	/**
	 * 订单支付
	 * @param member
	 * @param order
	 */
	private void orderPay(Member member, Order order) {
		
		Date currentDate = new Date();
		
		//更新订单状态和支付时间
		order.setOrderStatus(LightningConstant.ORDER_STATUS_PAID); //订单状态: 1->已支付
		order.setCallbackType(LightningConstant.CALLBACK_TYPE_MEMBER); //回调类型: 1->会员手工回调
		order.setPayTime(currentDate);
		
		orderMapper.updateByPrimaryKey(order);
		
		//扣减会员冻结余额
		BigDecimal freezeIntegration = new BigDecimal(member.getFreezeIntegration());
		BigDecimal tradeValue = new BigDecimal(order.getAmount());
		BigDecimal beforeValue = new BigDecimal(member.getIntegration()).add(freezeIntegration);
		BigDecimal afterValue = beforeValue.subtract(tradeValue);
		
		//写入会员账户交易记录表
		insertAccountTrade(member.getId(), LightningConstant.USER_TYPE_MEMBER, LightningConstant.TRADE_TYPE_EXPEND,
				LightningConstant.TRADE_ITEM_ORDER_PAY, beforeValue, tradeValue, afterValue, "理财释放", order.getOrderNo(), currentDate);
		
		member.setFreezeIntegration(freezeIntegration.subtract(tradeValue).toPlainString());
		int count = portalMemberMapper.updateMemberByPrimaryKey(member);
		if(count != 1) {
			insertAccountConcurrentError(member.getId(), LightningConstant.USER_TYPE_MEMBER, LightningConstant.TRADE_TYPE_EXPEND,
					LightningConstant.TRADE_ITEM_ORDER_PAY, tradeValue, "理财释放", order.getOrderNo(), currentDate);
		}
		
		//增加商户余额
		Merchant merchant = merchantMapper.selectByPrimaryKey(order.getMerchantId());
		BigDecimal balance = new BigDecimal(merchant.getBalance());
		beforeValue = balance.add(new BigDecimal(merchant.getFreezeBalance()));
		afterValue = beforeValue.add(tradeValue);
		
		//写入商户账户交易记录表
		insertAccountTrade(merchant.getId(), LightningConstant.USER_TYPE_MERCHANT, LightningConstant.TRADE_TYPE_INCOME,
				LightningConstant.TRADE_ITEM_ORDER_PAY, beforeValue, tradeValue, afterValue, "订单支付", order.getOrderNo(), currentDate);
		merchant.setBalance(balance.add(tradeValue).toPlainString());
		
		count = portalMerchantMapper.updateMerchantByPrimaryKey(merchant);
		if(count != 1) {
			insertAccountConcurrentError(merchant.getId(), LightningConstant.USER_TYPE_MERCHANT, LightningConstant.TRADE_TYPE_INCOME,
					LightningConstant.TRADE_ITEM_ORDER_PAY, tradeValue, "订单支付", order.getOrderNo(), currentDate);
		}
		
	}
	
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
	@Override
	public void insertAccountTrade(Long id, Integer userType, Integer tradeType, Integer tradeItem,
			BigDecimal beforeValue, BigDecimal value, BigDecimal afterValue, String title, String note, Date createTime) {
		
		if(LightningConstant.USER_TYPE_MEMBER.equals(userType)) {
			
			MemberIntegrationTrade integrationTrade = new MemberIntegrationTrade();
			integrationTrade.setMemberId(id);
			integrationTrade.setTradeType(tradeType);
			integrationTrade.setTradeItem(tradeItem);
			integrationTrade.setBeforeValue(beforeValue.toPlainString());
			integrationTrade.setValue(value.toPlainString());
			integrationTrade.setAfterValue(afterValue.toPlainString());
			integrationTrade.setTitle(title);
			integrationTrade.setNote(note);
			integrationTrade.setCreateTime(createTime);
			memberIntegrationTradeMapper.insert(integrationTrade);
			
		} else {
			
			MerchantBalanceTrade balanceTrade = new MerchantBalanceTrade();
			balanceTrade.setMerchantId(id);
			balanceTrade.setTradeType(tradeType);
			balanceTrade.setTradeItem(tradeItem);
			balanceTrade.setBeforeValue(beforeValue.toPlainString());
			balanceTrade.setValue(value.toPlainString());
			balanceTrade.setAfterValue(afterValue.toPlainString());
			balanceTrade.setTitle(title);
			balanceTrade.setNote(note);
			balanceTrade.setCreateTime(createTime);
			merchantBalanceTradeMapper.insert(balanceTrade);
			
		}
		
	}

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
	@Override
	public void insertAccountConcurrentError(Long id, Integer userType, Integer tradeType,
			Integer tradeItem, BigDecimal value, String title, String note, Date createTime) {
		
		AccountConcurrentError record = new AccountConcurrentError();
		record.setUserId(id);
		record.setUserType(userType);
		record.setTradeType(tradeType);
		record.setTradeItem(tradeItem);
		record.setValue(value);
		record.setTitle(title);
		record.setNote(note);
		record.setHandleStatus(LightningConstant.HANDLE_STATUS_NO);
		record.setCreateTime(createTime);
		
		accountConcurrentErrorMapper.insert(record);
		
	}

	/**
	 * 商户提现申请
	 */
	@Override
	public CommonResult<String> withdrawApply(WithdrawApplyDto requestDto) {
		
		if(requestDto.getMerchantId() == null
				|| requestDto.getValue() == null
				|| requestDto.getValue() <= 0
				|| StringUtils.isEmpty(requestDto.getBankName())
				|| StringUtils.isEmpty(requestDto.getBankAccount())
				|| StringUtils.isEmpty(requestDto.getBankCardNo())) {
			return CommonResult.failed(LightningConstant.SERVER_ERROR);
		}
		
		//允许转账的最小数量(设置为0时表示不限制)
		double withdrawLowerLimit = Double.valueOf(commonService.getDictionaryValue(LightningConstant.WITHDRAW_LOWER_LIMIT));
		//允许转账的整数倍数(设置为0时表示不限制)
		double withdrawMultiple = Double.valueOf(commonService.getDictionaryValue(LightningConstant.WITHDRAW_MULTIPLE));
		
		//如果限制条件设置为0，表示不限制
		if(withdrawLowerLimit != 0 && requestDto.getValue() < withdrawLowerLimit) {
			return CommonResult.failed("提现金额必须大于" + withdrawLowerLimit);
		}
		if(withdrawMultiple != 0 && requestDto.getValue() % withdrawMultiple != 0) {
			return CommonResult.failed("提现金额必须是" + withdrawMultiple + "的倍数");
		}
		
		Merchant merchant = merchantMapper.selectByPrimaryKey(requestDto.getMerchantId());
		if(LightningConstant.DELETE_STATUS_DELETEED.equals(merchant.getDeleteStatus())) {
			return CommonResult.failed("账号已被删除");
		}
		
		if(LightningConstant.STATUS_DISABLE.equals(merchant.getStatus())) {
			return CommonResult.failed("账号已被冻结");
		}
		
		if(StringUtils.isEmpty(merchant.getSettlementRate())) {
			return CommonResult.failed("结算费率为空");
		}
		
		BigDecimal balance = new BigDecimal(merchant.getBalance());
		BigDecimal value = new BigDecimal(requestDto.getValue()).setScale(2, BigDecimal.ROUND_DOWN);
		BigDecimal settlementRate = new BigDecimal(merchant.getSettlementRate());
		BigDecimal poundage = value.multiply(settlementRate);
		
		if(balance.compareTo(value) < 0) {
			return CommonResult.failed("余额不足");
		}
		
		MerchantWithdrawApply withdrawApply = new MerchantWithdrawApply();
		withdrawApply.setMerchantId(requestDto.getMerchantId());
		withdrawApply.setValue(value.toPlainString());
		withdrawApply.setPoundage(poundage.toPlainString());
		withdrawApply.setActualValue(value.subtract(poundage).toPlainString());
		withdrawApply.setBankName(requestDto.getBankName());
		withdrawApply.setBankAccount(requestDto.getBankAccount());
		withdrawApply.setBankCardNo(requestDto.getBankCardNo());
		withdrawApply.setWithdrawStatus(LightningConstant.WITHDRAW_STATUS_APPLY);
		withdrawApply.setCreateTime(new Date());
		merchantWithdrawApplyMapper.insert(withdrawApply);
		
		//划转账户余额到冻结余额
		merchant.setBalance(balance.subtract(value).toPlainString());
		merchant.setFreezeBalance(new BigDecimal(merchant.getFreezeBalance()).add(value).toPlainString());
		int count = portalMerchantMapper.updateMerchantByPrimaryKey(merchant);
		if(count != 1) {
			throw new RuntimeException(LightningConstant.SERVER_ERROR);
		}
		
		return CommonResult.success(null, "提现申请成功");
	}

	/**
	 * 确认商户提现
	 */
	@Override
	public CommonResult<String> confirmWithdraw(ConfirmWithdrawDto requestDto) {
		
		if(requestDto.getWithdrawId() == null) {
			return CommonResult.failed(LightningConstant.SERVER_ERROR);
		}

		MerchantWithdrawApply withdrawApply = merchantWithdrawApplyMapper.selectByPrimaryKey(requestDto.getWithdrawId());
		if(!LightningConstant.WITHDRAW_STATUS_APPLY.equals(withdrawApply.getWithdrawStatus())) {
			return CommonResult.failed("提现状态异常");
		}
		
		Merchant merchant = merchantMapper.selectByPrimaryKey(withdrawApply.getMerchantId());
		if(LightningConstant.DELETE_STATUS_DELETEED.equals(merchant.getDeleteStatus())) {
			return CommonResult.failed("账号已被删除");
		}
		
		if(LightningConstant.STATUS_DISABLE.equals(merchant.getStatus())) {
			return CommonResult.failed("账号已被冻结");
		}
		
		BigDecimal freezeBalance = new BigDecimal(merchant.getFreezeBalance());
		BigDecimal value = new BigDecimal(withdrawApply.getValue());
		
		if(freezeBalance.compareTo(value) < 0) {
			return CommonResult.failed("账户数据异常");
		}
		
		Date currentDate = new Date();
		
		withdrawApply.setArrivedTime(currentDate);
		withdrawApply.setWithdrawStatus(LightningConstant.WITHDRAW_STATUS_SUCCESS);
		merchantWithdrawApplyMapper.updateByPrimaryKey(withdrawApply);
		
		BigDecimal balance = new BigDecimal(merchant.getBalance());
		BigDecimal beforeValue = balance.add(freezeBalance);
		BigDecimal afterValue = beforeValue.subtract(value);
		
		//写入商户账户交易记录
		insertAccountTrade(merchant.getId(), LightningConstant.USER_TYPE_MERCHANT, LightningConstant.TRADE_TYPE_EXPEND,
				LightningConstant.TRADE_ITEM_WITHDRAW, beforeValue, value, afterValue, "提现", null, currentDate);
		
		//扣除冻结金额
		merchant.setFreezeBalance(freezeBalance.subtract(value).toPlainString());
		int count = portalMerchantMapper.updateMerchantByPrimaryKey(merchant);
		if(count != 1) {
			throw new RuntimeException(LightningConstant.SERVER_ERROR);
		}
		
		return CommonResult.success(null, "确认提现成功");
	}

	/**
	 * 拒绝商户提现
	 */
	@Override
	public CommonResult<String> refuseWithdraw(RefuseWithdrawDto requestDto) {
		
		if(requestDto.getWithdrawId() == null
				|| StringUtils.isEmpty(requestDto.getFailedReason())) {
			return CommonResult.failed(LightningConstant.SERVER_ERROR);
		}
		
		MerchantWithdrawApply withdrawApply = merchantWithdrawApplyMapper.selectByPrimaryKey(requestDto.getWithdrawId());
		if(!LightningConstant.WITHDRAW_STATUS_APPLY.equals(withdrawApply.getWithdrawStatus())) {
			return CommonResult.failed("提现状态异常");
		}
		
		Merchant merchant = merchantMapper.selectByPrimaryKey(withdrawApply.getMerchantId());
		if(LightningConstant.DELETE_STATUS_DELETEED.equals(merchant.getDeleteStatus())) {
			return CommonResult.failed(LightningConstant.SERVER_ERROR);
		}
		
		if(LightningConstant.STATUS_DISABLE.equals(merchant.getStatus())) {
			return CommonResult.failed("账号已被冻结");
		}
		
		BigDecimal freezeBalance = new BigDecimal(merchant.getFreezeBalance());
		BigDecimal value = new BigDecimal(withdrawApply.getValue());
		
		if(freezeBalance.compareTo(value) < 0) {
			return CommonResult.failed("账户数据异常");
		}
		
		withdrawApply.setFailedReason(requestDto.getFailedReason());
		withdrawApply.setWithdrawStatus(LightningConstant.WITHDRAW_STATUS_FAILURE);
		merchantWithdrawApplyMapper.updateByPrimaryKey(withdrawApply);
		
		//划转账户冻结余额到余额
		merchant.setBalance(new BigDecimal(merchant.getBalance()).add(value).toPlainString());
		merchant.setFreezeBalance(freezeBalance.subtract(value).toPlainString());
		int count = portalMerchantMapper.updateMerchantByPrimaryKey(merchant);
		if(count != 1) {
			throw new RuntimeException(LightningConstant.SERVER_ERROR);
		}
		
		return CommonResult.success();
	}

	@Override
	public CommonResult<String> confirmRecharge(ConfirmRechargeDto confirmTopUpDto) {
		
		OfflineRechargeRecord offlineRechargeRecord=offlineRechargeRecordMapper.selectByPrimaryKey(confirmTopUpDto.getId());
		//验证充值状态
		if(!offlineRechargeRecord.getRechargeStatus().equals(LightningConstant.RECHARGE_STATUS_RECHARGE)) {
			throw new BadRequestException("充值状态异常！");
		}
		
		//统一时间
		Date now=new Date();
		
		offlineRechargeRecord.setRechargeStatus(LightningConstant.RECHARGE_STATUS_SUCCEED);
		offlineRechargeRecord.setRechargeTime(now);
		
		Member member=memberService.getMemberById(offlineRechargeRecord.getMemberId());
		
		BigDecimal Integration = new BigDecimal(member.getIntegration());
		BigDecimal freezeIntegration = new BigDecimal(member.getFreezeIntegration());
		
		BigDecimal value = new BigDecimal(offlineRechargeRecord.getValue());
		
		MemberIntegrationTrade memberIntegrationTrade=new MemberIntegrationTrade();
		memberIntegrationTrade.setMemberId(member.getId());
		memberIntegrationTrade.setTradeType(LightningConstant.TRADE_TYPE_INCOME);
		memberIntegrationTrade.setTradeItem(LightningConstant.TRADE_ITEM_RECHARGE);
		memberIntegrationTrade.setBeforeValue(Integration.add(freezeIntegration).toPlainString());
		memberIntegrationTrade.setValue(value.toPlainString());
		memberIntegrationTrade.setAfterValue(Integration.add(freezeIntegration).add(value).toPlainString());
		memberIntegrationTrade.setTitle("充值");
		memberIntegrationTrade.setNote("充值");
		memberIntegrationTrade.setCreateTime(now);
		memberIntegrationTradeMapper.insert(memberIntegrationTrade);
		
		member.setIntegration(Integration.add(value).toPlainString());
		
		int count= portalMemberMapper.updateMemberByPrimaryKey(member);
		if(count != 1) {
			throw new RuntimeException(LightningConstant.SERVER_ERROR);
		}
		
		offlineRechargeRecordMapper.updateByPrimaryKey(offlineRechargeRecord);
		
		return CommonResult.success();
		
	}

	@Override
	public CommonResult<String> refuseRecharge(RefuseRechargeDto refuseRechargeDto) {
		
		OfflineRechargeRecord offlineRechargeRecord=offlineRechargeRecordMapper.selectByPrimaryKey(refuseRechargeDto.getId());
		//验证充值状态
		if(!offlineRechargeRecord.getRechargeStatus().equals(LightningConstant.RECHARGE_STATUS_RECHARGE)) {
			throw new BadRequestException("充值状态异常！");
		}
		
		offlineRechargeRecord.setFailedReason(refuseRechargeDto.getFailedReason());
		offlineRechargeRecordMapper.updateByPrimaryKey(offlineRechargeRecord);
		
		return CommonResult.success();
		
	}

}
