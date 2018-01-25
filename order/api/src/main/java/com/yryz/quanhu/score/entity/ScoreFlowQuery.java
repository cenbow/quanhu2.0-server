package com.yryz.quanhu.score.entity;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
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
	private Date startTime;
	
    /** 结束时间*/
	private Date endTime;
	
    /** 事件类型 */
	private int flowType;
	
    /** 开始 */
	private int start;
	
    /** 条数 */
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
