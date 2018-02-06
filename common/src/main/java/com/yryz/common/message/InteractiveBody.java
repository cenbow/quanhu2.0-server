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
	 * 互动人
	 */
	private String custId;
	
	/**
	 * 互动人头像
	 */
	private String custImg;
	
	/**
	 * 互动人昵称
	 */
	private String custName;
	
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
	 * 功能id
	 */
	private String moduleEnum;

	public String getModuleEnum() {
		return moduleEnum;
	}

	public void setModuleEnum(String moduleEnum) {
		this.moduleEnum = moduleEnum;
	}

	/**
	 * 
	 * @exception 
	 */
	public InteractiveBody() {
		super();
	}

	/**
	 * @param custId
	 * @param custImg
	 * @param circleId
	 * @param circleName
	 * @param coterieId
	 * @param coterieName
	 * @param bodyImg
	 * @param bodyTitle
	 * @exception 
	 */
	public InteractiveBody(String custId, String custImg, String circleId, String circleName, String coterieId,
			String coterieName, String bodyImg, String bodyTitle) {
		super();
		this.custId = custId;
		this.custImg = custImg;
		this.circleId = circleId;
		this.circleName = circleName;
		this.coterieId = coterieId;
		this.coterieName = coterieName;
		this.bodyImg = bodyImg;
		this.bodyTitle = bodyTitle;
	}

	public InteractiveBody(String custId, String custImg, String custName, String circleId, String circleName, String coterieId, String coterieName, String bodyImg, String bodyTitle, String moduleEnum) {
		this.custId = custId;
		this.custImg = custImg;
		this.custName = custName;
		this.circleId = circleId;
		this.circleName = circleName;
		this.coterieId = coterieId;
		this.coterieName = coterieName;
		this.bodyImg = bodyImg;
		this.bodyTitle = bodyTitle;
		this.moduleEnum = moduleEnum;
	}

	/**
	 * @return the custId
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * @param custId the custId to set
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	/**
	 * @return the custImg
	 */
	public String getCustImg() {
		return custImg;
	}

	/**
	 * @param custImg the custImg to set
	 */
	public void setCustImg(String custImg) {
		this.custImg = custImg;
	}
	
	/**
	 * @return the custName
	 */
	public String getCustName() {
		return custName;
	}

	/**
	 * @param custName the custName to set
	 */
	public void setCustName(String custName) {
		this.custName = custName;
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
