package com.yryz.quanhu.user.dto;

import com.yryz.common.entity.GenericEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/2/3 13:42
 * Created by huangxy
 */
public class UserStarAuthDto extends GenericEntity{

    /**
     * 用户id
     */
    private Long userId;

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
     * 应用id
     */
    private String appId;

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
     * 推荐语
     */
    private String recommendDesc;
    /**
     * 推荐排序权重
     */
    private Integer recommendHeight;
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

    private Set<Long> tagIds;

    private String tagNames;

    private int pageNo;

    private int pageSize;

    private String beginDate;

    private String endDate;

    private Integer userLevel;

    public Set<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(Set<Long> tagIds) {
        this.tagIds = tagIds;
    }

    public String getTagNames() {
        return tagNames;
    }

    public void setTagNames(String tagNames) {
        this.tagNames = tagNames;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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
        this.organizationName = organizationName;
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
        this.location = location;
    }

    public String getTradeField() {
        return tradeField;
    }

    public void setTradeField(String tradeField) {
        this.tradeField = tradeField;
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
        this.resourceDesc = resourceDesc;
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
        this.organizationPaper = organizationPaper;
    }

    public Byte getAuthType() {
        return authType;
    }

    public void setAuthType(Byte authType) {
        this.authType = authType;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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

    public String getRecommendDesc() {
        return recommendDesc;
    }

    public void setRecommendDesc(String recommendDesc) {
        this.recommendDesc = recommendDesc;
    }

    public Integer getRecommendHeight() {
        return recommendHeight;
    }

    public void setRecommendHeight(Integer recommendHeight) {
        this.recommendHeight = recommendHeight;
    }

    public String getOperational() {
        return operational;
    }

    public void setOperational(String operational) {
        this.operational = operational;
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
}
