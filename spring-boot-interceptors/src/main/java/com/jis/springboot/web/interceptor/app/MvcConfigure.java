package com.jis.springboot.web.interceptor.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class MvcConfigure implements WebMvcConfigurer {

	@Autowired
	@Qualifier("homeInterceptor")
	private HandlerInterceptor homeInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(homeInterceptor).excludePathPatterns("/cerrado");

	}

	
}
