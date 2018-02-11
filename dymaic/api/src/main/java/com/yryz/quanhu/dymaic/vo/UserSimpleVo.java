package com.yryz.quanhu.dymaic.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class UserSimpleVo implements Serializable{
	private static final long serialVersionUID = 8315586242856230666L;
	@ApiModelProperty("用户账户id")
	private Long userId;
	
	@ApiModelProperty("昵称")
	private String userNickName;
	
	@ApiModelProperty("头像")
	private String userImg;
	
	@ApiModelProperty("用户签名")
	private String userSignature;
	
	@ApiModelProperty("用户简介")
	private String userDesc;
	
	@ApiModelProperty("是否达人 : 10:普通用户 11:达人")
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

	@Override
	public String toString() {
		return "UserSimpleVO [userId=" + userId + ", userNickName=" + userNickName + ", userImg=" + userImg
				+ ", userSignature=" + userSignature + ", userDesc=" + userDesc + ", userRole=" + userRole + "]";
	}
	
}
