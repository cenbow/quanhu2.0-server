package com.yryz.quanhu.user.vo;

import java.io.Serializable;

/**
 * 用户简要信息不聚合其他信息
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class UserSimpleNoneOtherVO implements Serializable {
	/**
     * 用户账户id
     */
	
    private Long userId;
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
     * 用户角色 10:普通用户 11:达人
     */
    private Byte userRole;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
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
	public UserSimpleNoneOtherVO() {
		super();
	}
	public UserSimpleNoneOtherVO(Long userId, String userNickName, String userImg, 
			String userDesc, Byte userRole) {
		super();
		this.userId = userId;
		this.userNickName = userNickName;
		this.userImg = userImg;
		this.userDesc = userDesc;
		this.userRole = userRole;
	}
	@Override
	public String toString() {
		return "UserSimpleNoneOtherVO [userId=" + userId + ", userNickName=" + userNickName + ", userImg=" + userImg
				+ ", userSignature=" + userSignature + ", userDesc=" + userDesc + ", userRole=" + userRole + "]";
	}
}
