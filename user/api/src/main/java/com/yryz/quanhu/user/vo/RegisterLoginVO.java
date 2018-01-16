/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月10日
 * Id: RegisterLoginVO.java, 2017年11月10日 上午11:47:20 Administrator
 */
package com.yryz.quanhu.user.vo;

import java.io.Serializable;

import com.yryz.quanhu.auth.vo.AuthTokenVO;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月10日 上午11:47:20
 * @Description 登录注册使用vo
 */
@SuppressWarnings("serial")
public class RegisterLoginVO implements Serializable {
	/**
	 * token信息
	 */
	private AuthTokenVO authInfo;
	/**
	 * 用户基本信息
	 */
	private UserSimpleVO user;
	public AuthTokenVO getAuthInfo() {
		return authInfo;
	}
	public void setAuthInfo(AuthTokenVO authInfo) {
		this.authInfo = authInfo;
	}
	public UserSimpleVO getUser() {
		return user;
	}
	public void setUser(UserSimpleVO user) {
		this.user = user;
	}
	/**
	 * 
	 * @exception 
	 */
	public RegisterLoginVO() {
		super();
	}
	/**
	 * @param authInfo
	 * @param user
	 * @exception 
	 */
	public RegisterLoginVO(AuthTokenVO authInfo, UserSimpleVO user) {
		super();
		this.authInfo = authInfo;
		this.user = user;
	}
	@Override
	public String toString() {
		return "RegisterLoginVO [authInfo=" + authInfo + ", user=" + user + "]";
	}
}
