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
    
    
    //----------------------------------业务字典start------------------------------------//
    
    /**
     * 系统维护开关(false表示没有维护,true表示系统维护中)
     */
    public static final String SYSTEM_MAINTENANCE_SWITCH = "system_maintenance_switch";
    
    /**
     * 会员默认代理分销比例
     */
    public static final String MEMBER_AGENT_RATIO = "member_agent_ratio";
    
	
	//----------------------------------通用数据字典start------------------------------------//
	
	
	
}
