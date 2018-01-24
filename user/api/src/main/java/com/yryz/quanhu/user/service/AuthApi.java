/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月8日
 * Id: AuthApi.java, 2018年1月8日 上午9:15:51 Administrator
 */
package com.yryz.quanhu.user.service;

import com.yryz.common.constant.DevType;
import com.yryz.common.response.Response;
import com.yryz.quanhu.user.contants.RedisConstants;
import com.yryz.quanhu.user.dto.AuthRefreshDTO;
import com.yryz.quanhu.user.dto.AuthTokenDTO;
import com.yryz.quanhu.user.vo.AuthTokenVO;

/**
 * 用户登录认证服务
 * @author danshiyu
 * @version 1.0
 * @date 2018年1月8日 上午9:15:51
 */
public interface AuthApi {
	/**
	 * 
	 * @param userId 用户id
	 * @param appId 应用id
	 * @param devType 客户端设备类型
	 * @return
	 */
	static String cacheKey(Long userId, String appId, DevType devType){
		return String.format("%s.%s.%s.%s", RedisConstants.AUTH_TOKEN,userId.toString(),appId,devType.getLabel());
	};
	/**
	 * 检查web端token
	 * @param tokenDTO
	 */
	Response<Integer> checkToken(AuthTokenDTO tokenDTO);
	
	/**
	 * 检查app端token
	 * @param refreshDTO
	 */
	Response<Integer> checkToken(AuthRefreshDTO refreshDTO);
	
	/**
	 * 创建、刷新token<br/>
	 * token过期强制获取新的token
	 * @param tokenDTO
	 * @return
	 * @Description web端登录token获取
	 */
	Response<AuthTokenVO> getToken(AuthTokenDTO tokenDTO);
	
	/**
	 * 创建token<br/>
	 * 长期token过期强制获取新的token
	 * @param refreshDTO
	 * @return
	 * @Description app端登录token获取
	 */
	Response<AuthTokenVO> getToken(AuthRefreshDTO refreshDTO);
	
	/**
	 * 刷新token
	 * @param refreshDTO
	 * @return
	 * @Description app端刷新token
	 */
	Response<AuthTokenVO> refreshToken(AuthRefreshDTO refreshDTO);
}
