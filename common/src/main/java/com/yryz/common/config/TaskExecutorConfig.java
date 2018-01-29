/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月29日
 * Id: TaskExecutorConfig.java, 2018年1月29日 下午5:48:48 yehao
 */
package com.yryz.common.config;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月29日 下午5:48:48
 * @Description ThreadPoolTaskExecutor配置项
 */
@Configuration
@EnableAsync
public class TaskExecutorConfig implements AsyncConfigurer{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * @return
	 * @see org.springframework.scheduling.annotation.AsyncConfigurer#getAsyncExecutor()
	 */
	@Override
	@Bean
	@ConfigurationProperties(prefix = "spring.executor")
	public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //如果池中的实际线程数小于corePoolSize,无论是否其中有空闲的线程，都会给新的任务产生新的线程
        taskExecutor.setCorePoolSize(5);
        //连接池中保留的最大连接数。Default: 15 maxPoolSize  
        taskExecutor.setMaxPoolSize(10);
        //queueCapacity 线程池所使用的缓冲队列
        taskExecutor.setQueueCapacity(25);
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务  
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行  
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());  
        taskExecutor.initialize();
        return taskExecutor;
	}
	
	/**
	 * @return
	 * @see org.springframework.scheduling.annotation.AsyncConfigurer#getAsyncUncaughtExceptionHandler()
	 */
	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new AsyncUncaughtExceptionHandler() {
			
			@Override
			public void handleUncaughtException(Throwable ex, Method method, Object... params) {
				logger.info("AsyncUncaughtExceptionHandler..." , ex);
			}
		};
	}

}
