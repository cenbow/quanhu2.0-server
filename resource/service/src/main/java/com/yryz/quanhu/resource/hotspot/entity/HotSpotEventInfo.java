/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月25日
 * Id: HotSpotEventInfo.java, 2018年1月25日 下午1:47:03 yehao
 */
package com.yryz.quanhu.resource.hotspot.entity;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月25日 下午1:47:03
 * @Description 热度计算的事件值
 */
public class HotSpotEventInfo {
	
	/**
	 * 事件ID
	 */
	private String eventId;
	
	/**
     * 事件用户ID
     */
    private Long userId;

    /**
     * 私圈ID
     */
    private String coterieId;

    /**
     * 资源ID
     */
    private Long resourceId;

    /**
     * 资源作者ID
     */
    private Long ownerId;

    /**
     * 事件编码
     */
    private String eventCode;

    /**
     * 事件计数
     */
    private Integer eventNum;

    /**
     * 创建时间
     */
    private String createDate;

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
	 * @return the resourceId
	 */
	public Long getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * @return the ownerId
	 */
	public Long getOwnerId() {
		return ownerId;
	}

	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
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
	public Integer getEventNum() {
		return eventNum;
	}

	/**
	 * @param eventNum the eventNum to set
	 */
	public void setEventNum(Integer eventNum) {
		this.eventNum = eventNum;
	}

	/**
	 * @return the createDate
	 */
	public String getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
    
}
