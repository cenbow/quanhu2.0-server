package com.yryz.component.distlock;

import java.io.Serializable;

import com.yryz.component.distlock.spi.DistLockStrategy;

public class DistLockStrategyConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1160366814228751008L;

	/**
	 * 重试间隔
	 */
	private Integer retryInteval;

	/**
	 * 重试次数
	 */
	private Integer retryCount;

	private Class<? extends DistLockStrategy> strategy;

	/**
	 * 必须配置锁策略
	 * @param strategy
	 */
	public DistLockStrategyConfig(Class<? extends DistLockStrategy> strategy) {
		this.strategy = strategy;
	}

	public Integer getRetryInteval() {
		return retryInteval;
	}

	public void setRetryInteval(Integer retryInteval) {
		this.retryInteval = retryInteval;
	}

	public Integer getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}

	public Class<? extends DistLockStrategy> getStrategy() {
		return strategy;
	}

	public void setStrategy(Class<? extends DistLockStrategy> strategy) {
		this.strategy = strategy;
	}

}
