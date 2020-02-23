package com.wulingqi.lightning.portal.component;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.wulingqi.lightning.mapper.BusinessDictionaryMapper;
import com.wulingqi.lightning.mapper.GeneralDataMapper;
import com.wulingqi.lightning.model.BusinessDictionary;
import com.wulingqi.lightning.model.GeneralData;
import com.wulingqi.lightning.portal.service.RedisService;
import com.wulingqi.lightning.utils.LightningConstant;

/**
 * 缓存初始化
 */
@Component
public class CacheInitializtion implements CommandLineRunner {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CacheInitializtion.class);
	
	@Autowired
    private RedisService redisService;
	
	@Autowired
	private BusinessDictionaryMapper businessDictionaryMapper;
	
	@Autowired
	private GeneralDataMapper generalDataMapper;

	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("Cache initialize start");
		
		Gson gson = new Gson();
		
		List<BusinessDictionary> dictionaries = businessDictionaryMapper.selectAll();
		redisService.set(LightningConstant.BUSINESS_DICTIONARY, gson.toJson(dictionaries));
		
		List<GeneralData> generalDatas = generalDataMapper.selectAll();
		redisService.set(LightningConstant.GENERAL_DATA, gson.toJson(generalDatas));
		
		LOGGER.info("Cache initialize end");
	}

	
	
}
