package com.yryz.quanhu.user.dto;

import java.io.Serializable;

/**
 * 达人列表传参
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class StarAuthParamDTO implements Serializable {
	/**
	 * 达人标签(二级分类)
	 */
	private Long categoryId;

	/**
	 * 页数
	 */
	private Integer currentPage;
	/**
	 * 页长度
	 */
	private Integer pageSize;
	/**
	 * 当前用户id
	 */
	private Long userId;
	/**
	 * 用户昵称
	 */
	private String userNname;
	/**
	 * 联系方式
	 */
	private String phone;
	/**
	 * 审核状态 0:待审核 1:审核通过 2:审核失败 3:取消认证
	 */
	private Byte auditStatus;
	/**
	 * 认证类型 0:个人认证 1:企业/机构认证
	 */
	private Byte authType;
	/**
	 * 认证方式 10:用户申请 11:平台设置
	 */
	private Byte authWay;
	/**
	 * 用户等级
	 */
	private Integer userLevel;
	/**
	 * 申请起始时间
	 */
	private String startTime;
	/**
	 * 申请结束时间
	 */
	private String endTime;
	/**
	 * 是否达人认证列表
	 */
	private Boolean starAuthList;
	/**
	 * 是否推荐
	 */
	private Boolean starRecommend;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getUserNname() {
		return userNname;
	}
	public void setUserNname(String userNname) {
		this.userNname = userNname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Byte getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(Byte auditStatus) {
		this.auditStatus = auditStatus;
	}
	public Byte getAuthType() {
		return authType;
	}
	public void setAuthType(Byte authType) {
		this.authType = authType;
	}
	public Byte getAuthWay() {
		return authWay;
	}
	public void setAuthWay(Byte authWay) {
		this.authWay = authWay;
	}
	public Integer getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
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
	public Boolean getStarAuthList() {
		return starAuthList;
	}
	public void setStarAuthList(Boolean starAuthList) {
		this.starAuthList = starAuthList;
	}
	public Boolean getStarRecommend() {
		return starRecommend;
	}
	public void setStarRecommend(Boolean starRecommend) {
		this.starRecommend = starRecommend;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
