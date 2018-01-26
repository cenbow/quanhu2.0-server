package com.yryz.quanhu.support.activity.vo;


import com.yryz.quanhu.support.activity.entity.ActivityInfo;

import java.util.Date;



public class AdminActivityVoteVo extends ActivityInfo {
	
	private Integer joinFlag;
	
	private Integer joinCount;
	
	private Date currentDate;
	
	private Integer voteTotalCount;
	
	/**
	 *活动状态 1未开始 2进行中 3已结束
	 */
	private Integer activityStatus;
	
	
	private Long shareCount; 
	
	private Long voteCount; 
	

	public Long getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(Long voteCount) {
		this.voteCount = voteCount;
	}

	public Long getShareCount() {
		return shareCount;
	}

	public void setShareCount(Long shareCount) {
		this.shareCount = shareCount;
	}


	public Integer getJoinFlag() {
		return joinFlag;
	}

	public void setJoinFlag(Integer joinFlag) {
		this.joinFlag = joinFlag;
	}

	public Integer getJoinCount() {
		return joinCount;
	}

	public void setJoinCount(Integer joinCount) {
		this.joinCount = joinCount;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public Integer getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(Integer activityStatus) {
		this.activityStatus = activityStatus;
	}

	public Integer getVoteTotalCount() {
		return voteTotalCount;
	}

	public void setVoteTotalCount(Integer voteTotalCount) {
		this.voteTotalCount = voteTotalCount;
	}
	
}
