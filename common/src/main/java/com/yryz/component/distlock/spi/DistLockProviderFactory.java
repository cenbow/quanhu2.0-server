package com.yryz.component.distlock.spi;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.yryz.component.distlock.spring.DistLockContext;

/**
 * 
 * 锁实现提供者工厂，使用工厂方式比注册方式，可减少对spring的依赖，虽然最终还是会与spring集成 此模式下，既可通过IOC容器自动注入，也可手工注入
 * 
 * @author lsn
 *
 */
public class DistLockProviderFactory implements ApplicationContextAware, InitializingBean {

	private ApplicationContext applicationContext;

	private static Map<Class<? extends DistLockProvider>, DistLockProvider> dlps = new ConcurrentHashMap<>();

	public static DistLockProvider getDistLockProvider() {
		return dlps.get(DistLockContext.getDistLockProvider());
	}

	public static DistLockProvider getDistLockProvider(Class<? extends DistLockProvider> provider) {
		return dlps.get(provider);
	}

	/**
	 * 依赖IOC容器自动注册
	 * 
	 * @throws Exception
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, DistLockProvider> dlpMaps = applicationContext.getBeansOfType(DistLockProvider.class);
		dlpMaps.forEach(($_, provider) -> {
			dlps.putIfAbsent(provider.getClass(), provider);
		});
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
