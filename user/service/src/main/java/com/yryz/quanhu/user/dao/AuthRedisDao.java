/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月9日
 * Id: AuthRedisDao.java, 2017年11月9日 下午2:17:32 Administrator
 */
package com.yryz.quanhu.user.dao;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.yryz.common.exception.RedisOptException;
import com.yryz.quanhu.user.dto.AuthRefreshDTO;
import com.yryz.quanhu.user.dto.AuthTokenDTO;
import com.yryz.quanhu.user.service.AuthApi;
import com.yryz.quanhu.user.vo.AuthTokenVO;

/**
 * 认证缓存管理
 * 
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月9日 下午2:17:32
 */
@Service
public class AuthRedisDao {
	private static Logger logger = LoggerFactory.getLogger(AuthRedisDao.class);

	@Resource
	private RedisTemplate<String, AuthTokenVO> redisTemplate;

	/**
	 * web端token设置
	 * 
	 * @param tokenDTO 
	 * @param expireAt token过期时间
	 */
	public void setToken(AuthTokenDTO tokenDTO, Long expireAt) {
		String key = AuthApi.cacheKey(tokenDTO.getUserId(), tokenDTO.getAppId(), tokenDTO.getType());
		try {
			AuthTokenVO tokenVO = new AuthTokenVO(tokenDTO.getUserId(), tokenDTO.getToken(), expireAt);
			redisTemplate.opsForValue().set(key, tokenVO);
		} catch (Exception e) {
			logger.error("TokenRedis.addToken", e);
			throw new RedisOptException("[TokenRedis.addToken]", e.getCause());
		}
	}
	
	/**
	 * auth2.0 token设置
	 * @param refreshDTO
	 * @param expireAt 短期token过期时间
	 * @param refreshExpireAt 长期token过期时间
	 */
	public void setToken(AuthRefreshDTO refreshDTO, Long expireAt, Long refreshExpireAt) {
		String key =  AuthApi.cacheKey(refreshDTO.getUserId(), refreshDTO.getAppId(), refreshDTO.getType());
		try {
			AuthTokenVO tokenVO = new AuthTokenVO(refreshDTO.getUserId(), refreshDTO.getToken(), expireAt,
					refreshDTO.getRefreshToken(), refreshExpireAt);
			redisTemplate.opsForValue().set(key, tokenVO);
		} catch (Exception e) {
			logger.error("TokenRedis.addToken", e);
			throw new RedisOptException("[TokenRedis.addToken]", e.getCause());
		}
	}
	
	/**
	 * 查询Token
	 * 
	 * @param tokenDTO
	 * @return
	 */
	public AuthTokenVO getToken(AuthTokenDTO tokenDTO) {
		String key = AuthApi.cacheKey(tokenDTO.getUserId(), tokenDTO.getAppId(), tokenDTO.getType());
		try {
			AuthTokenVO tokenVO = redisTemplate.opsForValue().get(key);
				return tokenVO;
		} catch (Exception e) {
			logger.error("TokenRedis.getToken", e);
			throw new RedisOptException("[TokenRedis.getToken]", e.getCause());
		}
	}

	/**
	 * 删除token
	 * 
	 * @param custId
	 * @param type
	 * @throws Exception
	 */
	public void delToken(AuthTokenDTO tokenDTO) {
		String key = AuthApi.cacheKey(tokenDTO.getUserId(), tokenDTO.getAppId(), tokenDTO.getType());
		try {
			redisTemplate.delete(key);
		} catch (Exception e) {
			logger.error("TokenRedis.delToken", e);
			throw new RedisOptException("[TokenRedis.delToken]", e.getCause());
		} 
	}

}
