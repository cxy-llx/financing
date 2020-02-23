package com.wulingqi.lightning.portal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wulingqi.lightning.mapper.BusinessDictionaryMapper;
import com.wulingqi.lightning.mapper.GeneralDataMapper;
import com.wulingqi.lightning.model.BusinessDictionary;
import com.wulingqi.lightning.model.GeneralData;
import com.wulingqi.lightning.portal.service.CommonService;
import com.wulingqi.lightning.portal.service.RedisService;
import com.wulingqi.lightning.utils.LightningConstant;

@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
    private RedisService redisService;
	
	@Autowired
	private BusinessDictionaryMapper businessDictionaryMapper;
	
	@Autowired
	private GeneralDataMapper generalDataMapper;
	
	/**
	 * 根据name获取业务字典的value
	 * @param name
	 * @return
	 */
	@Override
	public String getDictionaryValue(String name) {
		String gsonStr = redisService.get(LightningConstant.BUSINESS_DICTIONARY);
		List<BusinessDictionary> list = null;
		if(StringUtils.isEmpty(gsonStr)) {
			list = businessDictionaryMapper.selectAll();
		} else {
			Gson gson = new Gson();
			list = gson.fromJson(gsonStr, new TypeToken<List<BusinessDictionary>>() {}.getType());
		}
		for(BusinessDictionary dictionary : list) {
			if(dictionary.getName().equals(name)) {
				return dictionary.getValue();
			}
		}
		return null;
	}
	
	/**
	 * 根据key获取通用数据的value
	 * @param name
	 * @return
	 */
	@Override
	public String getGeneralDataValue(String field) {
		String gsonStr = redisService.get(LightningConstant.GENERAL_DATA);
		List<GeneralData> list = null;
		if(StringUtils.isEmpty(gsonStr)) {
			list = generalDataMapper.selectAll();
		} else {
			Gson gson = new Gson();
			list = gson.fromJson(gsonStr, new TypeToken<List<GeneralData>>() {}.getType());
		}
		for(GeneralData data : list) {
			if(data.getField().equals(field)) {
				return data.getValue();
			}
		}
		return null;
	}

}
