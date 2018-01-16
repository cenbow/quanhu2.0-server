/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月5日
 * Id: AuthTokenDTO.java, 2018年1月5日 下午3:15:16 Administrator
 */
package com.yryz.quanhu.auth.dto;

import java.io.Serializable;

import com.yryz.common.constant.DevType;

/**
 * web端token 入参dto
 * @author danshiyu
 * @version 1.0
 * @date 2018年1月5日 下午3:15:16
 */
@SuppressWarnings("serial")
public class AuthTokenDTO implements Serializable {
	/**
	 * 用户账户Id
	 */
	private String userId;
	/**
	 * 用户访问类型
	 */
	private DevType type;
	/**
	 * 应用id
	 */
	private String appId;
	/**
	 * 普通token
	 */
	private String token;
	/**
	 * 登录是否刷新token
	 */
	private Boolean refreshLogin;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public DevType getType() {
		return type;
	}
	public void setType(DevType type) {
		this.type = type;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Boolean isRefreshLogin() {
		return refreshLogin;
	}
	public void setRefreshLogin(Boolean refreshLogin) {
		this.refreshLogin = refreshLogin;
	}
	/**
	 * 
	 * @exception 
	 */
	public AuthTokenDTO() {
		super();
	}
	

	/**
	 * @param userId
	 * @param type
	 * @param appId
	 * @exception 
	 */
	public AuthTokenDTO(String userId, DevType type, String appId) {
		super();
		this.userId = userId;
		this.type = type;
		this.appId = appId;
	}
	/**
	 * @param userId
	 * @param type
	 * @param appId
	 * @param refreshLogin
	 * @exception 
	 */
	public AuthTokenDTO(String userId, DevType type, String appId, Boolean refreshLogin) {
		super();
		this.userId = userId;
		this.type = type;
		this.appId = appId;
		this.refreshLogin = refreshLogin;
	}
	/**
	 * 检查token
	 * @param userId
	 * @param type
	 * @param appId
	 * @param token
	 * @exception 
	 */
	public AuthTokenDTO(String userId, DevType type, String appId, String token) {
		super();
		this.userId = userId;
		this.type = type;
		this.appId = appId;
		this.token = token;
	}
	
	@Override
	public String toString() {
		return "AuthTokenDTO [userId=" + userId + ", type=" + type + ", appId=" + appId + ", token=" + token + "]";
	}
}
