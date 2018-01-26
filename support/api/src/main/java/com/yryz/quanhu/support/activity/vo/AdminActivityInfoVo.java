package com.yryz.quanhu.support.activity.vo;


import com.yryz.quanhu.support.activity.entity.ActivityInfo;

public class AdminActivityInfoVo extends ActivityInfo {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 157889278266769750L;
	/**
	 *活动状态 1未开始 2进行中 3已结束
	 */
	private Integer activityStatus;
	public Integer getActivityStatus() {
		return activityStatus;
	}
	public void setActivityStatus(Integer activityStatus) {
		this.activityStatus = activityStatus;
	}
}
