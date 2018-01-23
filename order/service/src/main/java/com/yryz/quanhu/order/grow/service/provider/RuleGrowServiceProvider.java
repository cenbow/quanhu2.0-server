package com.yryz.quanhu.order.grow.service.provider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.yryz.quanhu.order.grow.rule.service.RuleGrowService;
import com.yryz.quanhu.order.grow.type.GrowTypeEnum;

@Configuration
public class RuleGrowServiceProvider implements ApplicationContextAware , InitializingBean{
	
	private static final ConcurrentHashMap<GrowTypeEnum, RuleGrowService> RuleGrowServices = new ConcurrentHashMap<>();

	ApplicationContext applicationContext;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, RuleGrowService> services = this.applicationContext.getBeansOfType(RuleGrowService.class);
		services.forEach((key , service)->{
			RuleGrowServices.putIfAbsent(service.getGrowType(), service);
		});
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public RuleGrowService getService(GrowTypeEnum ste){
		return RuleGrowServices.get(ste);
	}

}
