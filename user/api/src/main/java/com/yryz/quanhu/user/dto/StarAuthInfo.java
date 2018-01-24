package com.yryz.quanhu.user.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 达人认证信息
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class StarAuthInfo implements Serializable {
    /**
     * 应用id
     */
    private String appId;
    /**
     * 机构名称
     */
    private String organizationName;

    /**
     * 用户id
     */
    private String userId;

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
     * 行业以及领域
     */
    private String tradeField;

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

    /**
     * 申请时间
     */
    private Date createTime;

    /**
     * 操作时间
     */
    private Date updateTime;

    /**
     * 操作人名称
     */
    private String operational;

    /**
     * 推荐人
     */
    private String recommendOperate;

    /**
     * 审核拒绝原因
     */
    private String auditFailReason;

    /**
     * 认证通过时间
     */
    private Date authTime;

    /**
     * 认证失败时间
     */
    private Date auditFailTime;

    /**
     * 取消认证时间
     */
    private Date authCancelTime;

    /**
     * 达人推荐时间
     */
    private Date recommendTime;

    /**
     * 取消推荐时间
     */
    private Date recommendCancelTime;
    /**
     * 达人推荐权重值
     */
    private Integer recommendHeight;
    /**
     * 达人推荐语
     */
    private String recommendDesc;

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName == null ? null : organizationName.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getTradeField() {
        return tradeField;
    }

    public void setTradeField(String tradeField) {
        this.tradeField = tradeField == null ? null : tradeField.trim();
    }

    public String getOwnerAppId() {
        return ownerAppId;
    }

    public void setOwnerAppId(String ownerAppId) {
        this.ownerAppId = ownerAppId == null ? null : ownerAppId.trim();
    }

    public String getResourceDesc() {
        return resourceDesc;
    }

    public void setResourceDesc(String resourceDesc) {
        this.resourceDesc = resourceDesc == null ? null : resourceDesc.trim();
    }

    public String getContactCall() {
        return contactCall;
    }

    public void setContactCall(String contactCall) {
        this.contactCall = contactCall == null ? null : contactCall.trim();
    }

    public String getOrganizationPaper() {
        return organizationPaper;
    }

    public void setOrganizationPaper(String organizationPaper) {
        this.organizationPaper = organizationPaper == null ? null : organizationPaper.trim();
    }


	public Integer getRecommendHeight() {
		return recommendHeight;
	}

	public void setRecommendHeight(Integer recommendHeight) {
		this.recommendHeight = recommendHeight;
	}

	public String getRecommendDesc() {
		return recommendDesc;
	}

	public void setRecommendDesc(String recommendDesc) {
		this.recommendDesc = recommendDesc;
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

    public Byte getRecommendStatus() {
        return recommendStatus;
    }

    public void setRecommendStatus(Byte recommendStatus) {
        this.recommendStatus = recommendStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOperational() {
        return operational;
    }

    public void setOperational(String operational) {
        this.operational = operational == null ? null : operational.trim();
    }

	public String getRecommendOperate() {
		return recommendOperate;
	}

	public void setRecommendOperate(String recommendOperate) {
		this.recommendOperate = recommendOperate;
	}

	public String getAuditFailReason() {
		return auditFailReason;
	}

	public void setAuditFailReason(String auditFailReason) {
		this.auditFailReason = auditFailReason;
	}

	public Date getAuthTime() {
		return authTime;
	}

	public void setAuthTime(Date authTime) {
		this.authTime = authTime;
	}

	public Date getAuditFailTime() {
		return auditFailTime;
	}

	public void setAuditFailTime(Date auditFailTime) {
		this.auditFailTime = auditFailTime;
	}

	public Date getAuthCancelTime() {
		return authCancelTime;
	}

	public void setAuthCancelTime(Date authCancelTime) {
		this.authCancelTime = authCancelTime;
	}

	public Date getRecommendTime() {
		return recommendTime;
	}

	public void setRecommendTime(Date recommendTime) {
		this.recommendTime = recommendTime;
	}

	public Date getRecommendCancelTime() {
		return recommendCancelTime;
	}

	public void setRecommendCancelTime(Date recommendCancelTime) {
		this.recommendCancelTime = recommendCancelTime;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	
	public StarAuthInfo() {
		super();
	}

	/**
	 * 达人审核
	 * @param userId
	 * @param auditStatus
	 * @param operational
	 * @param auditFailReason
	 */
	public StarAuthInfo(String userId, Byte auditStatus, String operational, String auditFailReason) {
		super();
		this.userId = userId;
		this.auditStatus = auditStatus;
		this.operational = operational;
		this.auditFailReason = auditFailReason;
	}

	/**
	 * 达人推荐
	 * @param userId
	 * @param recommendStatus
	 * @param operational
	 * @param recommendOperate
	 * @param recommendDesc
	 */
	public StarAuthInfo(String userId, Byte recommendStatus, String operational, String recommendOperate,
			String recommendDesc) {
		super();
		this.userId = userId;
		this.recommendStatus = recommendStatus;
		this.operational = operational;
		this.recommendOperate = recommendOperate;
		this.recommendDesc = recommendDesc;
	}

	/**
	 * 达人推荐权重更新
	 * @param userId
	 * @param recommendHeight
	 */
	public StarAuthInfo(String userId, Integer recommendHeight) {
		super();
		this.userId = userId;
		this.recommendHeight = recommendHeight;
	}

}
