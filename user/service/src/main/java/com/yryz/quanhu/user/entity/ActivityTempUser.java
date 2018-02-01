/*
 * ActivityTempUser.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2018-01-30 Created
 */
package com.yryz.quanhu.user.entity;

import com.yryz.common.entity.GenericEntity;

/**
 * 活动临时用户表
 * 
 * @author danshiyu
 * @version 1.0 2018-01-30
 * @since 1.0
 */
@SuppressWarnings("serial")
public class ActivityTempUser extends GenericEntity{

    /**
     * 第三方唯一id
     */
    private String thirdId;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private String headImg;

    /**
     * 登录类型10-weixin  11-xinna
     */
    private Byte thirdType;

    /**
     * 活动编码
     */
    private String activivtyChannelCode;

    /**
     * 应用id
     */
    private String appId;

    /**
     * ip
     */
    private String ip;

    /**
     * 删除标识 10-否 11-是
     */
    private Byte delFlag;

    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId == null ? null : thirdId.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg == null ? null : headImg.trim();
    }

    public Byte getThirdType() {
        return thirdType;
    }

    public void setThirdType(Byte thirdType) {
        this.thirdType = thirdType;
    }

    public String getActivivtyChannelCode() {
        return activivtyChannelCode;
    }

    public void setActivivtyChannelCode(String activivtyChannelCode) {
        this.activivtyChannelCode = activivtyChannelCode == null ? null : activivtyChannelCode.trim();
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


    public Byte getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

	public ActivityTempUser() {
		super();
	}

	public ActivityTempUser(String thirdId, String nickName, String headImg, Byte thirdType,
			String activivtyChannelCode, String appId, String ip) {
		super();
		this.thirdId = thirdId;
		this.nickName = nickName;
		this.headImg = headImg;
		this.thirdType = thirdType;
		this.activivtyChannelCode = activivtyChannelCode;
		this.appId = appId;
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "ActivityTempUser [thirdId=" + thirdId + ", nickName=" + nickName + ", headImg=" + headImg
				+ ", thirdType=" + thirdType + ", activivtyChannelCode=" + activivtyChannelCode + ", appId=" + appId
				+ ", ip=" + ip + ", delFlag=" + delFlag + "]";
	}
}