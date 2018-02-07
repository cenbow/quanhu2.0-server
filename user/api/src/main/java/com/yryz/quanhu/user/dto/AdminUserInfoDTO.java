/**
 * 
 */
package com.yryz.quanhu.user.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author danshiyu
 * 管理后台查询用户dto
 */
@SuppressWarnings("serial")
public class AdminUserInfoDTO implements Serializable{

	private Integer pageNo = 1;

	private Integer pageSize = 10;

	private String keyword;


	/**
	 * 注册:10 充值:11 提现: 12
	 */
	private String dataSource;

	//用户信息
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 是否马甲 不为空即搜索马甲
	 */
	private String isVest;
	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 达人联系方式
	 */
	private String contactCall;
	/**
	 * 注册起始时间
	 */
	private String startDate;
	/**
	 * 注册结束时间
	 */
	private String endDate;
	/**
	 * 应用id 必填
	 */
	private String appId;
	/**
	 * 用户状态 0-正常 1-违规 2-禁言 3-冻结 4-注销
	 */
	private Integer userStatus;
	/**
	 * 用户角色 10-普通用户 11-达人 
	 */
	private Integer userRole;
	/**
	 * 标签集合
	 */
	private Set<Long> tagIds;

	//达人信息
	/**
	 * 认证类型 10:个人认证 11:企业/机构认证
	 */
	private Byte authType;

	/**
	 * 认证方式 10:用户申请 11:平台设置
	 */
	private Byte authWay;

	/**
	 * 审核状态 10:待审核 11:审核通过 12:审核失败 13:取消认证
	 */
	private Byte auditStatus;

	/**
	 * 是否被推荐 10:否 11:是
	 */
	private Byte recommendStatus;

	//注册信息
	/**
	 * 渠道号
	 */
	private String activityChannelCode;

	//积分信息
	/**
	 * 成长级别
	 */
	private String growLevel;

	/**
	 * 申请认证开始时间
	 */
	private String applyAuthBeginDate;

	/**
	 * 申请认证结束时间
	 */
	private String applyAuthEndDate;
	
	/**
	 * 是否需要收入 
	 */
	private Boolean needIntegral = false;

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getContactCall() {
		return contactCall;
	}

	public void setContactCall(String contactCall) {
		this.contactCall = contactCall;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public Byte getRecommendStatus() {
		return recommendStatus;
	}

	public void setRecommendStatus(Byte recommendStatus) {
		this.recommendStatus = recommendStatus;
	}

	public String getApplyAuthBeginDate() {
		return applyAuthBeginDate;
	}

	public void setApplyAuthBeginDate(String applyAuthBeginDate) {
		this.applyAuthBeginDate = applyAuthBeginDate;
	}

	public String getApplyAuthEndDate() {
		return applyAuthEndDate;
	}

	public void setApplyAuthEndDate(String applyAuthEndDate) {
		this.applyAuthEndDate = applyAuthEndDate;
	}

	public Set<Long> getTagIds() {
		return tagIds;
	}

	public void setTagIds(Set<Long> tagIds) {
		this.tagIds = tagIds;
	}

	public String getGrowLevel() {
		return growLevel;
	}

	public void setGrowLevel(String growLevel) {
		this.growLevel = growLevel;
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

	public Byte getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Byte auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getActivityChannelCode() {
		return activityChannelCode;
	}

	public void setActivityChannelCode(String activityChannelCode) {
		this.activityChannelCode = activityChannelCode;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getIsVest() {
		return isVest;
	}
	public void setIsVest(String isVest) {
		this.isVest = isVest;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public Integer getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}
	public Integer getUserRole() {
		return userRole;
	}
	public void setUserRole(Integer userRole) {
		this.userRole = userRole;
	}

	public Boolean getNeedIntegral() {
		return needIntegral;
	}

	public void setNeedIntegral(Boolean needIntegral) {
		this.needIntegral = needIntegral;
	}

	public AdminUserInfoDTO() {
		super();
	}


	public AdminUserInfoDTO(String nickName, String isVest, String phone, String startDate, String endDate,
							String appId) {
		super();
		this.nickName = nickName;
		this.isVest = isVest;
		this.phone = phone;
		this.startDate = startDate;
		this.endDate = endDate;
		this.appId = appId;
	}
	public AdminUserInfoDTO(String nickName, String isVest, String phone, String startDate, String endDate,
			List<Long> userIds) {
		super();
		this.nickName = nickName;
		this.isVest = isVest;
		this.phone = phone;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	
	@Override
	public String toString() {
		return "AdminUserInfoDTO [nickName=" + nickName + ", phone=" + phone + ", startDate=" + startDate + ", endDate="
				+ endDate + "]";
	}
}
