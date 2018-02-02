/**
 * 
 */
package com.yryz.quanhu.dymaic.dto;

import java.io.Serializable;

/**
 * 用户信息搜索
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class UserSearchDTO implements Serializable {
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 圈乎渠道号
	 */
	private String channelCode;
	/**
	 * 注册起始时间
	 */
	private Long startDate;
	/**
	 * 注册结束时间
	 */
	private Long endDate;
	/**
	 * 认证真实名称
	 */
	private String realName;
	/**
	 * 达人审核状态 10:待审核 11:审核通过 12:审核失败 13:取消认证
	 */
	private Integer starAuditStatus;
	/**
	 * 达人认证类型10:个人认证 11:企业/机构认证
	 */
	private Integer starAuthType;
	/**
	 * 达人认证方式 10:用户申请 11:平台设置
	 */
	private Integer starAuthWay;
	/**
	 * 用户等级
	 */
	private String userLevel;
	/**
	 * 达人联系电话
	 */
	private String starContactCall;
	/**
	 * 达人申请起始时间
	 */
	private Long starBeginDate;
	/**
	 * 达人申请结束时间
	 */
	private Long starEndDate;
	/**
	 * 标签id
	 */
	private String tagId;
	
	private Integer currentPage;
	
	private Integer pageSize;
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public Long getStartDate() {
		return startDate;
	}
	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}
	public Long getEndDate() {
		return endDate;
	}
	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Integer getStarAuditStatus() {
		return starAuditStatus;
	}
	public void setStarAuditStatus(Integer starAuditStatus) {
		this.starAuditStatus = starAuditStatus;
	}
	public Integer getStarAuthType() {
		return starAuthType;
	}
	public void setStarAuthType(Integer starAuthType) {
		this.starAuthType = starAuthType;
	}
	public Integer getStarAuthWay() {
		return starAuthWay;
	}
	public void setStarAuthWay(Integer starAuthWay) {
		this.starAuthWay = starAuthWay;
	}
	public String getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}
	public String getStarContactCall() {
		return starContactCall;
	}
	public void setStarContactCall(String starContactCall) {
		this.starContactCall = starContactCall;
	}
	public Long getStarBeginDate() {
		return starBeginDate;
	}
	public void setStarBeginDate(Long starBeginDate) {
		this.starBeginDate = starBeginDate;
	}
	public Long getStarEndDate() {
		return starEndDate;
	}
	public void setStarEndDate(Long starEndDate) {
		this.starEndDate = starEndDate;
	}
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
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
	public UserSearchDTO() {
		super();
	}
	/**
	 * 用户管理
	 * @param nickName
	 * @param phone
	 * @param channelCode
	 * @param startDate
	 * @param endDate
	 */
	public UserSearchDTO(String nickName, String phone, String channelCode, Long startDate, Long endDate) {
		super();
		this.nickName = nickName;
		this.phone = phone;
		this.channelCode = channelCode;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	/**
	 * 违规用户管理
	 * @param nickName
	 * @param phone
	 * @param startDate
	 * @param endDate
	 */
	public UserSearchDTO(String nickName, String phone, Long startDate, Long endDate) {
		super();
		this.nickName = nickName;
		this.phone = phone;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	/**
	 * 达人管理
	 * @param realName
	 * @param starAuditStatus
	 * @param starAuthType
	 * @param starAuthWay
	 * @param userLevel
	 * @param starContactCall
	 * @param starBeginDate
	 * @param starEndDate
	 */
	public UserSearchDTO(String realName, Integer starAuditStatus, Integer starAuthType, Integer starAuthWay,
			String userLevel, String starContactCall, Long starBeginDate, Long starEndDate) {
		super();
		this.realName = realName;
		this.starAuditStatus = starAuditStatus;
		this.starAuthType = starAuthType;
		this.starAuthWay = starAuthWay;
		this.userLevel = userLevel;
		this.starContactCall = starContactCall;
		this.starBeginDate = starBeginDate;
		this.starEndDate = starEndDate;
	}
	@Override
	public String toString() {
		return "UserSearchDTO [nickName=" + nickName + ", phone=" + phone + ", channelCode=" + channelCode
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", realName=" + realName + ", starAuditStatus="
				+ starAuditStatus + ", starAuthType=" + starAuthType + ", starAuthWay=" + starAuthWay + ", userLevel="
				+ userLevel + ", starContactCall=" + starContactCall + ", starBeginDate=" + starBeginDate
				+ ", starEndDate=" + starEndDate + ", tagId=" + tagId + "]";
	}
	
	
}
