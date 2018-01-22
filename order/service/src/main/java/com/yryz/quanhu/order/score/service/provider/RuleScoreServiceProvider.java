package com.yryz.quanhu.order.score.service.provider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import com.yryz.quanhu.order.score.rule.service.RuleScoreService;
import com.yryz.quanhu.order.score.type.ScoreTypeEnum;

@Configuration
public class RuleScoreServiceProvider implements ApplicationContextAware , InitializingBean{
	
	private static final ConcurrentHashMap<ScoreTypeEnum, RuleScoreService> RuleScoreServices = new ConcurrentHashMap<>();

	ApplicationContext applicationContext;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, RuleScoreService> services = this.applicationContext.getBeansOfType(RuleScoreService.class);
		services.forEach((key , service)->{
			RuleScoreServices.putIfAbsent(service.getScoreType(), service);
		});
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public RuleScoreService getService(ScoreTypeEnum ste){
		return RuleScoreServices.get(ste);
	}

}
