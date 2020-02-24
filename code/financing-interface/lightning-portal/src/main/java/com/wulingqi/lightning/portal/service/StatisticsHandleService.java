package com.wulingqi.lightning.portal.service;

import org.springframework.transaction.annotation.Transactional;

public interface StatisticsHandleService {
	
	@Transactional
	void handle();

}
