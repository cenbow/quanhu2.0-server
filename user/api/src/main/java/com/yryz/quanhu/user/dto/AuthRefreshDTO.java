/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月5日
 * Id: AuthRefreshDTO.java, 2018年1月5日 下午3:19:41 Administrator
 */
package com.yryz.quanhu.user.dto;

/**
 * auth2.0 app token dto
 * @author danshiyu
 * @version 1.0
 * @date 2018年1月5日 下午3:19:41
 */
@SuppressWarnings("serial")
public class AuthRefreshDTO extends AuthTokenDTO {
	/**
	 * 长期token
	 */
	private String refreshToken;
	/**
	 * 刷新短期token
	 */
	private boolean refreshTokenFlag;
	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}


	public boolean isRefreshTokenFlag() {
		return refreshTokenFlag;
	}

	public void setRefreshTokenFlag(boolean refreshTokenFlag) {
		this.refreshTokenFlag = refreshTokenFlag;
	}

	/**
	 * 
	 * @exception 
	 */
	public AuthRefreshDTO() {
		super();
	}

	/**
	 * @param refreshToken
	 * @exception 
	 */
	public AuthRefreshDTO(String refreshToken,boolean refreshTokenFlag) {
		super();
		this.refreshToken = refreshToken;
		this.refreshTokenFlag = refreshTokenFlag;
	}

	@Override
	public String toString() {
		return "AuthRefreshDTO [refreshToken=" + refreshToken + ", getUserId()=" + getUserId() + ", getType()="
				+ getType() + ", getAppId()=" + getAppId() + ", getToken()=" + getToken() + "]";
	}
}
