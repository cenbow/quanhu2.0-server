package com.yryz.quanhu.order.score.service;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.redis.core.RedisTemplate;

import com.yryz.quanhu.score.vo.EventAcount;


public interface EventAcountService {
	
	Long save(EventAcount ea);
	
	int update(EventAcount ea);
	
	EventAcount getLastAcount(String userId);
	
	Long initAcount(String userId);
	
    String redislocksset(RedisTemplate<String, String> redisTemplate, String key, String value, String nxxx, String expx, long time );
	  
	/**
	 * 
	 * @param userId
	 * @param score
	 * @param eventCode
	 * @return
	 * @Description 消费积分
	 */
    int consumeScore(String userId , int score , String eventCode);

}
