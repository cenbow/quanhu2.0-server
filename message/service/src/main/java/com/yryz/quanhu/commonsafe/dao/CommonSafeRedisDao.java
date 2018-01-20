/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年12月5日
 * Id: CommSafeRedisDao.java, 2017年12月5日 下午6:03:56 Administrator
 */
package com.yryz.quanhu.commonsafe.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.yryz.common.exception.RedisOptException;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.commonsafe.api.CommonSafeApi;
import com.yryz.quanhu.commonsafe.constants.Constants;
import com.yryz.quanhu.commonsafe.utils.DateUtils;
import com.yryz.quanhu.configuration.VerifyCodeConfigVO;
import com.yryz.quanhu.support.id.constants.RedisConstants;


/**
 * 公共安全缓存管理
 * @author danshiyu
 * @version 1.0
 * @date 2017年12月5日 下午6:03:56
 */
@Repository
public class CommonSafeRedisDao {
	private static final Logger logger = LoggerFactory.getLogger(CommonSafeRedisDao.class);

	@Autowired
	RedisTemplateBuilder templateBuilder;
	
	/* ************** ip限制begin ******************** */
	/**
	 * 
	 * 更新某个ip执行业务的次数
	 * 
	 * @param ip
	 * @param appId
	 * @param serviceType
	 */
	public void saveIpCount(String ip,String appId,String serviceType) {
		String key = CommonSafeApi.getIpLimitKey(ip,appId,serviceType);
		try {
			RedisTemplate<String, Integer> template = templateBuilder.buildRedisTemplate(Integer.class);
			template.opsForValue().increment(key, 1);
			if(template.getExpire(key) < 0){
				template.expire(key, DateUtils.getNowToNextDayTime(), TimeUnit.SECONDS);
			}
		} catch (Exception e) {
			logger.error("[saveIpCount]", e);
			throw new RedisOptException("[saveIpCount]", e.getCause());
		}
	}
	
	/**
	 * 
	 * 获取当前ip执行计数
	 * 
	 * @param ip
	 * @param appId
	 * @param serviceType
	 * @return
	 */
	public int getIpCount(String ip,String appId,String serviceType) {
		String key = CommonSafeApi.getIpLimitKey(ip,appId,serviceType);
		try {
			RedisTemplate<String, Integer> template = templateBuilder.buildRedisTemplate(Integer.class);
			return template.opsForValue().get(key);
		} catch (Exception e) {
			logger.error("getIpCount", e);
			throw new RedisOptException("[getIpCount]", e.getCause());
		}
	}
	
	/**
	 * 
	 * 保存每个ip执行业务的时间
	 * 
	 * @param ip
	 * @param appId
	 * @param serviceType
	 * @return
	 */
	public void saveIpRunTime(String ip,String appId,String serviceType) {
		String key = CommonSafeApi.getIpRunTimeKey(ip, appId, serviceType);
		try {
			RedisTemplate<String, Long> template = templateBuilder.buildRedisTemplate(Long.class);
			template.opsForValue().set(key, System.currentTimeMillis());
			if (template.getExpire(key) < 0) {
				template.expire(key, Constants.IP_TIME_EXPIRE, TimeUnit.SECONDS);
			}
		} catch (Exception e) {
			logger.error("saveIpSendTime", e);
			throw new RedisOptException(e);
		} 
	}
	
	/**
	 * 
	 * 查询每个ip执行业务的时间
	 * 
	 * @param ip	 
	 * @param appId
	 * @param serviceType
	 * @return
	 */
	public long getIpRunTime(String ip,String appId,String serviceType) {
		String key = CommonSafeApi.getIpRunTimeKey(ip, appId,serviceType);
		try {
			RedisTemplate<String, Long> template = templateBuilder.buildRedisTemplate(Long.class);
			return template.opsForValue().get(key);
		} catch (Exception e) {
			logger.error("getIpSendTime", e);
			throw new RedisOptException(e);
		}
	}
	
	/* ************** ip限制end ******************** */
	
