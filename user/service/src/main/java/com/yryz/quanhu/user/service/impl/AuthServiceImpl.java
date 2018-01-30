/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月9日
 * Id: AuthServiceImpl.java, 2017年11月9日 下午2:18:37 Administrator
 */
package com.yryz.quanhu.user.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yryz.common.config.AuthConfig;
import com.yryz.common.constant.DevType;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.utils.StringUtils;
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
	
	@Override
	public TokenCheckEnum checkToken(AuthTokenDTO tokenDTO) {
		String token = null;
		// token解析
		try {
			token = TokenUtils.getTokenByDesToken(tokenDTO.getToken(), tokenDTO.getUserId().toString());
		} catch (IOException e) {
			logger.error("[des decrypt token]", e);
			return TokenCheckEnum.INVALID;
		} catch (Exception e) {
			logger.error("[des decrypt token]", e);
			return TokenCheckEnum.INVALID;
		}

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
		String token = null;
		String refreshToken = null;
		// token解析
		try {
			token = TokenUtils.getTokenByDesToken(tokenDTO.getToken(), tokenDTO.getUserId().toString());
			refreshToken = TokenUtils.getTokenByDesToken(tokenDTO.getRefreshToken(), tokenDTO.getUserId().toString());
		} catch (IOException e) {
			logger.error("[des decrypt token]", e);
			return TokenCheckEnum.INVALID;
		} catch (Exception e) {
			logger.error("[des decrypt token]", e);
			return TokenCheckEnum.INVALID;
		}

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
		Long expireAt = authConfig.getWebTokenExpire().longValue() * 3600 * 1000 + System.currentTimeMillis();
		AuthTokenVO tokenVO;
		tokenVO = redisDao.getToken(tokenDTO);

		// token过期或者设置登录刷新token都重新获取新token
		if (tokenVO == null || (tokenVO.getExpireAt() != null && tokenVO.getExpireAt() < System.currentTimeMillis())
				|| (tokenDTO.isRefreshLogin() != null && tokenDTO.isRefreshLogin())) {
			token = TokenUtils.constructToken(tokenDTO.getUserId().toString());
			tokenDTO.setToken(token);
			redisDao.setToken(tokenDTO, expireAt);
		} else {
			token = tokenVO.getToken();
		}
		try {
			tokenVO = new AuthTokenVO(tokenDTO.getUserId(), TokenUtils.getDesToken(tokenDTO.getUserId().toString(), token),
					expireAt);
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
		
		Long expireAt = authConfig.getTokenExpire().longValue() * 3600 * 1000 + System.currentTimeMillis();
		Long refreshExpireAt = authConfig.getRefreshExpire().longValue() * 3600 * 24 * 1000 + System.currentTimeMillis();
		System.out.println("expireAt="+expireAt);
		System.out.println("refreshExpireAt="+refreshExpireAt);
		AuthTokenVO tokenVO;
		tokenVO = redisDao.getToken(refreshDTO);

		// 长期token过期或者设置登录刷新token都重新获取新token
		if (tokenVO == null
				|| (tokenVO.getRefreshExpireAt() != null && tokenVO.getRefreshExpireAt() < System.currentTimeMillis())
				|| (refreshDTO.isRefreshLogin() != null && refreshDTO.isRefreshLogin())) {
			token = TokenUtils.constructToken(refreshDTO.getUserId().toString());
			refreshToken = TokenUtils.constructToken(refreshDTO.getUserId().toString());
			
			
			refreshDTO.setToken(token);
			refreshDTO.setRefreshToken(refreshToken);
			redisDao.setToken(refreshDTO, expireAt, refreshExpireAt);
		} else {
			token = tokenVO.getToken();
			// 刷新短期token
			if (refreshDTO.isRefreshTokenFlag()) {
				token = TokenUtils.constructToken(refreshDTO.getUserId().toString());
				refreshDTO.setToken(token);
				refreshDTO.setRefreshToken(tokenVO.getRefreshToken());
				refreshExpireAt = tokenVO.getRefreshExpireAt();
				redisDao.setToken(refreshDTO, expireAt, refreshExpireAt);
			}
			refreshToken = tokenVO.getRefreshToken();
		}
		try {
			tokenVO = new AuthTokenVO(refreshDTO.getUserId(), TokenUtils.getDesToken(refreshDTO.getUserId().toString(), token),
					expireAt, TokenUtils.getDesToken(refreshDTO.getUserId().toString(),refreshToken), refreshExpireAt);
		} catch (Exception e) {
			logger.error("[des decrypt token]", e);
			throw new QuanhuException(ExceptionEnum.BusiException);
		}
		return tokenVO;
	}

	@Override
	public void delToken(Long userId, String appId) {
		redisDao.delToken(new AuthTokenDTO(userId, DevType.ANDROID, appId));
		redisDao.delToken(new AuthTokenDTO(userId, DevType.WAP, appId));
		redisDao.delToken(new AuthTokenDTO(userId, DevType.WEB, appId));
	}

}
