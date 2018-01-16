package com.yryz.quanhu.user.vo;

import java.io.Serializable;

/**
 * 微信token
 * 
 * @author danshiyu
 * 
 */
@SuppressWarnings("serial")
public class WxToken implements Serializable {
	/**
	 * 错误码 0:成功 其他失败
	 */
	private int errcode = 0;
	/**
	 * 错误描述
	 */
	private String errmsg;
	/**
	 * token
	 */
	private String access_token;
	/**
	 * 过期时间 秒
	 */
	private int expires_in;
	/**
	 * 刷新token 可用于获取acess_token
	 */
	private String refresh_token;
	/**
	 * 用户的标识，对当前公众号唯一
	 */
	private String openid;
	/**
	 * 应用授权作用域
	 */
	private String scope;
	/**
	 * 多个公众号之间用户帐号互通UnionID机制
	 */
	private String unionid;

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	@Override
	public String toString() {
		return "WxToken [errcode=" + errcode + ", errmsg=" + errmsg + ", access_token=" + access_token + ", expires_in="
				+ expires_in + ", refresh_token=" + refresh_token + ", openid=" + openid + ", scope=" + scope
				+ ", unionid=" + unionid + "]";
	}

}
