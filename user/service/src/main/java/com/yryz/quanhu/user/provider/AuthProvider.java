/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月5日
 * Id: AuthApiImpl.java, 2018年1月5日 下午8:34:30 Administrator
 */
package com.yryz.quanhu.user.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.exception.RedisOptException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.user.contants.TokenCheckEnum;
import com.yryz.quanhu.user.dto.AuthRefreshDTO;
import com.yryz.quanhu.user.dto.AuthTokenDTO;
import com.yryz.quanhu.user.service.AuthApi;
import com.yryz.quanhu.user.service.AuthService;
import com.yryz.quanhu.user.vo.AuthTokenVO;

/**
 * 用户认证服务
 * 
 * @author danshiyu
 * @version 1.0
 * @date 2018年1月5日 下午8:34:30
 */
@Service(interfaceClass=AuthApi.class)
public class AuthProvider implements AuthApi {
	private static final Logger logger = LoggerFactory.getLogger(AuthProvider.class);

	@Autowired
	private AuthService authService;


	@Override
	public Response<Integer> checkToken(AuthTokenDTO tokenDTO) {
		try {
			checkParam(tokenDTO);
			if (StringUtils.isBlank(tokenDTO.getToken())) {
				throw QuanhuException.busiError(ExceptionEnum.PARAM_MISSING.getCode(),"token不能为空");
			}
			return ResponseUtils.returnObjectSuccess(authService.checkToken(tokenDTO).getStatus());
		} catch (RedisOptException e) {
			return ResponseUtils.returnException(e);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("检查token未知异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<Integer> checkToken(AuthRefreshDTO refreshDTO) {
		try {
			checkParam(refreshDTO);
			return ResponseUtils.returnObjectSuccess(authService.checkToken(refreshDTO).getStatus());
		} catch (RedisOptException e) {
			return ResponseUtils.returnException(e);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("检查token未知异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<AuthTokenVO> getToken(AuthTokenDTO tokenDTO) {
		try {
			checkParam(tokenDTO);
			return ResponseUtils.returnObjectSuccess(authService.getToken(tokenDTO));
		} catch (RedisOptException e) {
			return ResponseUtils.returnException(e);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("获取token未知异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<AuthTokenVO> getToken(AuthRefreshDTO refreshDTO) {
		try {
			checkParam(refreshDTO);
			return ResponseUtils.returnObjectSuccess(authService.getToken(refreshDTO));
		} catch (RedisOptException e) {
			return ResponseUtils.returnException(e);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("获取token未知异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<AuthTokenVO> refreshToken(AuthRefreshDTO refreshDTO) {
		try {
			checkParam(refreshDTO);
			if (StringUtils.isBlank(refreshDTO.getToken())) {
				throw QuanhuException.busiError(ExceptionEnum.PARAM_MISSING.getCode(),"token不能为空");
			}
			if (StringUtils.isBlank(refreshDTO.getRefreshToken())) {
				throw QuanhuException.busiError(ExceptionEnum.PARAM_MISSING.getCode(),"refreshToken不能为空");
			}
			TokenCheckEnum checkEnum = authService.checkToken(refreshDTO);
			if (checkEnum != TokenCheckEnum.EXPIRE && checkEnum != TokenCheckEnum.SUCCESS) {
				throw QuanhuException.busiError("登录状态不合法不允许刷新token");
			}
			refreshDTO.setRefreshTokenFlag(true);
			return ResponseUtils.returnObjectSuccess(authService.getToken(refreshDTO));
		} catch (RedisOptException e) {
			return ResponseUtils.returnException(e);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("刷新短期token未知异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	private void checkParam(AuthTokenDTO tokenDTO) {
		if (tokenDTO == null || StringUtils.isBlank(tokenDTO.getAppId())) {
			throw QuanhuException.busiError(ExceptionEnum.PARAM_MISSING.getCode(),"appId不能为空");
		}
		if (tokenDTO.getUserId() == null) {
			throw QuanhuException.busiError(ExceptionEnum.PARAM_MISSING.getCode(),"userId不能为空");
		}
		if (tokenDTO.getType() == null) {
			throw QuanhuException.busiError(ExceptionEnum.PARAM_MISSING.getCode(),"devType不能为空");
		}
	}

}
