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
     * 用户推送设备号id
     */
    private String userDeviceId;
    /**
     * 城市代码
     */
    private String cityCode;
    /**
     * 用户角色 0:普通用户 1:实名用户
     */
    private int userRole;
    /**
     * 删除标识 0:有效  1:删除
     */
    private int delFlag;
    
    public String getCustId() {
        return userId;
    }
    public void setCustId(String userId) {
        this.userId = userId;
    }
    public String getCustNickName() {
        return userNickName;
    }
    public void setCustNickName(String userNickName) {
        this.userNickName = userNickName;
    }
    public String getCustImg() {
        return userImg;
    }
    public void setCustImg(String userImg) {
        this.userImg = userImg;
    }
    public String getCustSignature() {
        return userSignature;
    }
    public void setCustSignature(String userSignature) {
        this.userSignature = userSignature;
    }
    public int getCustAge() {
        return userAge;
    }
    public void setCustAge(int userAge) {
        this.userAge = userAge;
    }
    public String getCustDesc() {
        return userDesc;
    }
    public void setCustDesc(String userDesc) {
        this.userDesc = userDesc;
    }
    public String getCustQr() {
        return userQr;
    }
    public void setCustQr(String userQr) {
        this.userQr = userQr;
    }
    public int getCustGenders() {
        return userGenders;
    }
    public void setCustGenders(int userGenders) {
        this.userGenders = userGenders;
    }
    public String getCustLocation() {
        return userLocation;
    }
    public void setCustLocation(String userLocation) {
        this.userLocation = userLocation;
    }
    public String getCustDeviceId() {
        return userDeviceId;
    }
    public void setCustDeviceId(String userDeviceId) {
        this.userDeviceId = userDeviceId;
    }
    public String getCityCode() {
        return cityCode;
    }
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    public int getCustRole() {
        return userRole;
    }
    public void setCustRole(int userRole) {
        this.userRole = userRole;
    }
    public int getDelFlag() {
        return delFlag;
    }
    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }
	public UpdateBaseInfoDTO(String userId, String userNickName, String userImg, String userSignature, int userAge,
			String userDesc, String userQr, int userGenders, String userLocation, String userDeviceId, String cityCode,
			int userRole) {
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
		this.userDeviceId = userDeviceId;
		this.cityCode = cityCode;
		this.userRole = userRole;
	}

}