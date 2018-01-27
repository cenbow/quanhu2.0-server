package com.yryz.quanhu.support.activity.dto;



import com.yryz.quanhu.support.activity.entity.ActivityInfo;

import java.util.Date;


public class AdminActivityInfoVoteDto extends ActivityInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = -38543985505185071L;
	/**
	 *活动状态 1未开始 2进行中 3已结束
	 */
	private Integer activityStatus;
	/**
	 *上线时间  开始
	 */
	private Date onlineTimeStart;
	/**
	 *上线时间  结束
	 */
	private Date onlineTimeEnd;
	/**
	 *开始时间  开始
	 */
	private Date beginTimeStart;
	/**
	 *开始时间  结束
	 */
	private Date beginTimeEnd;
	/**
	 *结束时间  开始
	 */
	private Date endTimeStart;
	
	
	private Integer pageNo = 1;
				
		     
	private Integer pageSize = 10;

	private Integer userFlag;

	private Integer prizesFlag;

	public Integer getUserFlag() {
		return userFlag;
	}

	public void setUserFlag(Integer userFlag) {
		this.userFlag = userFlag;
	}

	public Integer getPrizesFlag() {
		return prizesFlag;
	}

	public void setPrizesFlag(Integer prizesFlag) {
		this.prizesFlag = prizesFlag;
	}

	/**
	 * @return the pageNo
	 */
	public Integer getPageNo() {
		return pageNo;
	}
	/**
	 * @param pageNo the pageNo to set
	 */
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getActivityStatus() {
		return activityStatus;
	}
	public void setActivityStatus(Integer activityStatus) {
		this.activityStatus = activityStatus;
	}
	public Date getOnlineTimeStart() {
		return onlineTimeStart;
	}
	public void setOnlineTimeStart(Date onlineTimeStart) {
		this.onlineTimeStart = onlineTimeStart;
	}
	public Date getOnlineTimeEnd() {
		return onlineTimeEnd;
	}
	public void setOnlineTimeEnd(Date onlineTimeEnd) {
		this.onlineTimeEnd = onlineTimeEnd;
	}
	public Date getBeginTimeStart() {
		return beginTimeStart;
	}
	public void setBeginTimeStart(Date beginTimeStart) {
		this.beginTimeStart = beginTimeStart;
	}
	public Date getBeginTimeEnd() {
		return beginTimeEnd;
	}
	public void setBeginTimeEnd(Date beginTimeEnd) {
		this.beginTimeEnd = beginTimeEnd;
	}
	public Date getEndTimeStart() {
		return endTimeStart;
	}
	public void setEndTimeStart(Date endTimeStart) {
		this.endTimeStart = endTimeStart;
	}
	public Date getEndTimeEnd() {
		return endTimeEnd;
	}
	public void setEndTimeEnd(Date endTimeEnd) {
		this.endTimeEnd = endTimeEnd;
	}
	/**
	 *结束时间  结束
	 */
	private Date endTimeEnd;
}
