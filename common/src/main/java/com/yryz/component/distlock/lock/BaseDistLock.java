package com.yryz.component.distlock.lock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.yryz.component.distlock.DistLockConfig;
import com.yryz.component.distlock.DistLockConfig.DistLockConfigEntry;
import com.yryz.component.distlock.DistLockException;
import com.yryz.component.distlock.DistLockStrategyConfig;
import com.yryz.component.distlock.spi.DistLockProviderFactory;
import com.yryz.component.distlock.spi.DistLockStrategyFactory;

public abstract class BaseDistLock<K extends Serializable> implements DistLock<K, DistLockConfig<K>> {

	/**
	 * 加锁
	 *
	 * @param prefix
	 *            锁前缀
	 * @param resources
	 *            锁的目标资源
	 * @return 已经加锁的keys
	 * @throws Exception
	 */
	@Override
	public List<DistLockConfig<K>.DistLockConfigEntry<K>> lock(DistLockConfig<K> lockConfig,
			DistLockStrategyConfig strategyConfig) throws DistLockException {
		List<DistLockConfig<K>.DistLockConfigEntry<K>> lockedList = new ArrayList<>();
		if (lockConfig == null || CollectionUtils.isEmpty(lockConfig.getResources())) {
			return lockedList;
		}
		List<K> resources = lockConfig.getResources();
		try {
			for (int i = 0; i < resources.size(); i++) {
				K resource = resources.get(i);
				String lockKey = buildLockKey(lockConfig.getPrefix(), resource);
				DistLockConfig<K>.DistLockConfigEntry<K> entry = lockConfig.new DistLockConfigEntry<>(lockKey, resource,
						lockConfig.getTimeout(), lockConfig.getExpire());
				doLockWithProvider(entry, strategyConfig);
				lockedList.add(entry);
			}
			return lockedList;
		} catch (Exception e) {
			if (CollectionUtils.isNotEmpty(lockedList)) {
				unlock(lockedList, strategyConfig);
			}
			if (e instanceof DistLockException) {
				throw (DistLockException) e;
			}
			throw new DistLockException("获取分布式锁失败！", e);
		}
	}

	private String buildLockKey(String prefix, K resource) {
		return prefix + buildLockResourceKey(resource) + LOCK_SURFFIX;
	}

	/**
	 * 解锁
	 *
	 * @param prefix
	 *            业务前缀
	 * @param resources
	 *            锁的目标资源
	 */
	@Override
	public void unlock(List<DistLockConfig<K>.DistLockConfigEntry<K>> entries, DistLockStrategyConfig strategyConfig)
			throws DistLockException {
		if (CollectionUtils.isNotEmpty(entries)) {
			for (int i = 0; i < entries.size(); i++) {
				doUnLockWithProvider(entries.get(i), strategyConfig);
			}
		}
	}

	private void doLockWithProvider(DistLockConfig<K>.DistLockConfigEntry<K> entry,
			DistLockStrategyConfig strategyConfig) throws DistLockException {
		DistLockStrategyFactory.getDistLockStrategy(strategyConfig.getStrategy()).lockStrategy(DistLockProviderFactory.getDistLockProvider() , entry, strategyConfig);
	}

	private void doUnLockWithProvider(DistLockConfig<K>.DistLockConfigEntry<K> entry,
			DistLockStrategyConfig strategyConfig) throws DistLockException {
		DistLockStrategyFactory.getDistLockStrategy(strategyConfig.getStrategy()).unLockStrategy(DistLockProviderFactory.getDistLockProvider() , entry, strategyConfig);
	}

}
