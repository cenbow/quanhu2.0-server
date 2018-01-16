package com.yryz.component.distlock.spi;

import java.io.Serializable;

import com.yryz.component.distlock.DistLockConfig;
import com.yryz.component.distlock.DistLockException;
import com.yryz.component.distlock.DistLockStatus;

/**
 * 分布式锁实现提供者接口
 * 
 * @author lsn
 *
 */
public interface DistLockProvider {

	/**
	 * 具体某个资源的加锁方法实现
	 *
	 * @param lockKey
	 *            锁的目标资源key
	 * @param timeout
	 *            超时时间
	 * @param expire
	 *            失效时间
	 */
	DistLockStatus doLock(DistLockConfig<? extends Serializable>.DistLockConfigEntry<?> entry) throws DistLockException;
	
	/**
	 * 解锁方法
	 *
	 * @param lockKey
	 *            锁的目标资源key
	 */
	void doUnLock(DistLockConfig<? extends Serializable>.DistLockConfigEntry<?> entry) throws DistLockException;
}
