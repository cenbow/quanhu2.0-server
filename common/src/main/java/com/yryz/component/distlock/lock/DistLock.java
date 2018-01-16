package com.yryz.component.distlock.lock;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.yryz.component.distlock.DistLockConfig;
import com.yryz.component.distlock.DistLockException;
import com.yryz.component.distlock.DistLockStrategyConfig;

/**
 * 分布式锁操作接口 默认提供基于redis和基于zookeeper的分布式锁实现
 * 分布式锁与进程内锁的区别在于，锁的目标对象不同，但同样都服务于协调方法的执行时机
 * 
 * @author lsn
 * @param <S>
 *
 */
public interface DistLock<K extends Serializable , D extends DistLockConfig<K>> {

	String LOCK_SURFFIX = "_LOCK";
	
	/**
	 * 获取锁对象前缀
	 * 
	 * @param seperator
	 *            命名空间分隔符
	 * @param prefs
	 *            命名空间前缀名（多个名字顺序组装）
	 * @return
	 */
	default String getLockPrefix(String separator, String... prefs) {
		List<String> prefixs = Arrays.asList(prefs);
		StringBuffer sb = new StringBuffer();
		prefixs.forEach(pref -> {
			sb.append(pref).append(separator);
		});
		return sb.toString();
	}

	/**
	 * 加锁
	 *
	 * @param prefix
	 *            锁前缀
	 * @param resources
	 *            锁的目标资源
	 * @return 已经加锁的keys
	 */
	List<DistLockConfig<K>.DistLockConfigEntry<K>> lock(DistLockConfig<K> lockConfig , DistLockStrategyConfig strategyConfig) throws DistLockException;

	/**
	 * 强制要求用户去实现的部分，构建锁定目标资源的key
	 * 
	 * @param resource
	 * @return
	 */
	String buildLockResourceKey(K resource);

	/**
	 * 解锁
	 *
	 * @param prefix
	 *            业务前缀
	 * @param resources
	 *            锁的目标资源
	 */
	void unlock(List<DistLockConfig<K>.DistLockConfigEntry<K>> entries , DistLockStrategyConfig strategyConfig) throws DistLockException;

}
