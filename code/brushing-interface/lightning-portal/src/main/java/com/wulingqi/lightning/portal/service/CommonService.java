package com.wulingqi.lightning.portal.service;

public interface CommonService {
	
	/**
	 * 根据name获取业务字典的value
	 * @param name
	 * @return
	 */
	String getDictionaryValue(String name);

	/**
	 * 根据key获取通用数据的value
	 * @param name
	 * @return
	 */
	String getGeneralDataValue(String field);
	
}
