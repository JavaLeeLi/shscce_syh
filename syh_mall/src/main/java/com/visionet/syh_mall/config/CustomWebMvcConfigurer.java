package com.visionet.syh_mall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.visionet.syh_mall.interceptor.RequestInterceptor;

@Configuration
public class CustomWebMvcConfigurer extends WebMvcConfigurerAdapter {
	
	/**
	 * 自定义拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new RequestInterceptor()).addPathPatterns("/**")
			.excludePathPatterns("/adminLogin","/logout");
		super.addInterceptors(registry);
	}
	
}
