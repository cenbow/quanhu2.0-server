/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月9日
 * Id: AuthService.java, 2017年11月9日 下午2:18:03 Administrator
 */
package com.yryz.quanhu.auth.service;

import com.yryz.quanhu.auth.constants.TokenCheckEnum;
import com.yryz.quanhu.auth.dto.AuthRefreshDTO;
import com.yryz.quanhu.auth.dto.AuthTokenDTO;
import com.yryz.quanhu.auth.vo.AuthTokenVO;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月9日 下午2:18:03
 * @Description token认证
 */
public interface AuthService {
	/**
	 * 检查web端token
	 * @param tokenDTO
	 */
	public TokenCheckEnum checkToken(AuthTokenDTO tokenDTO);
	
	/**
	 * 检查app端token
	 * @param tokenDTO
	 */
	public TokenCheckEnum checkToken(AuthRefreshDTO refreshDTO);
	
	/**
	 * 创建、刷新token
	 * @param custId
	 * @param refresh
	 * @return
	 * @Description web端获取
	 */
	AuthTokenVO getToken(AuthTokenDTO tokenDTO);
	
	/**
	 * 创建、刷新token
	 * @param custId
	 * @param refresh
	 * @param type
	 * @return
	 * @Description app端token获取
	 */
	AuthTokenVO getToken(AuthRefreshDTO refreshDTO);
	
	/**
	 * 删除当前应用下所有端token 后台踢人下线
	 * @param userId 用户id
	 * @param appId 应用id
	 */
	public void delToken(String userId,String appId);
	
}
