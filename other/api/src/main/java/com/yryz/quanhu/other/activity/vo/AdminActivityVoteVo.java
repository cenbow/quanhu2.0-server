package com.yryz.quanhu.other.activity.vo;



import com.yryz.quanhu.other.activity.entity.ActivityInfo;

import java.util.Date;


public class AdminActivityVoteVo extends ActivityInfo {

	private Date currentDate;
	
	private Integer voteTotalCount;
	
	/**
	 *活动状态 11未开始 12进行中 13已结束
	 */
	private Integer activityStatus;
	
	
	private Long shareCount; 
	
	private Integer userNum;

	private Long amount;

	private Integer prizesFlag;

	private Integer userFlag;

	public Integer getUserFlag() {
		return userFlag;
	}

	public void setUserFlag(Integer userFlag) {
		this.userFlag = userFlag;
	}

	public Integer getUserNum() {
		return userNum;
	}

	public void setUserNum(Integer userNum) {
		this.userNum = userNum;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Integer getPrizesFlag() {
		return prizesFlag;
	}

	public void setPrizesFlag(Integer prizesFlag) {
		this.prizesFlag = prizesFlag;
	}

	public Long getShareCount() {
		return shareCount;
	}

	public void setShareCount(Long shareCount) {
		this.shareCount = shareCount;
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
