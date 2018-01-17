/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月5日
 * Id: AuthTokenVO.java, 2018年1月5日 下午3:04:15 Administrator
 */
package com.yryz.quanhu.user.vo;

import java.io.Serializable;

/**
 * web token vo
 * @author danshiyu
 * @version 1.0
 * @date 2018年1月5日 下午3:04:15
 */
@SuppressWarnings("serial")
public class AuthTokenVO implements Serializable {
	/**
	 * 用户账户id
	 */
	private String userId;
	/**
	 * token
	 */
	private String token;
	/**
	 * 过期时间
	 */
	private Long expireAt;
	/**
	 * 用于刷新token的令牌
	 */
	private String refreshToken;
	/**
	 * refreshToken过期时间
	 */
	private Long refreshExpireAt;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getExpireAt() {
		return expireAt;
	}
	public void setExpireAt(Long expireAt) {
		this.expireAt = expireAt;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public Long getRefreshExpireAt() {
		return refreshExpireAt;
	}
	public void setRefreshExpireAt(Long refreshExpireAt) {
		this.refreshExpireAt = refreshExpireAt;
	}
	/**
	 * 
	 * @exception 
	 */
	public AuthTokenVO() {
		super();
	}
	/**
	 * @param userId
	 * @param token
	 * @param expireAt
	 * @exception 
	 */
	public AuthTokenVO(String userId, String token, Long expireAt) {
		super();
		this.userId = userId;
		this.token = token;
		this.expireAt = expireAt;
	}
	
	/**
	 * @param userId
	 * @param token
	 * @param expireAt
	 * @param refreshToken
	 * @param refreshExpireAt
	 * @exception 
	 */
	public AuthTokenVO(String userId, String token, Long expireAt, String refreshToken, Long refreshExpireAt) {
		super();
		this.userId = userId;
		this.token = token;
		this.expireAt = expireAt;
		this.refreshToken = refreshToken;
		this.refreshExpireAt = refreshExpireAt;
	}
	@Override
	public String toString() {
		return "AuthTokenVO [userId=" + userId + ", token=" + token + ", expireAt=" + expireAt + "]";
	}
}
