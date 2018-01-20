package com.yryz.quanhu.coterie.entity;

import java.util.List;

/**
 * 私圈搜索条件
 * @author jk
 *
 */
public class CoterieSearch {
	/**
	 * 圈子状态 ：0待审核，2审批未通过，3上架，4下架
	 */
	private Byte status;
	
	/**
	 * 是否推荐，0否，1是 
	 */
	private Byte recommend;
	
	/**
	 * 圈子名称
	 */
	private String circleName;
	
	/**
	 * 圈子ID
	 */
	private String circleId;
	
	/**
	 * 私圈名称
	 */
	private String coterieName;
	
	/**
	 * 圈主ID
	 */
	private String ownerId;
	
	/**
	 * 申请开时间   起 ,格式yyyy-MM-dd
	 */
	private String startTime;
	
	/**
	 * 申请时间 止,格式yyyy-MM-dd
	 */
	private String endTime;
	
	/**
	 * 特定 私圈里搜索（推荐等）
	 */
	private List<Integer> coterieIdList;
	
	private Integer start;
	private Integer pageSize;
	
	/**
	 * 值：asc,desc  默认desc
	 */
	private String sortValue;

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public String getCoterieName() {
		return coterieName;
	}

	public void setCoterieName(String coterieName) {
		this.coterieName = coterieName;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getCircleId() {
		return circleId;
	}

	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}

	public List<Integer> getCoterieIdList() {
		return coterieIdList;
	}

	public void setCoterieIdList(List<Integer> coterieIdList) {
		this.coterieIdList = coterieIdList;
	}

	public String getSortValue() {
		return sortValue;
	}

	public void setSortValue(String sortValue) {
		this.sortValue = sortValue;
	}

	public Byte getRecommend() {
		return recommend;
	}

	public void setRecommend(Byte recommend) {
		this.recommend = recommend;
	}

	@Override
	public String toString() {
		return "CoterieSearch [status=" + status + ", recommend=" + recommend + ", circleName=" + circleName
				+ ", circleId=" + circleId + ", coterieName=" + coterieName + ", ownerId=" + ownerId + ", startTime="
				+ startTime + ", endTime=" + endTime + ", coterieIdList=" + coterieIdList + ", start=" + start
				+ ", pageSize=" + pageSize + ", sortValue=" + sortValue + "]";
	}
}
