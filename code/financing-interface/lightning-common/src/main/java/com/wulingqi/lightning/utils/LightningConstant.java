package com.wulingqi.lightning.utils;

/**
 * 常用静态常量
 */
public class LightningConstant {

    public static final String RESET_PASS = "重置密码";

    public static final String RESET_MAIL = "重置邮箱";
    
    /**
     * 用于IP定位转换
     */
    public static final String REGION = "内网IP|内网IP";
    
    public static final String SERVER_ERROR= "服务器繁忙，请稍后再试";
    
    /**
     * 订单延迟redis key
     */
    public static final String REDIS_KEY_ORDER_NO = "orderNo:";
    
    /**
	 * 通用数据缓存redis key
	 */
	public static final String GENERAL_DATA = "general_data";
	
	/**
	 * 分销字典redis key
	 */
	public static final String BUSINESS_DICTIONARY = "business_dictionary";
	
	/**
     * 删除状态: 0->未删除
     */
    public static final Integer DELETE_STATUS_NOT_DELETE = 0;
    
    /**
     * 删除状态: 1->已删除
     */
    public static final Integer DELETE_STATUS_DELETEED = 1;
    
    /**
     * 帐号启用状态: 0->禁用
     */
    public static final Integer STATUS_DISABLE = 0;
    
    /**
     * 帐号启用状态: 1->启用
     */
    public static final Integer STATUS_ENABLE = 1;
    
    /**
     * 在线状态: 0->不在线
     */
    public static final Integer ONLINE_STATUS_OFFLINE = 0;
    
    /**
     * 在线状态: 1->在线
     */
    public static final Integer ONLINE_STATUS_ONLINE = 1;
    
    /**
     * 验证类型: 0->注册
     */
    public static final Integer VERIFY_TYPE_REGISTER = 0;
    
    /**
     * 验证类型: 1->忘记密码
     */
    public static final Integer VERIFY_TYPE_FORGET_PW = 1;
    
    /**
     * 验证类型: 2->修改密码
     */
    public static final Integer VERIFY_TYPE_EDIT_PW = 2;
    
    /**
     * 接收类型: 0->手机号
     */
    public static final Integer RECEIVER_TYPE_PHONE = 0;
    
    /**
     * 接收类型: 1->邮箱
     */
    public static final Integer RECEIVER_TYPE_EMAIL = 1;
    
    /**
     * 有效会员状态: 0->无效
     */
    public static final Integer EFFECTIVE_STATUS_NO = 0;
    
    /**
     * 有效会员状态: 1->有效
     */
    public static final Integer EFFECTIVE_STATUS_YES = 1;
    
    /**
     * 处理状态: 0->未处理
     */
    public static final Integer HANDLE_STATUS_NO = 0;
    
    /**
     * 处理状态: 1->已处理
     */
    public static final Integer HANDLE_STATUS_YES = 1;
    
    /**
     * 处理类型: 0->邀请统计
     */
    public static final Integer HANDLE_TYPE_INVITE = 0;
    
    /**
     * 处理类型: 1->分销统计
     */
    public static final Integer HANDLE_TYPE_DISTRIBUTION = 1;
    
    /**
     * 默认标识: 0->否
     */
    public static final Integer DEFAULT_FLAG_NO = 0;
    
    /**
     * 默认标识: 1->是
     */
    public static final Integer DEFAULT_FLAG_YES = 1;
    
    /**
     * 收款方式: 0->银行卡
     */
    public static final Integer COLLECTION_TYPE_BANK = 0;
    
    /**
     * 收款方式: 1->支付宝
     */
    public static final Integer COLLECTION_TYPE_ALIPAY = 1;
    
    /**
     * 收款方式: 2->微信
     */
    public static final Integer COLLECTION_TYPE_WECHAT = 2;
    
    /**
     * 收款方式: 3->USDT
     */
    public static final Integer COLLECTION_TYPE_USDT = 3;
    
