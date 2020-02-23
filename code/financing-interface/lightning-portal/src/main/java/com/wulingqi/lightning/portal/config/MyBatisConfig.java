package com.wulingqi.lightning.portal.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis配置类
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.wulingqi.lightning.mapper", "com.wulingqi.lightning.portal.mapper"})
public class MyBatisConfig {
}
