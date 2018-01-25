package com.yryz.quanhu.user.vo;

import java.io.Serializable;
/**
 * 达人认证简化信息
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class UserStarSimpleVo implements Serializable {
	/**
	 * 行业以及领域
	 */
	private String tradeField;


	/**
	 * 认证类型 0:个人认证 1:企业/机构认证
	 */
	private Integer authType;
	
	/**
	 * 	认证方式 0:用户申请 1:平台设置
	 */
	private Integer authWay;
	/**
	 * 用户id
	 */
	private Long userId;
	
	/**
	 * 	认证状态10:审核中 11:成功 12:失败 13:后台取消认证 14:未填写过认证资料
	 */
	private Byte auditStatus;
	
	/**
     * 机构名称
     */
    private String organizationName;

    /**
     * 真实姓名或者运营者姓名
     */
    private String realName;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 所在地区
     */
    private String location;

    /**
     * 自媒体账户
     */
    private String ownerAppId;

    /**
     * 可提供的资源或者拥有的资源
     */
    private String resourceDesc;

    /**
     * 联系电话
     */
    private String contactCall;

    /**
     * 机构证件
     */
    private String organizationPaper;

	/**
	 * 推荐状态
	 */
    private Byte recommendStatus;

	/**
	 * 推荐语
	 */
	private String recommendDesc;

	public Byte getRecommendStatus() {
		return recommendStatus;
	}

	public void setRecommendStatus(Byte recommendStatus) {
		this.recommendStatus = recommendStatus;
	}

	public String getRecommendDesc() {
		return recommendDesc;
	}

	public void setRecommendDesc(String recommendDesc) {
		this.recommendDesc = recommendDesc;
	}

	public String getTradeField() {
		return tradeField;
	}

	public void setTradeField(String tradeField) {
		this.tradeField = tradeField == null ? "" : tradeField;
	}

	public Integer getAuthType() {
		return authType;
	}

	public void setAuthType(Integer authType) {
		this.authType = authType;
	}

	public Integer getAuthWay() {
		return authWay;
	}

	public void setAuthWay(Integer authWay) {
		this.authWay = authWay;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName == null ? "" : organizationName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location == null ? "" : location;
	}

	public String getOwnerAppId() {
		return ownerAppId;
	}

	public void setOwnerAppId(String ownerAppId) {
		this.ownerAppId = ownerAppId;
	}

	public String getResourceDesc() {
		return resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc == null ? "" : resourceDesc;
	}

	public String getContactCall() {
		return contactCall;
	}

	public void setContactCall(String contactCall) {
		this.contactCall = contactCall;
	}

	public String getOrganizationPaper() {
		return organizationPaper;
	}

	public void setOrganizationPaper(String organizationPaper) {
		this.organizationPaper = organizationPaper == null ? "" : organizationPaper;
	}

	public Byte getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Byte auditStatus) {
		this.auditStatus = auditStatus;
	}
	
}
