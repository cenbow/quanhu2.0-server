package com.yryz.quanhu.score.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lsn on 2017/8/28.
 */
public class GrowFlow implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3577121435443411098L;

	private Long id;

    private String custId;

    private String eventCode;
    
    private String eventName;

    private Integer newGrow;

    private Long allGrow;

    private Date createTime;

    private Date updateTime;
    
    public GrowFlow(){
    	
    }
    
    public GrowFlow(String custId , String eventCode , int newGrow){
    	this.custId = custId ;
    	this.eventCode = eventCode;
    	this.newGrow = newGrow;
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

    public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

    public Integer getNewGrow() {
		return newGrow;
	}

	public void setNewGrow(Integer newGrow) {
		this.newGrow = newGrow;
	}

	public Long getAllGrow() {
		return allGrow;
	}

	public void setAllGrow(Long allGrow) {
		this.allGrow = allGrow;
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

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
    
}
