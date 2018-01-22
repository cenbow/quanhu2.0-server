/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月8日
 * Id: RequestHeader.java, 2018年1月8日 上午9:36:54 Administrator
 */
package com.yryz.common.entity;

import java.io.Serializable;

/**
 * http request header信息对象
 * @author danshiyu
 * @version 1.0
 * @date 2018年1月8日 上午9:36:54
 */
@SuppressWarnings("serial")
public class RequestHeader implements Serializable{
	/**
	 * 应用id
	 */
	private String appId;
	/**
	 * 签名
	 */
	private String sign; // 签名
	/**
	 * 短期令牌
	 */
	private String token; // 令牌
	/**
	 * app版本号
	 */
	private String appVersion;
	/**
	 * 设备类型   1IOS, 2Android, 3WEB
	 */
	private String devType;
	/**
	 * 设备名称
	 */
	private String devName;
	/**
	 * 设备id
	 */
	private String devId;
	/**
	 * app下载来源码
	 */
	private String ditchCode;
	/**
	 * User-Agent
	 */
	private String userAgent;
	/**
	 * 网络类型
	 */
	private String net;
	
	private String xForwardedFor;
	
	private String proxyClientIP;
	
	private String xLProxyClientIP;
	
	private String HTT_PCLIENT_IP;
	
	private String HTTP_X_FORWARDED_FOR;
	
	private String XRequestedWith;
	
	private String userId;

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getDevType() {
		return devType;
	}

	public void setDevType(String devType) {
		this.devType = devType;
	}

	public String getDevName() {
		return devName;
	}

	public void setDevName(String devName) {
		this.devName = devName;
	}

	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}

	public String getDitchCode() {
		return ditchCode;
	}

	public void setDitchCode(String ditchCode) {
		this.ditchCode = ditchCode;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getNet() {
		return net;
	}

	public void setNet(String net) {
		this.net = net;
	}

	public String getxForwardedFor() {
		return xForwardedFor;
	}

	public void setxForwardedFor(String xForwardedFor) {
		this.xForwardedFor = xForwardedFor;
	}

	public String getProxyClientIP() {
		return proxyClientIP;
	}

	public void setProxyClientIP(String proxyClientIP) {
		this.proxyClientIP = proxyClientIP;
	}

	public String getxLProxyClientIP() {
		return xLProxyClientIP;
	}

	public void setxLProxyClientIP(String xLProxyClientIP) {
		this.xLProxyClientIP = xLProxyClientIP;
	}

	public String getHTT_PCLIENT_IP() {
		return HTT_PCLIENT_IP;
	}

	public void setHTT_PCLIENT_IP(String hTT_PCLIENT_IP) {
		HTT_PCLIENT_IP = hTT_PCLIENT_IP;
	}

	public String getHTTP_X_FORWARDED_FOR() {
		return HTTP_X_FORWARDED_FOR;
	}

	public void setHTTP_X_FORWARDED_FOR(String hTTP_X_FORWARDED_FOR) {
		HTTP_X_FORWARDED_FOR = hTTP_X_FORWARDED_FOR;
	}

	public String getXRequestedWith() {
		return XRequestedWith;
	}

	public void setXRequestedWith(String xRequestedWith) {
		XRequestedWith = xRequestedWith;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Override
	public String toString() {
		return "RequestHeader [sign=" + sign + ", token=" + token + ", appVersion="
				+ appVersion + ", devType=" + devType + ", devName=" + devName + ", devId=" + devId + ", ditchCode="
				+ ditchCode + ", userAgent=" + userAgent + ", net=" + net + ", xForwardedFor=" + xForwardedFor
				+ ", proxyClientIP=" + proxyClientIP + ", xLProxyClientIP=" + xLProxyClientIP + ", HTT_PCLIENT_IP="
				+ HTT_PCLIENT_IP + ", HTTP_X_FORWARDED_FOR=" + HTTP_X_FORWARDED_FOR + ", XRequestedWith="
				+ XRequestedWith + ", userId=" + userId + "]";
	}
}
