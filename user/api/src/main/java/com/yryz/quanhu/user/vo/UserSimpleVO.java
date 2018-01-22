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
     * 用户等级（这里的等级要从积分中心获取，不能以此为准）
     */
    private String userLevel;
    /**
     * 用户角色 0:普通用户 1:实名用户
     */
    private Byte userRole;
    
    /**
     * 好友备注名
     */
    private String nameNotes;
	public String getNameNotes() {
		return nameNotes == null ? "" : nameNotes.trim();
	}
	public void setNameNotes(String nameNotes) {
		this.nameNotes = nameNotes == null ? "" : nameNotes.trim();
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
	public Byte getUserRole() {
		return userRole;
	}
	public void setUserRole(Byte userRole) {
		this.userRole = userRole;
	}
	public String getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel == null ? "1" : userLevel;
	}
	public UserSimpleVO() {
		super();
	}
	public UserSimpleVO(String userId, String userNickName, String userImg, String userDesc, Byte userRole) {
		super();
		this.userId = userId;
		this.userNickName = userNickName;
		this.userImg = userImg;
		this.userDesc = userDesc;
		this.userRole = userRole;
	}
	@Override
	public String toString() {
		return "UserSimpleVO [userId=" + userId + ", userNickName=" + userNickName + ", userImg=" + userImg
				+ ", userSignature=" + userSignature + ", userDesc=" + userDesc 
				+ ", userRole=" + userRole + "]";
	}
}
