package com.yryz.component.distlock.spring;

import com.yryz.component.distlock.spi.DistLockProvider;
import com.yryz.component.distlock.spi.DistLockStrategy;
import com.yryz.component.distlock.spi.DistLockStrategyFactory;

/**
 * 分布式锁上下文，包含锁实现方式配置数据
 * 
 * @author lsn
 *
 */
public abstract class DistLockContext {

	/**
	 * 默认锁实现方式，设定为基于redis的实现
	 */
	private static Class<? extends DistLockProvider> distLockProvider = DistLockProvider.class;

	public static void setDistLockProvider(Class<? extends DistLockProvider> provider) throws Exception {
		DistLockContext.distLockProvider = provider;
	}

	public static Class<? extends DistLockProvider> getDistLockProvider() {
		return DistLockContext.distLockProvider;
	}

	public static DistLockStrategy getDistLockStrategy(Class<? extends DistLockStrategy> strategy) {
		return DistLockStrategyFactory.getDistLockStrategy(strategy);
	}
}
