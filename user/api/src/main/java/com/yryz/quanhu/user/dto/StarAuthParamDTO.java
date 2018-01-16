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
	 * 起始位置
	 */
	private int  start;
	/**
	 * 用户昵称
	 */
	private String custNname;
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
	 * 认证方式 0:用户申请 1:平台设置
	 */
	private Byte authWay;
	/**
	 * 用户等级
	 */
	private Integer custLevel;
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


	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public String getCustNname() {
		return custNname;
	}
	public void setCustNname(String custNname) {
		this.custNname = custNname;
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
	public Integer getCustLevel() {
		return custLevel;
	}
	public void setCustLevel(Integer custLevel) {
		this.custLevel = custLevel;
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
}
