package com.yryz.quanhu.behavior.like.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.yryz.common.context.Context;
import com.yryz.common.utils.JsonUtils;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.behavior.like.Service.LikeApi;
import com.yryz.quanhu.behavior.like.contants.LikeContants.LikeFlag;
import com.yryz.quanhu.behavior.like.entity.Like;

import ch.qos.logback.classic.Logger;

@Component
public class LikeCache {
	@Autowired
	private RedisTemplateBuilder redisTemplateBuilder;
	
	/**
	 * 点赞保存
	 * @param like
	 */
	public void saveLike(Like like){
		String key = LikeApi.getLikeListKey(like.getResourceId());
		RedisTemplate<String, String> redisTemplate = redisTemplateBuilder.buildRedisTemplate(String.class);
		redisTemplate.opsForZSet().add(key,String.valueOf(like.getUserId()), like.getCreateDate().getTime());
		redisTemplate.expire(key, getExpireDay(), TimeUnit.DAYS);
	}
	
	/**
	 * 点赞状态保存
	 * @param like
	 */
	public void saveLikeFlag(Like like){
		String flagKey = LikeApi.getLikeFlagKey(like.getResourceId());
		RedisTemplate<String, String> redisTemplate = redisTemplateBuilder.buildRedisTemplate(String.class);
		redisTemplate.opsForZSet().add(flagKey,String.valueOf(like.getUserId()),like.getLikeFlag());
		redisTemplate.expire(flagKey, getExpireDay(), TimeUnit.DAYS);
	}
	
	/**
	 * 取消点赞
	 * @param like
	 */
	public void cancelLike(Long resourceId,Long userId){
		String key = LikeApi.getLikeListKey(resourceId);
		String flagKey = LikeApi.getLikeFlagKey(resourceId);
		RedisTemplate<String, String> redisTemplate = redisTemplateBuilder.buildRedisTemplate(String.class);
		redisTemplate.opsForZSet().remove(key, userId.toString());
		redisTemplate.opsForZSet().add(flagKey, userId.toString(),(double)LikeFlag.FALSE.getFlag());
	}
	
	/**
	 * 查询点赞状态
	 * @param moduleEnum
	 * @param resourceId
	 * @param userId
	 * @return
	 */
	public Double checkLikeFlag(Long resourceId,Long userId){
		String key = LikeApi.getLikeFlagKey(resourceId);
		RedisTemplate<String, String> redisTemplate = redisTemplateBuilder.buildRedisTemplate(String.class);
		Double score = redisTemplate.opsForZSet().score(key, userId.toString());
		return score;  
	}
	
	/**
	 * 获取点赞列表
	 * @param moduleEnum
	 * @param resourceId
	 * @param start
	 * @param limit
	 * @return
	 */
	public Set<String> getLikeUserId(Long resourceId,int start,int limit){
		String key = LikeApi.getLikeListKey(resourceId);
		RedisTemplate<String, String> redisTemplate = redisTemplateBuilder.buildRedisTemplate(String.class);
		Set<String> userIds = null;
		if(start < 0){
			userIds = redisTemplate.opsForZSet().reverseRange(key, 0, limit-1);
		}else{
			userIds = redisTemplate.opsForZSet().reverseRange(key, start, start + limit-1);
		}
		return userIds;
	}
	
	/**
	 * 查询点赞时间
	 * @param resourceId
	 * @param userIds
	 * @return
	 */
	public Map<String,Long> getLikeTime(Long resourceId,Set<String> userIds){
		String key = LikeApi.getLikeListKey(resourceId);
		RedisTemplate<String, String> redisTemplate = redisTemplateBuilder.buildRedisTemplate(String.class);
		Map<String,Long> map = new HashMap<>();
		for(Iterator<String> iterator = userIds.iterator();iterator.hasNext();){
			String userId = iterator.next();
			Double createTime = redisTemplate.opsForZSet().score(key, userId);
			map.put(userId, createTime.longValue());
		}
		return map;
	}
	
	/**
	 * 获取点赞过期时间
	 * 
	 * @return
	 */
	private Integer getExpireDay() {
		return NumberUtils.toInt(Context.getProperty("like.expireDays"), 180);
	}
}
