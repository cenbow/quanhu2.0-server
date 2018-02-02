package com.yryz.quanhu.dymaic.canal.entity;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class UserRegLog implements Serializable {
	/**
     * 用户id
     */
    @Field(type= FieldType.Long)
    private Long userId;

    /**
     * 应用渠道码(客户端渠道包)
     */
    @Field(type= FieldType.text)
    private String appChannel;

    /**
     * 应用版本号
     */
    @Field(type= FieldType.text)
    private String appVersion;

    /**
     * 注册方式 PHONE、WEIXIN、SINA
     */
    @Field(type= FieldType.text)
    private String regType;

    /**
     * APP、WEB、WAP
     */
    @Field(type= FieldType.text)
    private String devType;

    /**
     * 设备名称
     */
    @Field(type= FieldType.text)
    private String devName;

    /**
     * 应用id
     */
    @Field(type= FieldType.text)
    private String appId;

    /**
     * ip地址
     */
    @Field(type= FieldType.text)
    private String ip;

    /**
     * 地址
     */
    @Field(type= FieldType.text)
    private String location;

    /**
     * 活动渠道码
     */
    @Field(type= FieldType.text)
    private String activityChannelCode;

    /**
     * 注册渠道码（app_channel reg_type dev_type activity_channel_code组合字段）
     */
    @Field(type= FieldType.text)
    private String channelCode;
    /**
     * 创建时间
     */
    @Field(type= FieldType.Date)
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
