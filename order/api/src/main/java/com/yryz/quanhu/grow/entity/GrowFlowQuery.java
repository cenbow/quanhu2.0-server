package com.yryz.quanhu.grow.entity;

import java.io.Serializable;
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
	
	/**页码*/
    private Integer currentPage = 1;


	/**每页大小*/
    private Integer pageSize = 10;
    
	/**用户ID*/
	private String userId;
	
	/**积分增减标志（0-增加，1-减少）*/
	private String  consumeFlag;

	/**事件ID*/
	private String eventCode;
	
	/**开始时间*/
	private String startTime;
	
	/**结束时间*/
	private String endTime;
	
	/**开始*/
	private int start;
	
	/**条数*/
	private int limit;

	/** 手机号 */
	private String userPhone;
	
    /** 事件名称 */
	private String eventName;
	
	
	
	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

    public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public String getConsumeFlag() {
		return consumeFlag;
	}

	public void setConsumeFlag(String consumeFlag) {
		this.consumeFlag = consumeFlag;
	}

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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
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
