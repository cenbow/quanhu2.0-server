package com.yryz.quanhu.order.score.service;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.redis.core.RedisTemplate;

import com.yryz.quanhu.score.vo.EventAcount;


public interface EventAcountService {
	
	Long save(EventAcount ea);
	
	int update(EventAcount ea);
	
	EventAcount getLastAcount(String custId);
	
	Long initAcount(String custId);
	
    String redislocksset(RedisTemplate<String, String> redisTemplate, String key, String value, String nxxx, String expx, long time );

}
