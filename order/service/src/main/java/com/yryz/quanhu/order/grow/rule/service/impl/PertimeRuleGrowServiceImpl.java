package com.yryz.quanhu.order.grow.rule.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yryz.quanhu.grow.entity.GrowEventInfo;
import com.yryz.quanhu.order.grow.manage.service.GrowLevelManageService;
import com.yryz.quanhu.order.grow.rule.service.PertimeRuleGrowService;
import com.yryz.quanhu.order.grow.service.GrowFlowService;
import com.yryz.quanhu.order.score.service.EventAcountService;

import redis.clients.jedis.ShardedJedis;

@Transactional
@Service
public class PertimeRuleGrowServiceImpl extends BaseRuleGrowServiceImpl
		implements PertimeRuleGrowService {

	@Autowired
	GrowFlowService growFlowService;
	
	@Autowired
	EventAcountService eventAcountService;
	
	@Autowired
	GrowLevelManageService growLevelManageService;
	
    @Autowired
    private StringRedisTemplate redisTemplate;
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public boolean processStatus(String userId, String eventCode , String resourceId, GrowEventInfo sei ,  double amount) {
		saveGrowFlow(userId, eventCode, sei , amount);
		return true;
	}

}
