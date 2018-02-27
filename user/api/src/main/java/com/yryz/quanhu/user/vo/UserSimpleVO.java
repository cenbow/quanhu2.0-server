/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月10日
 * Id: userSimpleVO.java, 2017年11月10日 上午11:44:18 Administrator
 */
package com.yryz.quanhu.user.vo;

import java.io.Serializable;



import com.yryz.quanhu.user.contants.UserRelationConstant.STATUS;

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
    
    /**
     * 好友备注名
     */
    private String nameNotes = "";
    /**
     * 关系状态 0-陌生人 1-关注 2-粉丝 3-相互关注 4-拉黑 5-被拉黑 6-相互拉黑 7-自己
     */
    private Integer relationStatus = STATUS.OWNER.getCode();
    /**
     * 推荐语
     */
    private String recommendDesc = "";
	/**
	 * 行业以及领域
	 */
    private String tradeField = "";
    public String getNameNotes() {
		return nameNotes == null ? "" : nameNotes.trim();
	}
	public void setNameNotes(String nameNotes) {
		this.nameNotes = nameNotes == null ? "" : nameNotes.trim();
	}
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
	public Integer getRelationStatus() {
		return relationStatus;
	}
	public void setRelationStatus(Integer relationStatus) {
		this.relationStatus = relationStatus == null ? 0 : relationStatus;
	}
	public String getRecommendDesc() {
		return recommendDesc;
	}
	public void setRecommendDesc(String recommendDesc) {
		this.recommendDesc = recommendDesc;
	}
	public String getTradeField() {
		return tradeField;
	}
	public void setTradeField(String tradeField) {
		this.tradeField = tradeField;
	}
	public UserSimpleVO() {
		super();
	}
	public UserSimpleVO(Long userId, String userNickName, String userImg, String userDesc, Byte userRole) {
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
