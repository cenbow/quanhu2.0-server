/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月7日
 * Id: SystemBody.java, 2017年9月7日 上午10:04:57 yehao
 */
package com.yryz.quanhu.message.message;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年9月7日 上午9:54:46
 * @Description 系统消息(图文)||账户消息(图文)
 */
public class SystemBody implements Body {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8502622234976660726L;
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
	 * 相关内容图片
	 */
	private String bodyImg;
	
	/**
	 * 相关内容标题
	 */
	private String bodyTitle;

	/**
	 * 
	 * @exception 
	 */
	public SystemBody() {
		super();
	}

	/**
	 * @param circleId
	 * @param circleName
	 * @param coterieId
	 * @param coterieName
	 * @param bodyImg
	 * @param bodyTitle
	 * @exception 
	 */
	public SystemBody(String circleId, String circleName, String coterieId, String coterieName, String bodyImg,
			String bodyTitle) {
		super();
		this.circleId = circleId;
		this.circleName = circleName;
		this.coterieId = coterieId;
		this.coterieName = coterieName;
		this.bodyImg = bodyImg;
		this.bodyTitle = bodyTitle;
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
