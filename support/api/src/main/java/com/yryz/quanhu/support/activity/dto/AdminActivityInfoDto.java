package com.yryz.quanhu.support.activity.dto;


import com.yryz.quanhu.support.activity.entity.ActivityInfo;

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
