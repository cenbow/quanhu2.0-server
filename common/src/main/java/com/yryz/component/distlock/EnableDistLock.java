package com.yryz.component.distlock;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.yryz.component.distlock.spi.DistLockProvider;
import com.yryz.component.distlock.spi.DistLockStrategy;
import com.yryz.component.distlock.spi.provider.DistLockStrategyFailLoop;
import com.yryz.component.distlock.spring.DistLockRegistrar;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(DistLockRegistrar.class)
public @interface EnableDistLock {

	/**
	 * 分布式锁实现的客户端引用bean名称
	 * 
	 * @return
	 */
	String[] refs() default {};

	Class<? extends DistLockProvider> provider();

	Class<? extends DistLockStrategy>[] strategies() default { DistLockStrategyFailLoop.class };
}
