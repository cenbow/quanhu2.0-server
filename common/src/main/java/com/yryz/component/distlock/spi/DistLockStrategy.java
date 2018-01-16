package com.yryz.component.distlock.spi;

import com.yryz.component.distlock.DistLockConfig;
import com.yryz.component.distlock.DistLockException;
import com.yryz.component.distlock.DistLockStrategyConfig;

public interface DistLockStrategy {

	/**
	 * 纳秒毫秒换算
	 */
	Long MILLI_NANO_TIME = 1000 * 1000L;

	default void lockStrategy(DistLockProvider provider , DistLockConfig<?>.DistLockConfigEntry<?> entry, DistLockStrategyConfig strategyConfig)
			throws DistLockException {
		try {
			provider.doLock(entry);
		} catch (Exception e) {
			if (e instanceof DistLockException) {
				throw (DistLockException) e;
			}
			throw new DistLockException("执行锁策略异常！", e);
		}
	}

	default void unLockStrategy(DistLockProvider provider , DistLockConfig<?>.DistLockConfigEntry<?> entry, DistLockStrategyConfig strategyConfig)
			throws DistLockException {
		provider.doUnLock(entry);
	}
}
