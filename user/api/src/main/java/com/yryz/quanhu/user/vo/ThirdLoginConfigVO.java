/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月15日
 * Id: ThirdLoginConfigVO.java, 2018年1月15日 下午1:39:21 Administrator
 */
package com.yryz.quanhu.user.vo;

import java.io.Serializable;

import com.yryz.common.utils.JsonUtils;

/**
 * web第三方登录配置<br/>
 * 目前存在三种第三方登录方式，微信、QQ、微博，其中微信、微博存在app和web方式，微信拥有客户端内部认证方式<br/>
 * 微信、微博的appKey和secret各个端的配置都不同，由于获取第三方用户信息需要的appKey不分端校验，所有后台只配置了微信、微博web端应用的第三方配置
 * @author danshiyu
 * @version 1.0
 * @date 2018年1月15日 下午1:39:21
 */
@SuppressWarnings("serial")
public class ThirdLoginConfigVO implements Serializable {
	/**
	 * qq app_id
	 */
	private String qqAppKey = "101419427";
	/**
	 * qq app_secret
	 */
	private String qqAppSecret = "17fe3acefa3aeaae815d10f0315e9093";
	/**
	 * 微信appkey
	 */
	private String wxAppKey = "wxe739b71ab7671656";
	/**
	 * 微信appSecret
	 */
	private String wxAppSecret = "3954320f9e50961a70d1584eee43451e";
	/**
	 * 微博appKey
	 */
	private String weiboAppKey = "3886564843";
	/**
	 * 微博appSecret
	 */
	private String weiboAppSecret = "50db720e51cbaff793b6e569de4e9cfc";
	/**
	 * 回调地址
	 */
	private String notifyUrl = "/openapi/services/app/v2/user/thirdLoginNotify";
	
	/**
	 * 微信授权 app key
	 */
	private String wxOauthAppKey = "wx8b0aed844739ffbe";
	/**
	 * 微信授权app secret
	 */
	private String wxOauthAppSecret = "b80e24c23c211808fa4b03aad628dc06";
	/**
	 * 微信授权回调地址
	 */
	private String wxOauthNotifyUrl ="/openapi/services/app/v2/user/wxOauthLoginNotify";
	public String getWxOauthAppKey() {
		return wxOauthAppKey;
	}
	public void setWxOauthAppKey(String wxOauthAppKey) {
		this.wxOauthAppKey = wxOauthAppKey;
	}
	public String getWxOauthAppSecret() {
		return wxOauthAppSecret;
	}
	public void setWxOauthAppSecret(String wxOauthAppSecret) {
		this.wxOauthAppSecret = wxOauthAppSecret;
	}
	public String getWxOauthNotifyUrl() {
		return wxOauthNotifyUrl;
	}
	public void setWxOauthNotifyUrl(String wxOauthNotifyUrl) {
		this.wxOauthNotifyUrl = wxOauthNotifyUrl;
	}
	/**
	 * 
	 * @exception 
	 */
	public ThirdLoginConfigVO() {
		super();
	}
	/**
	 * @param wxAppKey
	 * @param wxAppSecret
	 * @param weiboAppKey
	 * @param weiboAppSecret
	 * @exception 
	 */
	public ThirdLoginConfigVO(String wxAppKey, String wxAppSecret, String weiboAppKey, String weiboAppSecret) {
		super();
		this.wxAppKey = wxAppKey;
		this.wxAppSecret = wxAppSecret;
		this.weiboAppKey = weiboAppKey;
		this.weiboAppSecret = weiboAppSecret;
	}
	public String getWxAppKey() {
		return wxAppKey;
	}
	public void setWxAppKey(String wxAppKey) {
		this.wxAppKey = wxAppKey;
	}
	public String getWxAppSecret() {
		return wxAppSecret;
	}
	public void setWxAppSecret(String wxAppSecret) {
		this.wxAppSecret = wxAppSecret;
	}
	public String getWeiboAppKey() {
		return weiboAppKey;
	}
	public void setWeiboAppKey(String weiboAppKey) {
		this.weiboAppKey = weiboAppKey;
	}
	public String getWeiboAppSecret() {
		return weiboAppSecret;
	}
	public void setWeiboAppSecret(String weiboAppSecret) {
		this.weiboAppSecret = weiboAppSecret;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	
	public String getQqAppKey() {
		return qqAppKey;
	}
	public void setQqAppKey(String qqAppKey) {
		this.qqAppKey = qqAppKey;
	}
	public String getQqAppSecret() {
		return qqAppSecret;
	}
	public void setQqAppSecret(String qqAppSecret) {
		this.qqAppSecret = qqAppSecret;
	}
	public static void main(String[] args){
		System.out.println(JsonUtils.toFastJson(new ThirdLoginConfigVO()));
	}
}
