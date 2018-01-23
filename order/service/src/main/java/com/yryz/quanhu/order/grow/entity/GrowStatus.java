package com.yryz.quanhu.order.grow.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lsn on 2017/8/28.
 */
public class GrowStatus implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6908069292655621423L;

	private Long id;

    private String userId;

    private String circleId;

    private String eventCode;

    private Integer eventCount;

    private Date createTime;

    private Date createDate;
    
    public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	private Date updateTime;
    
    public GrowStatus(){
    	
    }
    
    public GrowStatus(String userId , String eventCode , Integer eventCount){
    	this.userId = userId;
    	this.eventCode = eventCode;
    	this.eventCount = eventCount;
    }
    
    public GrowStatus(String userId , String eventCode , Integer eventCount , String circleId){
    	this.userId = userId;
    	this.eventCode = eventCode;
    	this.eventCount = eventCount;
    	this.circleId = circleId;
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

    public String getCircleId() {
        return circleId;
    }

    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public Integer getEventCount() {
        return eventCount;
    }

    public void setEventCount(Integer eventCount) {
        this.eventCount = eventCount;
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
