package com.yryz.quanhu.user.vo;

import java.io.Serializable;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/2/26 16:45
 * @Author: pn
 */
public class UserRegInfoVO implements Serializable {

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
     * 用户手机号码
     */
    private String userPhone;

    /**
     * 用户性别 10-女 11-男
     */
    private Byte userGenders;

    /**
     * 认证状态 10-未认证 11-已认证
     */
    private Byte authStatus;

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
     * 用户签名
     */
    private String userSignature;

    public String getUserSignature() {
        return userSignature;
    }

    public void setUserSignature(String userSignature) {
        this.userSignature = userSignature;
    }

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
        this.appId = appId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Byte getUserGenders() {
        return userGenders;
    }

    public void setUserGenders(Byte userGenders) {
        this.userGenders = userGenders;
    }

    public Byte getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(Byte authStatus) {
        this.authStatus = authStatus;
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
}
