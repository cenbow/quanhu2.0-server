package com.yryz.quanhu.order.score.entity;

import java.io.Serializable;
import java.util.Date;

public class ScoreStatusOnce implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2273982045698826751L;

	private Long id;
	
	private String userId;
	
	private String eventCode;
	
	private int scoreFlag;
	
	private Date createTime;
	
	private Date updateTime;
	
	public ScoreStatusOnce(){
		
	}
	
	public ScoreStatusOnce(String userId , String eventCode){
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

	public int getScoreFlag() {
		return scoreFlag;
	}

	public void setScoreFlag(int scoreFlag) {
		this.scoreFlag = scoreFlag;
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