    /**
     * 充值状态: 0->充值中
     */
    public static final Integer RECHARGE_STATUS_RECHARGE = 0;
    
    /**
     * 充值状态: 1->成功
     */
    public static final Integer RECHARGE_STATUS_SUCCEED = 1;
    
    /**
     * 充值状态: 2->失败
     */
    public static final Integer RECHARGE_STATUS_FAILED = 2;
    
    /**
     * 订单状态: 0->未支付
     */
    public static final Integer ORDER_STATUS_NO_PAY = 0;
    
    /**
     * 订单状态: 1->已支付
     */
    public static final Integer ORDER_STATUS_PAID = 1;
    
    /**
     * 订单状态: 2->订单超时
     */
    public static final Integer ORDER_STATUS_TIMEOUT = 2;
    
    /**
     * 回调类型: 0->系统回调
     */
    public static final Integer CALLBACK_TYPE_SYSTEM = 0;
    
    /**
     * 回调类型: 1->会员手工回调
     */
    public static final Integer CALLBACK_TYPE_MEMBER = 1;
    
    /**
     * 回调类型: 2->平台手工回调
     */
    public static final Integer CALLBACK_TYPE_PLATFORM = 2;
    
    /**
     * 用户类型: 0->会员
     */
    public static final Integer USER_TYPE_MEMBER = 0;
    
    /**
     * 用户类型: 1->商户
     */
    public static final Integer USER_TYPE_MERCHANT = 1;
    
    /**
     * 交易类型: 0->收入
     */
    public static final Integer TRADE_TYPE_INCOME = 0;
    
    /**
     * 交易类型: 1->支出
     */
    public static final Integer TRADE_TYPE_EXPEND = 1;
    
    /**
     * 交易项目: 0->充值
     */
    public static final Integer TRADE_ITEM_RECHARGE = 0;
    
    /**
     * 交易项目: 1->订单支付
     */
    public static final Integer TRADE_ITEM_ORDER_PAY = 1;
    
    /**
     * 交易项目: 2->商户提现
     */
    public static final Integer TRADE_ITEM_WITHDRAW = 2;
    
    /**
     * 回调状态: 0->未回调
     */
    public static final Integer CALLBACK_STATUS_NO = 0;
    
    /**
     * 回调状态: 1->已回调
     */
    public static final Integer CALLBACK_STATUS_YES = 1;
    
    /**
     * 提现状态: 0->申请中
     */
    public static final Integer WITHDRAW_STATUS_APPLY = 0;
    
    /**
     * 提现状态: 1->成功
     */
    public static final Integer WITHDRAW_STATUS_SUCCESS = 1;
    
    /**
     * 提现状态: 2->失败
     */
    public static final Integer WITHDRAW_STATUS_FAILURE = 2;
    
    
    //----------------------------------业务字典start------------------------------------//
    
    /**
     * 系统维护开关(false表示没有维护,true表示系统维护中)
     */
    public static final String SYSTEM_MAINTENANCE_SWITCH = "system_maintenance_switch";
    
    /**
     * 会员默认代理分销比例
     */
    public static final String MEMBER_AGENT_RATIO = "member_agent_ratio";
    
    /**
   	 * 允许提现的最小数量(设置为0时表示不限制)
   	 */
   	public static final String WITHDRAW_LOWER_LIMIT = "withdraw_lower_limit";
    
    /**
	 * 允许提现的整数倍数(设置为0时表示不限制)
	 */
	public static final String WITHDRAW_MULTIPLE = "withdraw_multiple";
    
    //----------------------------------业务字典end------------------------------------//
    
	
	//----------------------------------通用数据字典start------------------------------------//
	
    /**
	 * 分享连接地址
	 */
	public static final String SHARE_PAGE_URL = "share_page_url";
	
	//----------------------------------通用数据字典end------------------------------------//
	
}
