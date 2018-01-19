/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年8月17日
 * Id: ThirdLoginConfigDTO.java, 2017年8月17日 下午1:36:36 Administrator
 */
package com.yryz.quanhu.message.entity.config;

import java.io.Serializable;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年8月17日 下午1:36:36
 * @Description 第三方登录配置
 */
@SuppressWarnings("serial")
public class ThirdLoginConfigDTO implements Serializable{
	/**
	 * 微信appkey
	 */
	private String wxAppKey;
	/**
	 * 微信appSecret
	 */
	private String wxAppSecret;
	/**
	 * 微信登录开关 0:开启 1:关闭
	 */
	private Integer wxStatus;
	/**
	 * 微博appKey
	 */
	private String weiboAppKey;
	/**
	 * 微博appSecret
	 */
	private String weiboAppSecret;
	/**
	 * 微博第三方登录开关 0:开启 1:关闭
	 */
	private Integer weiboStatus;
	
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
	public Integer getWxStatus() {
		return wxStatus;
	}
	public void setWxStatus(Integer wxStatus) {
		this.wxStatus = wxStatus;
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
	public Integer getWeiboStatus() {
		return weiboStatus;
	}
	public void setWeiboStatus(Integer weiboStatus) {
		this.weiboStatus = weiboStatus;
	}
	@Override
	public String toString() {
		return "ThirdLoginConfigDTO [wxAppKey=" + wxAppKey + ", wxAppSecret=" + wxAppSecret + ", wxStatus=" + wxStatus
				+ ", weiboAppKey=" + weiboAppKey + ", weiboAppSecret=" + weiboAppSecret + ", weiboStatus=" + weiboStatus
				+ "]";
	}
	
}
