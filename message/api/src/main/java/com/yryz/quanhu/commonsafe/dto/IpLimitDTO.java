package com.yryz.quanhu.commonsafe.dto;

import java.io.Serializable;
/**
 * ip限制入参
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class IpLimitDTO implements Serializable {
	/**
	 * 业务类型接入方自定义，例如phone_login(手机号登录)、email_forget_password(邮箱找回密码)等
	 */
	private String serviceType;
	/**
	 * ip地址
	 */
	private String ip;
	/**
	 * 应用id
	 */
	private String appId;
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public IpLimitDTO() {
		super();
	}
	public IpLimitDTO(String serviceType, String ip, String appId) {
		super();
		this.serviceType = serviceType;
		this.ip = ip;
		this.appId = appId;
	}
	@Override
	public String toString() {
		return "IpLimitDTO [serviceType=" + serviceType + ", ip=" + ip + ", appId=" + appId + "]";
	}
	
}
