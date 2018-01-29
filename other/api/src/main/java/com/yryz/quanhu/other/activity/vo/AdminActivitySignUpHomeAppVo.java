package com.yryz.quanhu.other.activity.vo;


import com.yryz.quanhu.other.activity.entity.ActivityInfo;

/**
 * 报名类活动首页vo
 * @author dell
 *
 */
public class AdminActivitySignUpHomeAppVo extends ActivityInfo {
	/**
	 *已报名人数 
	 *//*
	private Integer enrolAlready;*/
	/**
	 *报名状态 1可参与 2已报名
	 */
	private Integer enrolStatus;
	/**
	 *报名人数上限
	 */
	private Integer enrolUpper;
	/**
	 *活动状态 1未开始 2进行中 3已结束
	 */
	private Integer activityStatus;
	/*public Integer getEnrolAlready() {
		return enrolAlready;
	}
	public void setEnrolAlready(Integer enrolAlready) {
		this.enrolAlready = enrolAlready;
	}*/
	public Integer getEnrolStatus() {
		return enrolStatus;
	}
	public void setEnrolStatus(Integer enrolStatus) {
		this.enrolStatus = enrolStatus;
	}
	public Integer getEnrolUpper() {
		return enrolUpper;
	}
	public void setEnrolUpper(Integer enrolUpper) {
		this.enrolUpper = enrolUpper;
	}
	public Integer getActivityStatus() {
		return activityStatus;
	}
	public void setActivityStatus(Integer activityStatus) {
		this.activityStatus = activityStatus;
	}
	
	
}
