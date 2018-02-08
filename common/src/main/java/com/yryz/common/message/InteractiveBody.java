/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月7日
 * Id: InteractiveBody.java, 2017年9月7日 上午10:04:05 yehao
 */
package com.yryz.common.message;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年9月7日 上午9:54:33
 * @Description 互动消息
 */
public class InteractiveBody implements Body {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7842463828127800992L;

	/**
	 * 互动人id
	 */
	private String userId;
	
	/**
	 * 互动人头像
	 */
	private String userImg;
	
	/**
	 * 互动人昵称
	 */
	private String userNickName;
	
	/**
	 * 圈子ID
	 */
	private String circleId;

	/**
	 * 圈子名称
	 */
	private String circleName;
	
	/**
	 * 私圈ID
	 */
	private String coterieId;
	
	/**
	 * 私圈名称
	 */
	private String coterieName;
	
	/**
	 * 提示框图片
	 */
	private String bodyImg;
	
	/**
	 * 提示框内容
	 */
	private String bodyTitle;

	/**
	 * 
	 * @exception 
	 */
	public InteractiveBody() {
		super();
	}

	public InteractiveBody(String userId, String userImg, String userNickName, String circleId, String circleName, String coterieId, String coterieName, String bodyImg, String bodyTitle) {
		this.userId = userId;
		this.userImg = userImg;
		this.userNickName = userNickName;
		this.circleId = circleId;
		this.circleName = circleName;
		this.coterieId = coterieId;
		this.coterieName = coterieName;
		this.bodyImg = bodyImg;
		this.bodyTitle = bodyTitle;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	/**
	 * @return the circleId
	 */
	public String getCircleId() {
		return circleId;
	}

	/**
	 * @param circleId the circleId to set
	 */
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}

	/**
	 * @return the circleName
	 */
	public String getCircleName() {
		return circleName;
	}

	/**
	 * @param circleName the circleName to set
	 */
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	/**
	 * @return the coterieId
	 */
	public String getCoterieId() {
		return coterieId;
	}

	/**
	 * @param coterieId the coterieId to set
	 */
	public void setCoterieId(String coterieId) {
		this.coterieId = coterieId;
	}

	/**
	 * @return the coterieName
	 */
	public String getCoterieName() {
		return coterieName;
	}

	/**
	 * @param coterieName the coterieName to set
	 */
	public void setCoterieName(String coterieName) {
		this.coterieName = coterieName;
	}

	/**
	 * @return the bodyImg
	 */
	public String getBodyImg() {
		return bodyImg;
	}

	/**
	 * @param bodyImg the bodyImg to set
	 */
	public void setBodyImg(String bodyImg) {
		this.bodyImg = bodyImg;
	}

	/**
	 * @return the bodyTitle
	 */
	public String getBodyTitle() {
		return bodyTitle;
	}

	/**
	 * @param bodyTitle the bodyTitle to set
	 */
	public void setBodyTitle(String bodyTitle) {
		this.bodyTitle = bodyTitle;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
