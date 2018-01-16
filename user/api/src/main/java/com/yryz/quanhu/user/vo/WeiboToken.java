package com.yryz.quanhu.user.vo;

import java.io.Serializable;

/**
 * 微博token
 * 
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class WeiboToken implements Serializable {

	/**
	 * 错误码 0:成功 其他失败
	 */
	private int error_code = 0;
	/**
	 * 错误描述
	 */
	private String error_description;
	/**
	 * 授权用户的uid
	 */
	private String uid;
	/**
	 * 用户授权的唯一票据，
	 */
	private String access_token;
	/**
	 * access_token的剩余时间，单位是秒数
	 */
	private String expire_in;

	public int getError_code() {
		return error_code;
	}

	public void setError_code(int error_code) {
		this.error_code = error_code;
	}

	public String getError_description() {
		return error_description;
	}

	public void setError_description(String error_description) {
		this.error_description = error_description;
	}

	public String getUid() {
		return uid;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getExpire_in() {
		return expire_in;
	}

	public void setExpire_in(String expire_in) {
		this.expire_in = expire_in;
	}

	@Override
	public String toString() {
		return "WeiboToken [error_code=" + error_code + ", error_description=" + error_description + ", uid=" + uid
				+ ", access_token=" + access_token + ", expire_in=" + expire_in + "]";
	}

}
