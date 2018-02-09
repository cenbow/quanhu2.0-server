package com.yryz.quanhu.user.vo;

import java.io.Serializable;
/**
 * 临时旧token vo
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class AuthTempTokenVO implements Serializable {
	/**
	 * 临时旧token
	 */
	private String token;
	/**
	 * 临时旧refreshToken
	 */
	private String refreshToken;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public AuthTempTokenVO() {
		super();
	}
	public AuthTempTokenVO(String token, String refreshToken) {
		super();
		this.token = token;
		this.refreshToken = refreshToken;
	}
	@Override
	public String toString() {
		return "AuthTempTokenVO [token=" + token + ", refreshToken=" + refreshToken + "]";
	}
}
