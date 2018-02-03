/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月9日
 * Id: AuthServiceImpl.java, 2017年11月9日 下午2:18:37 Administrator
 */
package com.yryz.quanhu.user.service.impl;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yryz.common.config.AuthConfig;
import com.yryz.common.constant.DevType;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.JsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.support.config.api.BasicConfigApi;
import com.yryz.quanhu.user.contants.Constants;
import com.yryz.quanhu.user.contants.TokenCheckEnum;
import com.yryz.quanhu.user.dao.AuthRedisDao;
import com.yryz.quanhu.user.dto.AuthRefreshDTO;
import com.yryz.quanhu.user.dto.AuthTokenDTO;
import com.yryz.quanhu.user.service.AuthService;
import com.yryz.quanhu.user.utils.TokenUtils;
import com.yryz.quanhu.user.vo.AuthTokenVO;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月9日 下午2:18:37
 * @Description token认证管理
 */
@Service
public class AuthServiceImpl implements AuthService {
	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Autowired
	private AuthRedisDao redisDao;
	@Autowired
	private AuthConfig authConfig;
	@Reference
	private BasicConfigApi configApi;
	
	@Override
	public TokenCheckEnum checkToken(AuthTokenDTO tokenDTO) {
		String token = tokenDTO.getToken();

		// 比对redis token
		AuthTokenVO tokenVO = redisDao.getToken(tokenDTO);
		if (tokenVO == null) {
			return TokenCheckEnum.NO_TOKEN;
		}
		// web token过期就算错误
		if (StringUtils.equals(token, tokenVO.getToken())) {
			if (System.currentTimeMillis() < tokenVO.getExpireAt()) {
				return TokenCheckEnum.SUCCESS;
			} else {
				return TokenCheckEnum.EXPIRE;
			}
		} else {
			return TokenCheckEnum.ERROR;
		}
	}

	@Override
	public TokenCheckEnum checkToken(AuthRefreshDTO tokenDTO) {
		String token = tokenDTO.getToken();
		String refreshToken = tokenDTO.getRefreshToken();


		// 比对redis token
		AuthTokenVO tokenVO = redisDao.getToken(tokenDTO);
		if (tokenVO == null) {
			return TokenCheckEnum.NO_TOKEN;
		}
		// 校验短期token
		if (StringUtils.equals(token, tokenVO.getToken())) {
			if (System.currentTimeMillis() < tokenVO.getExpireAt()) {
				return TokenCheckEnum.SUCCESS;
			} else {
				// refreshToken存在时，校验长期token，否则判断token为过期
				if(StringUtils.isNotBlank(refreshToken)){
					if (StringUtils.equals(refreshToken, tokenVO.getRefreshToken())
							&& System.currentTimeMillis() < tokenVO.getRefreshExpireAt()) {
						return TokenCheckEnum.EXPIRE;
					} else {
						return TokenCheckEnum.ERROR;
					}
				} else {
					return TokenCheckEnum.EXPIRE;
				}
			}
		} else {
			return TokenCheckEnum.ERROR;
		}
	}

	@Override
	public AuthTokenVO getToken(AuthTokenDTO tokenDTO) {
		String token = null;
		// 拿配置
		AuthConfig rangeConfig = getAuthConfig(tokenDTO.getAppId());
		
		Long expireAt = rangeConfig.getWebTokenExpire().longValue() * 3600 * 1000 + System.currentTimeMillis();
		AuthTokenVO tokenVO;
		tokenVO = redisDao.getToken(tokenDTO);
		logger.info("[getToken]:tokenDTO:{},tokenVO:{}",JsonUtils.toFastJson(tokenDTO),JsonUtils.toFastJson(tokenVO));
		// token过期或者设置登录刷新token都重新获取新token
		if (tokenVO == null || (tokenVO.getExpireAt() != null && tokenVO.getExpireAt() < System.currentTimeMillis())
				|| BooleanUtils.toBoolean(tokenDTO.isRefreshLogin())) {
			token = TokenUtils.constructToken(tokenDTO.getUserId().toString());
			tokenDTO.setToken(token);
			redisDao.setToken(tokenDTO, expireAt);
		} else {
			token = tokenVO.getToken();
		}
		try {
			tokenVO = new AuthTokenVO(tokenDTO.getUserId(), token,expireAt);
		} catch (Exception e) {
			logger.error("[des decrypt token]", e);
			throw new QuanhuException(ExceptionEnum.BusiException);
		}
		return tokenVO;
	}

