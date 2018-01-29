package com.yryz.quanhu.dymaic.canal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class TaskExecutorConfig {
	@Value("${spring.taskExecutor.poolMaxSize}")
	private Integer poolMaxSize=100;
	@Value("${spring.taskExecutor.poolCoreSize}")
	private Integer poolCoreSize=100;
	private ThreadPoolTaskExecutor poolTaskExecutor;
	
	@Bean
	public ThreadPoolTaskExecutor poolTaskExecutorConfig(){
		poolTaskExecutor=new ThreadPoolTaskExecutor();
		poolTaskExecutor.setCorePoolSize(poolCoreSize);
		poolTaskExecutor.setMaxPoolSize(poolMaxSize);
		return poolTaskExecutor;
	}
}
