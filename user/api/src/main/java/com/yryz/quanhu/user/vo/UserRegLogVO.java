package com.yryz.quanhu.user.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@SuppressWarnings("serial")
public class UserRegLogVO implements Serializable {
	/**
     * 用户id
     */
	@JsonSerialize(using = ToStringSerializer.class)
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
    /**
     * 创建时间
     */
    private Date createDate;
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
        this.appChannel = appChannel == null ? null : appChannel.trim();
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion == null ? null : appVersion.trim();
    }

    public String getRegType() {
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType == null ? null : regType.trim();
    }

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType == null ? null : devType.trim();
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName == null ? null : devName.trim();
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
        this.ip = ip == null ? null : ip.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getActivityChannelCode() {
        return activityChannelCode;
    }

    public void setActivityChannelCode(String activityChannelCode) {
        this.activityChannelCode = activityChannelCode == null ? null : activityChannelCode.trim();
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode == null ? null : channelCode.trim();
    }

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
