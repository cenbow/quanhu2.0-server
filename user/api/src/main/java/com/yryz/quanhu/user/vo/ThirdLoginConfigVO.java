/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月15日
 * Id: ThirdLoginConfigVO.java, 2018年1月15日 下午1:39:21 Administrator
 */
package com.yryz.quanhu.user.vo;

import java.io.Serializable;

/**
 * 第三方登录配置
 * @author danshiyu
 * @version 1.0
 * @date 2018年1月15日 下午1:39:21
 */
@SuppressWarnings("serial")
public class ThirdLoginConfigVO implements Serializable {
	/**
	 * 微信appkey
	 */
	private String wxAppKey;
	/**
	 * 微信appSecret
	 */
	private String wxAppSecret;
	/**
	 * 微博appKey
	 */
	private String weiboAppKey;
	/**
	 * 微博appSecret
	 */
	private String weiboAppSecret;
	/**
	 * 回调地址
	 */
	private String notifyUrl;
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
}
