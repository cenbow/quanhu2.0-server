package com.yryz.component.distlock.lock;

import java.io.Serializable;

/**
 * 分布式锁通用工具类，对锁的具体实现
 * 需要手动配置spring bean
 * 
 * @author lsn
 *
 * @param <T>
 */
public class GenericDistLock<K extends Serializable> extends BaseDistLock<K> {

	/**
	 * 强制要求用户去实现的部分，构建锁定目标资源的key，本实现中直接简单调用资源对象的toString方法返回
	 * 
	 * @param resource
	 * @return
	 */
	@Override
	public String buildLockResourceKey(K resource) {
		return resource.toString();
	}

}
