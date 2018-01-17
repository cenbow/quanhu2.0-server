/*
 * UserBaseinfo.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2018-01-12 Created
 */
package com.yryz.quanhu.user.entity;

import java.util.Date;

import com.yryz.common.entity.GenericEntity;

/**
 * 用户基础信息表
 * 
 * @author xxx
 * @version 1.0 2018-01-12
 * @since 1.0
 */
@SuppressWarnings("serial")
public class UserBaseInfo extends GenericEntity{

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 应用id
     */
    private String appId;

    /**
     * 昵称
     */
    private String userNickName;

    /**
     * 头像
     */
    private String userImg;

    /**
     * 用户签名
     */
    private String userSignature;

    /**
     * 用户手机号码
     */
    private String userPhone;

    /**
     * 年龄
     */
    private Byte userAge;

    /**
     * 用户二维码地址
     */
    private String userQr;

    /**
     * 用户性别 0-女 1-男
     */
    private Byte userGenders;

    /**
     * 出生年月日
     */
    private String userBirthday;

    /**
     * 用户城市位置(湖北武汉)
     */
    private String userLocation;

    /**
     * 用户推送设备号id
     */
    private String userDeviceId;

    /**
     * 用户状态 10-正常 11-冻结 12-黑名单
     */
    private Byte userStatus;

    /**
     * 国家
     */
    private String userCountry;

    /**
     * 省份
     */
    private String userProvince;

    /**
     * 城市
     */
    private String userCity;

    /**
     * 城市代码
     */
    private String cityCode;

    /**
     * 用户角色 10:普通用户 11:实名用户
     */
    private Byte custRole;

    /**
     * 是否马甲 10:否 11:是
     */
    private Byte custVest;

    /**
     * 认证状态 10-未认证 11-已认证
     */
    private Byte authStatus;

    /**
     * 最终热度值
     */
    private Integer lastHeat;

    /**
     * 删除标识 10:有效  11:删除
     */
    private Byte delFlag;

    /**
     * 冻结最大时间点
     */
    private Date banPostTime;

    /**
     * 用户简介
     */
    private String userDesc;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName == null ? null : userNickName.trim();
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg == null ? null : userImg.trim();
    }

    public String getUserSignature() {
        return userSignature;
    }

    public void setUserSignature(String userSignature) {
        this.userSignature = userSignature == null ? null : userSignature.trim();
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public Byte getUserAge() {
        return userAge;
    }

    public void setUserAge(Byte userAge) {
        this.userAge = userAge;
    }

    public String getUserQr() {
        return userQr;
    }

    public void setUserQr(String userQr) {
        this.userQr = userQr == null ? null : userQr.trim();
    }

    public Byte getUserGenders() {
        return userGenders;
    }

    public void setUserGenders(Byte userGenders) {
        this.userGenders = userGenders;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday == null ? null : userBirthday.trim();
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation == null ? null : userLocation.trim();
    }

    public String getUserDeviceId() {
        return userDeviceId;
    }

    public void setUserDeviceId(String userDeviceId) {
        this.userDeviceId = userDeviceId == null ? null : userDeviceId.trim();
    }

    public Byte getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Byte userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry == null ? null : userCountry.trim();
    }

    public String getUserProvince() {
        return userProvince;
    }

    public void setUserProvince(String userProvince) {
        this.userProvince = userProvince == null ? null : userProvince.trim();
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity == null ? null : userCity.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public Byte getCustRole() {
        return custRole;
    }

    public void setCustRole(Byte custRole) {
        this.custRole = custRole;
    }

    public Byte getCustVest() {
        return custVest;
    }

    public void setCustVest(Byte custVest) {
        this.custVest = custVest;
    }

    public Byte getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(Byte authStatus) {
        this.authStatus = authStatus;
    }

    public Integer getLastHeat() {
        return lastHeat;
    }

    public void setLastHeat(Integer lastHeat) {
        this.lastHeat = lastHeat;
    }

    public Byte getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

    public Date getBanPostTime() {
        return banPostTime;
    }

    public void setBanPostTime(Date banPostTime) {
        this.banPostTime = banPostTime;
    }


    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc == null ? null : userDesc.trim();
    }

	public UserBaseInfo() {
		super();
	}
	
	/**
	 * 注册初始化用户信息
	 * @param userId
	 * @param appId
	 * @param userPhone
	 * @param userLocation
	 * @param userDeviceId
	 * @param cityCode
	 */
	public UserBaseInfo(Long userId, String appId, String userPhone, String userLocation, String userDeviceId,
			String cityCode) {
		super();
		this.userId = userId;
		this.appId = appId;
		this.userPhone = userPhone;
		this.userLocation = userLocation;
		this.userDeviceId = userDeviceId;
		this.cityCode = cityCode;
	}

	/**
	 * 昵称头像更新
	 * @param userId
	 * @param userNickName
	 * @param userImg
	 */
	public UserBaseInfo(Long userId, String userNickName, String userImg) {
		super();
		this.userId = userId;
		this.userNickName = userNickName;
		this.userImg = userImg;
	}

	/**
	 * 头像更新
	 * @param userId
	 * @param userImg
	 */
	public UserBaseInfo(Long userId, String userImg) {
		super();
		this.userId = userId;
		this.userImg = userImg;
	}
	
	/**
	 * 手机号或者设备id更新
	 * @param userId
	 * @param userPhone
	 * @param userDeviceId
	 * @param delFlag
	 */
	public UserBaseInfo(Long userId, String userPhone, String userDeviceId, Byte delFlag) {
		super();
		this.userId = userId;
		this.userPhone = userPhone;
		this.userDeviceId = userDeviceId;
		this.delFlag = delFlag;
	}
	
	/**
	 * 用户状态、角色、认证、热度更新
	 * @param userId
	 * @param userStatus
	 * @param custRole
	 * @param custVest
	 * @param authStatus
	 * @param lastHeat
	 * @param banPostTime
	 */
	public UserBaseInfo(Long userId, Byte userStatus, Byte custRole, Byte custVest, Byte authStatus, Integer lastHeat,
			Date banPostTime) {
		super();
		this.userId = userId;
		this.userStatus = userStatus;
		this.custRole = custRole;
		this.custVest = custVest;
		this.authStatus = authStatus;
		this.lastHeat = lastHeat;
		this.banPostTime = banPostTime;
	}
	
	/**
	 * 用户基本信息更新
	 * @param userId
	 * @param userNickName
	 * @param userImg
	 * @param userSignature
	 * @param userAge
	 * @param userGenders
	 * @param userBirthday
	 * @param userLocation
	 * @param userCountry
	 * @param userProvince
	 * @param userCity
	 * @param cityCode
	 * @param userDesc
	 */
	public UserBaseInfo(Long userId, String userNickName, String userImg, String userSignature, Byte userAge,
			Byte userGenders, String userBirthday, String userLocation, String userCountry, String userProvince,
			String userCity, String cityCode, String userDesc) {
		super();
		this.userId = userId;
		this.userNickName = userNickName;
		this.userImg = userImg;
		this.userSignature = userSignature;
		this.userAge = userAge;
		this.userGenders = userGenders;
		this.userBirthday = userBirthday;
		this.userLocation = userLocation;
		this.userCountry = userCountry;
		this.userProvince = userProvince;
		this.userCity = userCity;
		this.cityCode = cityCode;
		this.userDesc = userDesc;
	}
}