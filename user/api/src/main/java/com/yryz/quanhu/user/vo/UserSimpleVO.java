/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月10日
 * Id: userSimpleVO.java, 2017年11月10日 上午11:44:18 Administrator
 */
package com.yryz.quanhu.user.vo;

import java.io.Serializable;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月10日 上午11:44:18
 * @Description 用户简要信息
 */
@SuppressWarnings("serial")
public class UserSimpleVO implements Serializable {
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
     * 用户简介
     */
    private String userDesc;
    /**
     * 用户二维码地址
     */
    private String userQr;
    /**
     * 用户城市位置(湖北武汉)
     */
    private String userLocation;
    /**
     * 城市代码
     */
    private String cityCode;
    /**
     * 用户角色 0:普通用户 1:实名用户
     */
    private Byte userRole;
    
    /**
     * 好友备注名
     */
    private String nameNotes;
    /**
     * 关系类型 0-陌生人 1-关注 2-粉丝 3-好友 4-黑名单
     */
    private Integer relationStatus;
	public String getNameNotes() {
		return nameNotes;
	}
	public void setNameNotes(String nameNotes) {
		this.nameNotes = nameNotes;
	}
	public Integer getRelationStatus() {
		return relationStatus;
	}
	public void setRelationStatus(Integer relationStatus) {
		this.relationStatus = relationStatus;
	}
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
	public Byte getUserRole() {
		return userRole;
	}
	public void setUserRole(Byte userRole) {
		this.userRole = userRole;
	}
	@Override
	public String toString() {
		return "UserSimpleVO [userId=" + userId + ", userNickName=" + userNickName + ", userImg=" + userImg
				+ ", userSignature=" + userSignature + ", userDesc=" + userDesc + ", userQr=" + userQr
				+ ", userLocation=" + userLocation + ", cityCode=" + cityCode + ", userRole=" + userRole + "]";
	}
}
