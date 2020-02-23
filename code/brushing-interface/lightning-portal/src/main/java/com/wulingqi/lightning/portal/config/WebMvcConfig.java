package com.wulingqi.lightning.portal.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.wulingqi.lightning.portal.component.LightingExceptionResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
		WebMvcConfigurer.super.configureHandlerExceptionResolvers(resolvers);
		resolvers.add(new LightingExceptionResolver());
	}

}
