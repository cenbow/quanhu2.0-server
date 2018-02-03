/*
 * UserLoginLog.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2018-01-12 Created
 */
package com.yryz.quanhu.user.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.yryz.common.entity.GenericEntity;

/**
 * 用户登录日志表
 * 
 * @author xxx
 * @version 1.0 2018-01-12
 * @since 1.0
 */
@SuppressWarnings("serial")
public class UserLoginLog extends GenericEntity{

	@JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 在线设备类型
     */
    private String deviceType;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 坐标经度
     */
    private Long loginX;

    /**
     * 坐标纬度
     */
    private Long loginY;

    /**
     * 应用id
     */
    private String appId;
    
    private String ip;
    
    private String token;
    
    private String refreshToken;
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public Long getLoginX() {
        return loginX;
    }

    public void setLoginX(Long loginX) {
        this.loginX = loginX;
    }

    public Long getLoginY() {
        return loginY;
    }

    public void setLoginY(Long loginY) {
        this.loginY = loginY;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

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

	public UserLoginLog() {
		super();
	}

	public UserLoginLog(Long userId, String deviceType, String deviceName, String deviceId, String appId, String ip,
			String token, String refreshToken) {
		super();
		this.userId = userId;
		this.deviceType = deviceType;
		this.deviceName = deviceName;
		this.deviceId = deviceId;
		this.appId = appId;
		this.ip = ip;
		this.token = token;
		this.refreshToken = refreshToken;
	}

	public UserLoginLog(Long userId, String deviceType, String deviceName, String deviceId, String appId) {
		super();
		this.userId = userId;
		this.deviceType = deviceType;
		this.deviceName = deviceName;
		this.deviceId = deviceId;
		this.appId = appId;
	}
}