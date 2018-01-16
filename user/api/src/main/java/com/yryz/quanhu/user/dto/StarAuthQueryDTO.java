package com.yryz.quanhu.user.dto;

import java.io.Serializable;
/**
 * 达人认证查询参数
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class StarAuthQueryDTO implements Serializable {
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
	 * 认证方式 0:用户申请 1:平台设置
	 */
	private Byte authWay;
	/**
	 * 用户等级
	 */
	private Integer userLevel;
	/**
	 * 申请起始时间 yyyy-MM-dd
	 */
	private String startTime;
	/**
	 * 申请结束时间 yyyy-MM-dd
	 */
	private String endTime;
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
}