	@Override
	public AuthTokenVO getToken(AuthRefreshDTO refreshDTO) {
		String token = null;
		String refreshToken = null;
		// 拿配置
		AuthConfig rangeConfig = getAuthConfig(refreshDTO.getAppId());
		
		//计算token和refreshToken过期时间，不使用就从缓存中获取返回
		Long expireAt = rangeConfig.getTokenExpire().longValue() * 3600 * 1000 + System.currentTimeMillis();
		Long refreshExpireAt = rangeConfig.getRefreshExpire().longValue() * 3600 * 24 * 1000 + System.currentTimeMillis();
		
		AuthTokenVO tokenVO;
		
		tokenVO = redisDao.getToken(refreshDTO);
		logger.info("[getToken]:refreshDTO:{},tokenVO:{}",JsonUtils.toFastJson(refreshDTO),JsonUtils.toFastJson(tokenVO));
		// 长期token过期或者设置登录刷新token都重新获取新token
		if (tokenVO == null
				|| (tokenVO.getRefreshExpireAt() != null && tokenVO.getRefreshExpireAt() < System.currentTimeMillis())
				|| BooleanUtils.toBoolean(refreshDTO.isRefreshLogin())) {
			token = TokenUtils.constructToken(refreshDTO.getUserId().toString());
			refreshToken = TokenUtils.constructToken(refreshDTO.getUserId().toString());
			refreshDTO.setToken(token);
			refreshDTO.setRefreshToken(refreshToken);
			redisDao.setToken(refreshDTO, expireAt, refreshExpireAt);
			//只要登录操作就删除刷新标识
			redisDao.deleteRefreshFlag(refreshDTO.getUserId(), refreshDTO.getAppId(), refreshDTO.getType());
		} else {
			token = tokenVO.getToken();
			//刷新短期token，分两种情况
			//普通刷新只更新token，
			//满足延长refreshToken条件都两个token都更新过期时间重新计算
			if (refreshDTO.isRefreshTokenFlag()) {
				token = TokenUtils.constructToken(refreshDTO.getUserId().toString());
				refreshDTO.setToken(token);
				refreshDTO.setRefreshToken(tokenVO.getRefreshToken());
				
				//满足更新refreshToken的条件,更新refreshToken和过期时间并返回				
				if(checkRefreshTokenDelayUpdate(tokenVO, rangeConfig)){
					refreshToken = TokenUtils.constructToken(refreshDTO.getUserId().toString());
					refreshDTO.setRefreshToken(refreshToken);
					tokenVO.setRefreshToken(refreshToken);					
				}
				//不满足refreshToken刷新，就不更新refresh任何参数，
				else{
					refreshExpireAt = tokenVO.getRefreshExpireAt();
				}
				redisDao.setToken(refreshDTO, expireAt, refreshExpireAt);
			}
			refreshToken = tokenVO.getRefreshToken();
		}
		try {
			tokenVO = new AuthTokenVO(refreshDTO.getUserId(), token, expireAt, refreshToken, refreshExpireAt);
		} catch (Exception e) {
			logger.error("[des decrypt token]", e);
			throw new QuanhuException(ExceptionEnum.BusiException);
		}
		return tokenVO;
	}
	
	@Override
	public boolean checkRefreshFlag(Long userId, String appId, DevType devType) {
		return redisDao.getRefreshFlag(userId, appId, devType) <= 1;
	}
	
	@Override
	public void delToken(Long userId, String appId) {
		redisDao.delToken(new AuthTokenDTO(userId, DevType.ANDROID, appId));
		redisDao.delToken(new AuthTokenDTO(userId, DevType.WAP, appId));
		redisDao.delToken(new AuthTokenDTO(userId, DevType.WEB, appId));
	}
	
	/**
	 * 获取用户认证配置
	 * @param appId
	 * @return
	 */
	private AuthConfig getAuthConfig(String appId){
		String configName = String.format("%s.%s", Constants.AUTH_CONFIG_NAME,appId);
		String configValue = ResponseUtils.getResponseData(configApi.getValue(configName));
		logger.info("[getAuthConfig]:configName:{},configValue:{}",configName,configValue);
		AuthConfig config = JSON.parseObject(configValue, new TypeReference<AuthConfig>(){});
		if(config == null){
			config = authConfig;
		}
		return config;
	}
	
	/**
	 * 检查refreshToken是否满足延期更新的条件
	 * @param tokenVO
	 * @param config
	 * @return
	 */
	private boolean checkRefreshTokenDelayUpdate(AuthTokenVO tokenVO,AuthConfig config){
		long refreshExpireAt = tokenVO.getRefreshExpireAt();
		long refreshTokenDelayExpireTime = config.getRefreshTokenDelayExpireTime()*3600*1000;
		long nowTime = System.currentTimeMillis();
		if(refreshTokenDelayExpireTime >= refreshExpireAt - nowTime){
			return true;
		}
		return false;
	}


}
