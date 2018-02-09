package com.yryz.quanhu.dymaic.canal.entity;



import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/1/29
 * @description
 */
public class UserStarInfo {

    @Field(type=FieldType.Long)
    private Long kid;
    /**
     * 用户id
     */
    
    private Long userId;

    /**
     * 机构名称
     */
    @Field(type = FieldType.text)
    private String organizationName;

    /**
     * 真实姓名或者运营者姓名
     */
    @Field(type = FieldType.text)
    private String realName;

    /**
     * 身份证号
     */
    @Field(type = FieldType.text)
    private String idCard;

    /**
     * 所在地区
     */
    @Field(type = FieldType.text)
    private String location;

    /**
     * 行业以及领域
     */
    @Field(type = FieldType.text)
    private String tradeField;

    /**
     * 自媒体账户
     */
    @Field(type = FieldType.text)
    private String ownerAppId;

    /**
     * 可提供的资源或者拥有的资源
     */
    @Field(type = FieldType.text)
    private String resourceDesc;

    /**
     * 联系电话
     */
    @Field(type = FieldType.text)
    private String contactCall;

    /**
     * 机构证件
     */
    @Field(type = FieldType.text)
    private String organizationPaper;

    /**
     * 认证类型 10:个人认证 11:企业/机构认证
     */
    @Field(type = FieldType.Integer)
    private Byte authType;
    /**
     * 应用id
     */
    @Field(type = FieldType.text)
    private String appId;


    /**
     * 认证方式 10:用户申请 11:平台设置
     */
    @Field(type = FieldType.Integer)
    private Byte authWay;


    /**
     * 审核状态 10:待审核 11:审核通过 12:审核失败 13:取消认证
     */
    @Field(type = FieldType.Integer)
    private Byte auditStatus;

    /**
     * 是否被推荐 10:否 11:是
     */
    @Field(type = FieldType.Integer)
    private Byte recommendStatus;


    /**
     * 推荐语
     */
    @Field(type = FieldType.text)
    private String recommendDesc;

    /**
     * 推荐排序权重
     */
    @Field(type = FieldType.Integer)
    private Integer recommendHeight;
    /**
     * 操作人名称
     */
    @Field(type = FieldType.text)
    private String operational;

    /**
     * 推荐人
     */
    @Field(type = FieldType.text)
    private String recommendOperate;

    /**
     * 审核拒绝原因
     */
    @Field(type = FieldType.text)
    private String auditFailReason;

    /**
     * 认证通过时间
     */
    @Field(type = FieldType.Date)
    private Date authTime;

    /**
     * 认证失败时间
     */
    private Date auditFailTime;

    /**
     * 取消认证时间
     */
    @Field(type = FieldType.Date)
    private Date authCancelTime;

    /**
     * 达人推荐时间
     */
    @Field(type = FieldType.Date)
    private Date recommendTime;

    /**
     * 取消推荐时间
     */
    @Field(type = FieldType.Date)
    private Date recommendCancelTime;

    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
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
        this.organizationName = organizationName == null ? null : organizationName.trim();
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
        this.recommendOperate = recommendOperate == null ? null : recommendOperate.trim();
    }

    public String getAuditFailReason() {
        return auditFailReason;
    }

    public void setAuditFailReason(String auditFailReason) {
        this.auditFailReason = auditFailReason == null ? null : auditFailReason.trim();
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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
