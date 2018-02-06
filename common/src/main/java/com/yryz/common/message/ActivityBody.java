/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月7日
 * Id: ActivityBody.java, 2017年9月7日 上午10:05:34 yehao
 */
package com.yryz.common.message;


/**
 * @author yehao
 * @version 1.0
 * @date 2017年9月7日 上午10:01:01
 * @Description 推荐与活动的扩展展示1
 */
public class ActivityBody implements Body {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7378469458462122189L;
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
	 * @param circleId
	 * @param circleName
	 * @param coterieId
	 * @param coterieName
	 * @exception 
	 */
	public ActivityBody(String circleId, String circleName, String coterieId, String coterieName) {
		super();
		this.circleId = circleId;
		this.circleName = circleName;
		this.coterieId = coterieId;
		this.coterieName = coterieName;
	}

	public ActivityBody(String circleId, String circleName, String coterieId, String coterieName, String moduleEnum) {
		this.circleId = circleId;
		this.circleName = circleName;
		this.coterieId = coterieId;
		this.coterieName = coterieName;
		this.moduleEnum = moduleEnum;
	}

	/**
	 * 
	 * @exception 
	 */
	public ActivityBody() {
		super();
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

}
