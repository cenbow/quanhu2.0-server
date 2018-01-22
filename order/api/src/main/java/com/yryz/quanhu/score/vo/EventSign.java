package com.yryz.quanhu.score.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 签到状态记录实体类
 * @author lsn
 *
 */
public class EventSign implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5784041224148862394L;

	private Long id;
	
	private String custId;
	
	private String eventCode;
	
	private int signCount;
	
	private Date lastSignTime;
	
	private Date createTime;
	
	private Date updateTime;
	
	private boolean signFlag;
	
	public EventSign(){
		
	}
	
	public EventSign(String custId , String eventCode){
		this.custId = custId;
		this.eventCode = eventCode;
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

	public int getSignCount() {
		return signCount;
	}

	public void setSignCount(int signCount) {
		this.signCount = signCount;
	}

	public Date getLastSignTime() {
		return lastSignTime;
	}

	public void setLastSignTime(Date lastSignTime) {
		this.lastSignTime = lastSignTime;
	}

	public boolean isSignFlag() {
		return signFlag;
	}

	public void setSignFlag(boolean signFlag) {
		this.signFlag = signFlag;
	}
}
