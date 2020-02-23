package com.wulingqi.lightning.portal.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 线程池初始化
 */
@Configuration
public class ThreadPoolConfig {
	
	@Bean
	public ExecutorService getThreadPool() {
		return Executors.newCachedThreadPool();
	}
	
}
