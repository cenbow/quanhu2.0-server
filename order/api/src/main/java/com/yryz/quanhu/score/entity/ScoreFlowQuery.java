package com.yryz.quanhu.score.entity;

import java.io.Serializable;
/**
 * 
 * @author syc  
 * @version 1.0
 * @date 2018年1月22日 上午9:59:37
 * @Description TODO (这里用一句话描述这个方法的作用)
 */
public class ScoreFlowQuery implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3438993563315960305L;
	
    /** 用户id */
	private String userId;
	
    /** 事件ID */
	private String eventCode;
	
    /** 开始时间 */
	private String startTime;
	
    /** 结束时间*/
	private String endTime;
	
    /** 事件类型 */
	private int flowType;
	
    /** 开始 */
	private int start;
	
    /** 条数 */
	private int limit;
	
	/** 手机号 */
	private String userPhone;
	
    /** 事件名称 */
	private String eventName;

	/**页码*/
    private Integer currentPage = 1;
    /**每页大小*/
    private Integer pageSize = 10;
	
	
	
    /** 积分增减标志（0-增加，1-减少）*/
	private Integer consumeFlag = 0;


	public Integer getConsumeFlag() {
		return consumeFlag;
	}

	public void setConsumeFlag(Integer consumeFlag) {
		this.consumeFlag = consumeFlag;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
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

	public int getFlowType() {
		return flowType;
	}

	public void setFlowType(int flowType) {
		this.flowType = flowType;
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
