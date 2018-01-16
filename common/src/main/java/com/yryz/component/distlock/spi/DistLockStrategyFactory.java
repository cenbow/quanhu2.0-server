package com.yryz.component.distlock.spi;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * 锁策略工厂
 * 
 * @author lsn
 *
 */
public class DistLockStrategyFactory implements ApplicationContextAware, InitializingBean {

	private ApplicationContext applicationContext;

	private static Map<Class<? extends DistLockStrategy>, DistLockStrategy> dlss = new ConcurrentHashMap<>();

	public static DistLockStrategy getDistLockStrategy(Class<? extends DistLockStrategy> strategy) {
		return dlss.get(strategy);
	}

	/**
	 * 依赖IOC容器自动注册
	 * 
	 * @throws Exception
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, DistLockStrategy> dlsMaps = applicationContext.getBeansOfType(DistLockStrategy.class);
		dlsMaps.forEach(($_, strategy) -> {
			dlss.putIfAbsent(strategy.getClass(), strategy);
		});
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
