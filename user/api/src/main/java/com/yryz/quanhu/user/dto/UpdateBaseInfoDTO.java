package com.yryz.quanhu.user.dto;

import java.io.Serializable;
/**
 * 用户基础信息表
 * @author suyongcheng
 * @date 2017年11月09日
 */
@SuppressWarnings("serial")
public class UpdateBaseInfoDTO implements Serializable{

    /**
     * 用户账户id
     */
    private String userId;
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
     * 年龄
     */
    private int userAge;
    /**
     * 用户简介
     */
    private String userDesc;
    /**
     * 用户二维码地址
     */
    private String userQr;
    /**
     * 用户性别 0-女 1-男
     */
    private int userGenders;
    /**
     * 用户城市位置(湖北武汉)
     */
    private String userLocation;
    /**
     * 城市代码
     */
    private String cityCode;
    
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserNickName() {
        return userNickName;
    }
    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }
    public String getUserImg() {
        return userImg;
    }
    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }
    public String getUserSignature() {
        return userSignature;
    }
    public void setUserSignature(String userSignature) {
        this.userSignature = userSignature;
    }
    public int getUserAge() {
        return userAge;
    }
    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }
    public String getUserDesc() {
        return userDesc;
    }
    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }
    public String getUserQr() {
        return userQr;
    }
    public void setUserQr(String userQr) {
        this.userQr = userQr;
    }
    public int getUserGenders() {
        return userGenders;
    }
    public void setUserGenders(int userGenders) {
        this.userGenders = userGenders;
    }
    public String getUserLocation() {
        return userLocation;
    }
    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }
    public String getCityCode() {
        return cityCode;
    }
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
	public UpdateBaseInfoDTO() {
		super();
	}
	public UpdateBaseInfoDTO(String userId, String userNickName, String userImg, String userSignature, int userAge,
			String userDesc, String userQr, int userGenders, String userLocation, String cityCode) {
		super();
		this.userId = userId;
		this.userNickName = userNickName;
		this.userImg = userImg;
		this.userSignature = userSignature;
		this.userAge = userAge;
		this.userDesc = userDesc;
		this.userQr = userQr;
		this.userGenders = userGenders;
		this.userLocation = userLocation;
		this.cityCode = cityCode;
	}

}