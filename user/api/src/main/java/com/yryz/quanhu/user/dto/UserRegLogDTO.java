/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年12月20日
 * Id: userRegLogDTO.java, 2017年12月20日 下午3:09:41 Administrator
 */
package com.yryz.quanhu.user.dto;

import java.io.Serializable;

/**
 * 用户注册日志
 * @author danshiyu
 * @version 1.0
 * @date 2017年12月20日 下午3:09:41
 */
@SuppressWarnings("serial")
public class UserRegLogDTO implements Serializable {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 应用渠道码(客户端渠道包)
     */
    private String appChannel;

    /**
     * 应用版本号
     */
    private String appVersion;

    /**
     * 注册方式 PHONE、WEIXIN、SINA
     */
    private String regType;

    /**
     * APP、WEB、WAP
     */
    private String devType;

    /**
     * 设备名称
     */
    private String devName;

    /**
     * 应用id
     */
    private String appId;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 地址
     */
    private String location;

    /**
     * 活动渠道码
     */
    private String activityChannelCode;

    /**
     * 注册渠道码（app_channel reg_type dev_type activity_channel_code组合字段）
     */
    private String channelCode;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAppChannel() {
		return appChannel;
	}

	public void setAppChannel(String appChannel) {
		this.appChannel = appChannel;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getRegType() {
		return regType;
	}

	public void setRegType(String regType) {
		this.regType = regType;
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

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getActivityChannelCode() {
		return activityChannelCode;
	}

	public void setActivityChannelCode(String activityChannelCode) {
		this.activityChannelCode = activityChannelCode;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	/**
	 * 
	 * @exception 
	 */
	public UserRegLogDTO() {
		super();
	}

	/**
	 * @param userId
	 * @param appChannel
	 * @param appVersion
	 * @param regType
	 * @param devType
	 * @param devName
	 * @param appId
	 * @param ip
	 * @param location
	 * @param activityChannelCode
	 * @param channelCode
	 * @exception 
	 */
	public UserRegLogDTO(Long userId, String appChannel, String appVersion, String regType, String devType,
			String devName, String appId, String ip, String location, String activityChannelCode, String channelCode) {
		super();
		this.userId = userId;
		this.appChannel = appChannel;
		this.appVersion = appVersion;
		this.regType = regType;
		this.devType = devType;
		this.devName = devName;
		this.appId = appId;
		this.ip = ip;
		this.location = location;
		this.activityChannelCode = activityChannelCode;
		this.channelCode = channelCode;
	}

	@Override
	public String toString() {
		return "userRegLogDTO [userId=" + userId + ", appChannel=" + appChannel + ", appVersion=" + appVersion
				+ ", regType=" + regType + ", devType=" + devType + ", devName=" + devName + ", appId=" + appId
				+ ", ip=" + ip + ", location=" + location + ", activityChannelCode=" + activityChannelCode
				+ ", channelCode=" + channelCode + "]";
	}
    
    
}
