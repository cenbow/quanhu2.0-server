/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月5日
 * Id: AuthConfigVO.java, 2018年1月5日 下午4:00:11 Administrator
 */
package com.yryz.quanhu.auth.vo;

import java.io.Serializable;

/**
 * 认证配置
 * @author danshiyu
 * @version 1.0
 * @date 2018年1月5日 下午4:00:11
 */
@SuppressWarnings("serial")
public class AuthConfig implements Serializable{
	/**
	 * web端token过期时间/小时
	 */
	private Integer webTokenExpire;
	/**
	 * app端短期token过期时间/小时
	 */
	private Integer tokenExpire;
	/**
	 * app端长期token过期时间/天
	 */
	private Integer refreshExpire;
	public Integer getWebTokenExpire() {
		return webTokenExpire;
	}
	public void setWebTokenExpire(Integer webTokenExpire) {
		this.webTokenExpire = webTokenExpire;
	}
	public Integer getTokenExpire() {
		return tokenExpire;
	}
	public void setTokenExpire(Integer tokenExpire) {
		this.tokenExpire = tokenExpire;
	}
	public Integer getRefreshExpire() {
		return refreshExpire;
	}
	public void setRefreshExpire(Integer refreshExpire) {
		this.refreshExpire = refreshExpire;
	}
	/**
	 * 
	 * @exception 
	 */
	public AuthConfig() {
		super();
	}
	/**
	 * @param webTokenExpire
	 * @param tokenExpire
	 * @param refreshExpire
	 * @exception 
	 */
	public AuthConfig(Integer webTokenExpire, Integer tokenExpire, Integer refreshExpire) {
		super();
		this.webTokenExpire = webTokenExpire;
		this.tokenExpire = tokenExpire;
		this.refreshExpire = refreshExpire;
	}
	@Override
	public String toString() {
		return "AuthConfig [webTokenExpire=" + webTokenExpire + ", tokenExpire=" + tokenExpire + ", refreshExpire="
				+ refreshExpire + "]";
	}
}
