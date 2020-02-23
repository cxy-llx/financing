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
    
    
    //----------------------------------业务字典start------------------------------------//
    public static final String SYSTEM_MAINTENANCE_SWITCH = "system_maintenance_switch";
    
	
	//----------------------------------通用数据字典start------------------------------------//
	
	
	
}
