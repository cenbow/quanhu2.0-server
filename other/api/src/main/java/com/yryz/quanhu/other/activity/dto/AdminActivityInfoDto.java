package com.yryz.quanhu.other.activity.dto;


import com.yryz.quanhu.other.activity.entity.ActivityInfo;

import java.util.Date;

public class AdminActivityInfoDto extends ActivityInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8237707946922781107L;
	/**
	 *活动状态 1未开始 2进行中 3已结束
	 */
	private Byte activityStatus;
	
	/*默认分页参数*/
	private Integer pageNo=1;
	
	/*默认分页参数*/
	private Integer pageSize=10;
	
	/*排序方式,默认按排序值*/
	private	String	orderType="sort";

	/**
	 *开始时间  开始
	 */
	private Date beginTimeStart;
	/**
	 *开始时间  结束
	 */
		private Date beginTimeEnd;

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

	public Byte getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(Byte activityStatus) {
		this.activityStatus = activityStatus;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	
}
