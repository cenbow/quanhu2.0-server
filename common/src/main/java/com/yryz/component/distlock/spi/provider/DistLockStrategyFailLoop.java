package com.yryz.component.distlock.spi.provider;

import java.util.Random;

import com.yryz.component.distlock.DistLockConfig;
import com.yryz.component.distlock.DistLockException;
import com.yryz.component.distlock.DistLockStatus;
import com.yryz.component.distlock.DistLockStrategyConfig;
import com.yryz.component.distlock.spi.DistLockProvider;
import com.yryz.component.distlock.spi.DistLockStrategy;

/**
 * 锁操作策略，锁操作失败时，等待循环重试，默认策略实现
 * 
 * @author KF3
 *
 */
public class DistLockStrategyFailLoop implements DistLockStrategy {

	public void lockStrategy(DistLockProvider provider, DistLockConfig<?>.DistLockConfigEntry<?> entry,
			DistLockStrategyConfig strategyConfig) throws DistLockException {

		Long nanoTime = System.nanoTime();
		long nanoTimeout = entry.getTimeout() * MILLI_NANO_TIME;
		// 在timeout的时间范围内不断轮询锁
		try {
			while (System.nanoTime() - nanoTime < nanoTimeout) {

				DistLockStatus status = provider.doLock(entry);
				switch (status) {
				case Locked:
					return;
				default:
					Thread.sleep(strategyConfig.getRetryInteval(), new Random().nextInt(10));
					break;
				}
			}
		} catch (Exception e) {
			if (e instanceof DistLockException) {
				throw (DistLockException) e;
			}
			throw new DistLockException("执行循环重试策略获取锁操作异常！", e);
		}
	}

	public void unLockStrategy(DistLockProvider provider, DistLockConfig<?>.DistLockConfigEntry<?> entry,
			DistLockStrategyConfig strategyConfig) throws DistLockException {
		provider.doUnLock(entry);
	}
}
