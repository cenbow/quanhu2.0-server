package com.yryz.component.distlock.spring;

import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import com.yryz.component.distlock.EnableDistLock;
import com.yryz.component.distlock.spi.DistLockProvider;
import com.yryz.component.distlock.spi.DistLockProviderFactory;
import com.yryz.component.distlock.spi.DistLockStrategy;
import com.yryz.component.distlock.spi.DistLockStrategyFactory;

/**
 * 分布式锁实现依赖的组件注入 不同实现方式依赖的中间件不同，且客户端配置灵活多样，因此只做必要依赖的检查
 * 
 * @author lsn
 *
 */
public class DistLockRegistrar implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
		Map<String, Object> attributes = metadata.getAnnotationAttributes(EnableDistLock.class.getName());
		@SuppressWarnings("unchecked")
		Class<? extends DistLockProvider> provider = (Class<? extends DistLockProvider>) attributes.get("provider");
		String[] refs = (String[]) attributes.get("refs");
		@SuppressWarnings("unchecked")
		Class<? extends DistLockStrategy>[] strategies = (Class<? extends DistLockStrategy>[]) attributes
				.get("strategies");

		registerDistLockBeanDefinitions(registry, provider, strategies);

		try {
			DistLockContext.setDistLockProvider(provider);
			validateRefs(refs, registry);
//			spring IOC容器中，要纳入容器管理，不再直接自己生成实例
//			if (strategies != null && strategies.length > 0) {
//				for (int i = 0; i < strategies.length; i++) {
//					DistLockContext.setDistLockStrategy(strategies[i]);
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void registerDistLockBeanDefinitions(BeanDefinitionRegistry registry,
			Class<? extends DistLockProvider> provider, Class<? extends DistLockStrategy>[] strategies) {
		if (!registry.containsBeanDefinition(provider.getClass().getName())) {
			BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(provider);
			definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

			AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();

			BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, provider.getName());
			BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
		}

		for (int i = 0; i < strategies.length; i++) {
			if (!registry.containsBeanDefinition(strategies[i].getName())) {
				BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(strategies[i]);
				definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

				AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();

				BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, strategies[i].getName());
				BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
			}
		}

		if (!registry.containsBeanDefinition(DistLockProviderFactory.class.getName())) {
			BeanDefinitionBuilder definition = BeanDefinitionBuilder
					.genericBeanDefinition(DistLockProviderFactory.class);
			definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

			AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();

			BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition,
					DistLockProviderFactory.class.getName());
			BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
		}
		
		if(!registry.containsBeanDefinition(DistLockStrategyFactory.class.getName())){
			BeanDefinitionBuilder definition = BeanDefinitionBuilder
					.genericBeanDefinition(DistLockStrategyFactory.class);
			definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

			AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();

			BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition,
					DistLockStrategyFactory.class.getName());
			BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
		}
	}

	private void validateRefs(String[] refs, BeanDefinitionRegistry registry) throws Exception {
		// redis配置组件， 连接池配置组件，redisTemplate模板，全部由使用方控制，只校验引用bean是否存在
		for (int i = 0; i < refs.length; i++) {
			if (!registry.containsBeanDefinition(refs[i])) {
				throw new Exception(
						"distributed lock implementation must config referenced component named:" + refs[i]);
			}
		}
	}

}
