/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年6月26日
 * Id: SmsConfig.java, 2017年6月26日 下午3:12:31 Administrator
 */
package com.yryz.quanhu.basic.entity;

import java.io.Serializable;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年6月26日 下午3:12:31
 * @Description 短信配置
 */
@SuppressWarnings("serial")
public class SmsConfigVo implements Serializable{
	/**
	 * app key
	 */
	private String appKey ;
	
	/**
	 * app secret
	 */
	private String appSecret;
	
	/**
	 * 短信签名
	 */
	private String sign;
	
	/**
	 * 渠道
	 */
	private String channel;
	
	/**
	 * 短信模板
	 */
	private String templateCode;
	
	/**
	 * 短信类型
	 */
	private String smsType;
	
	/**
	 * 短信模板内容
	 */
	private String smsParamString;
	
	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	
	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getSmsType() {
		return smsType;
	}

	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}

	

	public String getSmsParamString() {
		return smsParamString;
	}

	public void setSmsParamString(String smsParamString) {
		this.smsParamString = smsParamString;
	}

	@Override
	public String toString() {
		return "SmsConfigVo [appKey=" + appKey + ", appSecret=" + appSecret + ", sign=" + sign + ", channel=" + channel
				+ ", templateCode=" + templateCode + ", smsType=" + smsType + ", smsParamString=" + smsParamString
				+ "]";
	}

	
}
