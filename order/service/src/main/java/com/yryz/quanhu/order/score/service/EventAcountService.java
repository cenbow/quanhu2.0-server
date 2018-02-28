package com.yryz.quanhu.order.score.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.redis.core.RedisTemplate;

import com.yryz.quanhu.score.entity.ScoreFlow;
import com.yryz.quanhu.score.entity.ScoreFlowQuery;
import com.yryz.quanhu.score.vo.EventAcount;


public interface EventAcountService {
	
	Long save(EventAcount ea);
	
	int update(EventAcount ea);
	
	int saveOrUpdate(EventAcount ea);
	
	EventAcount getLastAcount(String userId);
	
	Long initAcount(String userId);
	
    String redislocksset(RedisTemplate<String, String> redisTemplate, String key, String value, String nxxx, String expx, long time );
    
	List<EventAcount> getPage(ScoreFlowQuery sfq);

}
