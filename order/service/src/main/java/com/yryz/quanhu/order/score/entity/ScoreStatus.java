package com.yryz.quanhu.order.score.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lsn on 2017/8/28.
 */
public class ScoreStatus implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6908069292655621423L;

	private Long id;

    private String custId;

    private String circleId;

    private String eventCode;

    private Integer eventCount;

    private Date createTime;

    private Date updateTime;
    
    public ScoreStatus(){
    	
    }
    
    public ScoreStatus(String custId , String eventCode , Integer eventCount){
    	this.custId = custId;
    	this.eventCode = eventCode;
    	this.eventCount = eventCount;
    }
    
    public ScoreStatus(String custId , String eventCode , Integer eventCount , String circleId){
    	this.custId = custId;
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

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
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
