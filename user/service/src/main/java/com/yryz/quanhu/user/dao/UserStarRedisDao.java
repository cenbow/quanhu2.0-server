package com.yryz.quanhu.user.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.yryz.common.exception.RedisOptException;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.user.contants.UserStarContants.StarAuditStatus;
import com.yryz.quanhu.user.entity.UserStarAuth;
import com.yryz.quanhu.user.service.UserStarApi;
import com.yryz.quanhu.user.vo.UserStarSimpleVo;

@Service
public class UserStarRedisDao {
	private static final Logger logger = LoggerFactory.getLogger(AuthRedisDao.class);
	
	/**
	 * 达人信息过期时间/天
	 */
	private static final int STAR_INFO_EXPIRE_TIME = 30;
	
	@Resource
	private RedisTemplateBuilder redisTemplateBuilder;
	
	/**
	 * 达人信息保存
	 * @param starAuth
	 */
	public void save(UserStarAuth starAuth){
		if(starAuth == null || starAuth.getAuditStatus().byteValue() != StarAuditStatus.AUDIT_SUCCESS.getStatus()){
			return;
		}
		String key = UserStarApi.getCacheKey(starAuth.getUserId().toString());
		try {
			RedisTemplate<String, UserStarAuth> redisTemplate = redisTemplateBuilder.buildRedisTemplate(UserStarAuth.class);
			redisTemplate.opsForValue().set(key, starAuth);
			redisTemplate.expire(key, STAR_INFO_EXPIRE_TIME, TimeUnit.DAYS);
		} catch (Exception e) {
			logger.error("[saveStarInfo]",e);
			throw new RedisOptException(e);
		}
	}
	
	/**
	 * 达人信息保存
	 * @param starAuths
	 */
	public void save(List<UserStarAuth> starAuths){
		if(CollectionUtils.isEmpty(starAuths)){
			return;
		}
		for(UserStarAuth auth : starAuths){
			save(auth);
		}
	}
	
	/**
	 * 查询达人简要信息
	 * @param userIds
	 * @return
	 */
	public List<UserStarSimpleVo> get(Set<String> userIds){
		if(CollectionUtils.isEmpty(userIds)){
			return null;
		}
		List<UserStarSimpleVo> simpleVos = new ArrayList<>(userIds.size());
		try {
			RedisTemplate<String, UserStarSimpleVo> redisTemplate = redisTemplateBuilder.buildRedisTemplate(UserStarSimpleVo.class);
			for(Iterator<String> iterator = userIds.iterator();iterator.hasNext();){
				String userId = iterator.next();
				String key = UserStarApi.getCacheKey(userId);
				UserStarSimpleVo simpleVo = redisTemplate.opsForValue().get(key);
				simpleVos.add(simpleVo);
			}
		} catch (Exception e) {
			logger.error("[getStarSimpleInfo]",e);
			throw new RedisOptException(e);
		}
		return simpleVos;
	}
	
	/**
	 * 查询达人简要信息
	 * @param userIds
	 * @return
	 */
	public List<UserStarAuth> getStarInfo(Set<String> userIds){
		if(CollectionUtils.isEmpty(userIds)){
			return null;
		}
		List<UserStarAuth> simpleVos = new ArrayList<>(userIds.size());
		try {
			RedisTemplate<String, UserStarAuth> redisTemplate = redisTemplateBuilder.buildRedisTemplate(UserStarAuth.class);
			for(Iterator<String> iterator = userIds.iterator();iterator.hasNext();){
				String userId = iterator.next();
				String key = UserStarApi.getCacheKey(userId);
				UserStarAuth simpleVo = redisTemplate.opsForValue().get(key);
				simpleVos.add(simpleVo);
			}
		} catch (Exception e) {
			logger.error("[getStarInfo]",e);
			throw new RedisOptException(e);
		}
		return simpleVos;
	}
	
	/**
	 * 删除达人
	 * @param userId
	 */
	public void delete(String userId){
		String key = UserStarApi.getCacheKey(userId);
		try {
			RedisTemplate<String, UserStarAuth> redisTemplate = redisTemplateBuilder.buildRedisTemplate(UserStarAuth.class);
			redisTemplate.delete(key);
		} catch (Exception e) {
			logger.error("[deleteStarInfo]",e);
			throw new RedisOptException(e);
		}
	}
}
