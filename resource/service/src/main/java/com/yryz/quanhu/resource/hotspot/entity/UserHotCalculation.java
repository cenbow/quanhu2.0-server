/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月25日
 * Id: UserHotCalculation.java, 2018年1月25日 下午1:41:01 yehao
 */
package com.yryz.quanhu.resource.hotspot.entity;

import java.io.Serializable;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月25日 下午1:41:01
 * @Description 用户热度计算中间值
 */
public class UserHotCalculation implements Serializable {
	
	/**
	 * 事件ID
	 */
	private String eventId;
	
	/**
	 * 用户ID
	 */
	private Long userId;
	
	/**
	 * 事件编码
	 */
	private String eventCode;
	
	/**
	 * 事件数
	 */
	private Long eventNum;

	/**
	 * 热度值
	 */
	private Long heat;
	
	/**
	 * 创建时间
	 */
	private Long createTime;

	/**
	 * 
	 * @exception 
	 */
	public UserHotCalculation() {
		super();
	}

	/**
	 * @return the eventId
	 */
	public String getEventId() {
		return eventId;
	}

	/**
	 * @param eventId the eventId to set
	 */
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the eventCode
	 */
	public String getEventCode() {
		return eventCode;
	}

	/**
	 * @param eventCode the eventCode to set
	 */
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	/**
	 * @return the eventNum
	 */
	public Long getEventNum() {
		return eventNum;
	}

	/**
	 * @param eventNum the eventNum to set
	 */
	public void setEventNum(Long eventNum) {
		this.eventNum = eventNum;
	}

	/**
	 * @return the heat
	 */
	public Long getHeat() {
		return heat;
	}

	/**
	 * @param heat the heat to set
	 */
	public void setHeat(Long heat) {
		this.heat = heat;
	}

	/**
	 * @return the createTime
	 */
	public Long getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	
}
