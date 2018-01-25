package com.yryz.quanhu.score.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 平台总体事件类型信息维护实体
 * @author syc
 *
 */
public class SysEventInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3903710047790469356L;
	
	private Long id;
	
	private String eventCode;
	
	private String eventType;
	
	private String eventName;
	
	private Date createTime;
	
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
