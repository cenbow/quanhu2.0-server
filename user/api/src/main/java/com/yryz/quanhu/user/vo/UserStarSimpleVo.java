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
	 * 达人主键
	 */
	private Long id;
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
	private String custId;
	
	/**
	 * 	认证状态 0:审核中 1:成功 2:失败 3:后台取消认证 4:未填写过认证资料
	 */
	private Byte authStatus;
	
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

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public Byte getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(Byte authStatus) {
		this.authStatus = authStatus;
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
	
}
