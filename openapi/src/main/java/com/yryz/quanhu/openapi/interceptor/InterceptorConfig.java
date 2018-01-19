/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: InterceptorConfig.java, 2018年1月18日 下午4:27:23 yehao
 */
package com.yryz.quanhu.openapi.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 下午4:27:23
 * @Description 拦截器管理
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

	@Bean
	public AuthInterceptor authInterceptor() {
		return new AuthInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 注册拦截器
		InterceptorRegistration common = registry.addInterceptor(authInterceptor());
		// 配置拦截的路径
		// common.addPathPatterns("/**");
		// 配置不拦截的路径
		// common.excludePathPatterns("/**.html");
		common.addPathPatterns("/v2/**");
		common.excludePathPatterns("/v2/user/login");

		// 还可以在这里注册其它的拦截器
		// registry.addInterceptor(new
		// OtherInterceptor()).addPathPatterns("/**");
	}

}
