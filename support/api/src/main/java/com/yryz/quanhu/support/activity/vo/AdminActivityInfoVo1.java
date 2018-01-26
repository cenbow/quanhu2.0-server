package com.yryz.quanhu.support.activity.vo;


import com.yryz.quanhu.support.activity.entity.ActivityInfo;

import java.util.Date;



public class AdminActivityInfoVo1 extends ActivityInfo {
	
	private Integer joinFlag;
	
	private Integer joinCount;
	
	private Date currentDate;
	
	/**
	 *活动状态 1未开始 2进行中 3已结束
	 */
	private Integer activityStatus;

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
	
}
