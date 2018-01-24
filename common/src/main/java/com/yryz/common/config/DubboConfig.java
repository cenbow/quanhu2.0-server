/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月22日
 * Id: DubboConfig.java, 2018年1月22日 下午8:23:23 yehao
 */
package com.yryz.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.ConsumerConfig;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月22日 下午8:23:23
 * @Description dubbo配置中心的配置项
 */
@Configuration
public class DubboConfig {
	
    @Bean
	@ConfigurationProperties(prefix = "spring.dubbo.consumer")
	public ConsumerConfig consumerConfig() {
    	ConsumerConfig consumerConfig = new ConsumerConfig();
    	consumerConfig.setCheck(false);
    	consumerConfig.setLazy(true);
    	return consumerConfig;
	}

}