	/* ************** 图形验证码begin ******************** */
	/**
	 * 保存图形验证码检验次数
	 * @param key 验证码载体 例如:邮箱、手机号
	 * @param appId 
	 * @param configVO 配置信息
	 */
	public void saveImgCodeCount(String key,String appId,VerifyCodeConfigVO configVO){
		String rediskey = CommonSafeApi.getImgCodeCount(key, appId);
		try {
			RedisTemplate<String, Integer> template = templateBuilder.buildRedisTemplate(Integer.class);
			template.opsForValue().increment(key, 1);
			if(template.getExpire(key) < 0){
				template.expire(rediskey, configVO.getImgCodeNumLimit()*configVO.getImgCodeExpireTime(),TimeUnit.SECONDS);
			}
		} catch (Exception e) {
			logger.error("[saveImgCodeCount]", e);
			throw new RedisOptException(e);
		}
	}
	
	/**
	 * 查询图形验证码检验次数
	 * @param key 验证码载体 例如:邮箱、手机号
	 * @param appId
	 */
	public int getImgCodeCount(String key,String appId){
		String redisKey = CommonSafeApi.getImgCodeCount(key, appId);
		try {
			RedisTemplate<String, Integer> template = templateBuilder.buildRedisTemplate(Integer.class);
			return template.opsForValue().get(redisKey);
		} catch (Exception e) {
			logger.error("[getImgCodeCount]", e);
			throw new RedisOptException(e);
		}
	}
	
	/**
	 * 清除图形验证码检验次数
	 * @param key 验证码载体 例如:邮箱、手机号
	 * @param appId
	 */
	public void clearImgCodeCount(String key,String appId){
		String redisKey = CommonSafeApi.getImgCodeCount(key, appId);
		try {
			RedisTemplate<String, Integer> template = templateBuilder.buildRedisTemplate(Integer.class);
			template.delete(redisKey);
		} catch (Exception e) {
			logger.error("[clearImgCodeCount]", e);
			throw new RedisOptException(e);
		}
	}
	
	/**
	 * 保存图像验证码
	 * @param key 验证码载体 例如:邮箱、手机号
	 * @param appId
	 * @param code 验证码
	 * @param configVO 验证码配置
	 */
	public void saveImgCode(String key,String appId,String code,VerifyCodeConfigVO configVO){
		String redisKey = CommonSafeApi.getImgCode(key, appId);
		try {
			RedisTemplate<String, String> template = templateBuilder.buildRedisTemplate(String.class);
			template.opsForValue().set(redisKey, code);
			if(template.getExpire(key) < 0){
				template.expire(redisKey, configVO.getNormalCodeExpireTime(),TimeUnit.SECONDS);
			}
		} catch (Exception e) {
			logger.error("[saveImgCode]", e);
			throw new RedisOptException(e);
		}
	}
	
	/**
	 * 查询图像验证码
	 * @param key 验证码载体 例如:邮箱、手机号
	 * @param appId
	 */
	public String getImgCode(String key,String appId){
		String redisKey = CommonSafeApi.getImgCode(key, appId);
		try {
			RedisTemplate<String, String> template = templateBuilder.buildRedisTemplate(String.class);
			return template.opsForValue().get(redisKey);
		} catch (Exception e) {
			logger.error("[getImgCode]", e);
			throw new RedisOptException(e);
		}
	}

	/**
	 * 清除图像验证码
	 * @param key 验证码载体 例如:邮箱、手机号
	 * @param appId
	 */
	public void clearImgCode(String key,String appId){
		String redisKey = CommonSafeApi.getImgCode(key, appId);
		try {
			RedisTemplate<String, String> template = templateBuilder.buildRedisTemplate(String.class);
			template.delete(redisKey);
		} catch (Exception e) {
			logger.error("[clearImgCode]", e);
			throw new RedisOptException(e);
		}
	}
	
