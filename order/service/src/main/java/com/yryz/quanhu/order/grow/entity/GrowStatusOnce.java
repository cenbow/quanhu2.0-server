package com.yryz.quanhu.order.grow.entity;

import java.io.Serializable;
import java.util.Date;

public class GrowStatusOnce implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2273982045698826751L;

	private Long id;
	
	private String userId;
	
	private String eventCode;
	
	private String resourceId;
	
	private int growFlag;
	
	private Date createTime;
	
	private Date updateTime;
	
	public GrowStatusOnce(){
		
	}
	
	public GrowStatusOnce(String userId , String eventCode){
		this.userId = userId;
		this.eventCode = eventCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public int getGrowFlag() {
		return growFlag;
	}

	public void setGrowFlag(int growFlag) {
		this.growFlag = growFlag;
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

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
}
