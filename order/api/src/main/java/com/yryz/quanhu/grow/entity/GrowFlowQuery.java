package com.yryz.quanhu.grow.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @author lsn
 * @version 1.0
 */
public class GrowFlowQuery implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -227291341770675652L;
	
	private String userId;
	
	private String eventCode;
	
	private Date startTime;
	
	private Date endTime;
	
	private int start;
	
	private int limit;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	
}