	/* ************** 图形验证码end******************** */
	
	
	/* ************** 普通验证码 ******************** */
	/**
	 * 保存普通验证码
	 * @param key  验证码载体 例如:邮箱、手机号
	 * @param appId
	 * @param serviceCode 功能码
	 * @param code 验证码
	 * @param configVO 配置
	 */
	public void saveVerifyCode(String key,String appId,int serviceCode,String code,VerifyCodeConfigVO configVO){
		String redisKey = CommonSafeApi.getVerifyCodeKey(key, appId, serviceCode);
		try {
			RedisTemplate<String, String> template = templateBuilder.buildRedisTemplate(String.class);
			template.opsForValue().set(redisKey, code);
			if(template.getExpire(redisKey) < 0){
				template.expire(redisKey, configVO.getNormalCodeExpireTime(),TimeUnit.SECONDS);
			}
		} catch (Exception e) {
			logger.error("[saveVerifyCode]", e);
			throw new RedisOptException(e);
		}
	}
	
	/**
	 * 查询普通验证码
	 * @param key  验证码载体 例如:邮箱、手机号
	 * @param appId
	 * @param serviceCode 功能码
	 */
	public String getVerifyCode(String key,String appId,int serviceCode){
		String redisKey = CommonSafeApi.getVerifyCodeKey(key, appId, serviceCode);
		try {
			RedisTemplate<String, String> template = templateBuilder.buildRedisTemplate(String.class);
			return template.opsForValue().get(redisKey);
		} catch (Exception e) {
			logger.error("[getVerifyCode]", e);
			throw new RedisOptException(e);
		}
	}
	
	/**
	 * 清理普通验证码
	 * @param key  验证码载体 例如:邮箱、手机号
	 * @param appId
	 * @param serviceCode 功能码
	 */
	public void clearVerifyCode(String key,String appId,int serviceCode){
		String redisKey = CommonSafeApi.getVerifyCodeKey(key, appId, serviceCode);
		try {
			RedisTemplate<String, String> template = templateBuilder.buildRedisTemplate(String.class);
			template.delete(redisKey);
		} catch (Exception e) {
			logger.error("[clearVerifyCode]", e);
			throw new RedisOptException(e);
		}
	}
	
	/**
	 * 保存普通验证码最后获取时间和当天总量
	 * @param key  验证码载体 例如:邮箱、手机号
	 * @param appId
	 */
	public void saveVerifyCodeTime(String key,String appId){
		String redisKey = CommonSafeApi.getVerifyCodeTimeKey(key, appId);
		try {
			RedisTemplate<String, String> template = templateBuilder.buildRedisTemplate(String.class);
			template.opsForHash().increment(redisKey, RedisConstants.VERIFY_CODE_TOTAL, 1);
			template.opsForHash().put(redisKey, RedisConstants.VERIFY_CODE_LASTTIME, String.valueOf(System.currentTimeMillis()));
			Map<String,String> map = new HashMap<>();
			map.put(RedisConstants.VERIFY_CODE_TOTAL, String.valueOf(1));
			map.put(RedisConstants.VERIFY_CODE_LASTTIME, String.valueOf(System.currentTimeMillis()));
			template.opsForHash().putAll(redisKey, map);
			if(template.getExpire(redisKey) < 0){
				template.expire(redisKey, DateUtils.getNowToNextDayTime(), TimeUnit.SECONDS);
			}
		} catch (Exception e) {
			logger.error("[saveVerifyCodeTime]", e);
			throw new RedisOptException(e);
		}
	}
	
	/**
	 * 查询普通验证码最后获取时间和当天总量
	 * @param key  验证码载体 例如:邮箱、手机号
	 * @param appId
	 */
	@SuppressWarnings("unchecked")
	public Map<String,String> getVerifyCodeTime(String key,String appId){
		String redisKey = CommonSafeApi.getVerifyCodeTimeKey(key, appId);
		try {
			RedisTemplate<String,String> template = templateBuilder.buildRedisTemplate(String.class);
			@SuppressWarnings("rawtypes")
			Map map = template.opsForHash().entries(redisKey);
			return map;
		} catch (Exception e) {
			logger.error("[getVerifyCodeTime]", e);
			throw new RedisOptException(e);
		}
	}
	
}
