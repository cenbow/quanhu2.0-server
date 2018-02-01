package com.yryz.quanhu.resource.questionsAnswers.entity;

import java.io.Serializable;
import java.util.Date;

public class Question  implements Serializable{
    private Long id;

    private Long kid;

    private String title;

    private String content;

    private String targetId;

    private Byte questionType;

    private Byte shelveFlag;

    private Byte delFlag;

    private Date createDate;

    private Date lastUpdateDate;

    private Long createUserId;

    private Long lastUpdateUserId;

    private Integer revision;

    private String operatorId;

    private Long coterieId;

    private Integer validTime;

    private Long chargeAmount;

    private Byte isOnlyShowMe;

    private Byte isAnonymity;

    private Byte isValid;

    private String cityCode;

    private String gps;

    private Date operateShelveDate;

    private Byte orderFlag;

    private String orderId;

    private String refundOrderId;

    private Byte answerdFlag;

    private String contentSource;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId == null ? null : targetId.trim();
    }

    public Byte getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Byte questionType) {
        this.questionType = questionType;
    }

    public Byte getShelveFlag() {
        return shelveFlag;
    }

    public void setShelveFlag(Byte shelveFlag) {
        this.shelveFlag = shelveFlag;
    }

    public Byte getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getLastUpdateUserId() {
        return lastUpdateUserId;
    }

    public void setLastUpdateUserId(Long lastUpdateUserId) {
        this.lastUpdateUserId = lastUpdateUserId;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId == null ? null : operatorId.trim();
    }

    public Long getCoterieId() {
        return coterieId;
    }

    public void setCoterieId(Long coterieId) {
        this.coterieId = coterieId;
    }

    public Integer getValidTime() {
        return validTime;
    }

    public void setValidTime(Integer validTime) {
        this.validTime = validTime;
    }

    public Long getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(Long chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public Byte getIsOnlyShowMe() {
        return isOnlyShowMe;
    }

    public void setIsOnlyShowMe(Byte isOnlyShowMe) {
        this.isOnlyShowMe = isOnlyShowMe;
    }

    public Byte getIsAnonymity() {
        return isAnonymity;
    }

    public void setIsAnonymity(Byte isAnonymity) {
        this.isAnonymity = isAnonymity;
    }

    public Byte getIsValid() {
        return isValid;
    }

    public void setIsValid(Byte isValid) {
        this.isValid = isValid;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps == null ? null : gps.trim();
    }

    public Date getOperateShelveDate() {
        return operateShelveDate;
    }

    public void setOperateShelveDate(Date operateShelveDate) {
        this.operateShelveDate = operateShelveDate;
    }

    public Byte getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(Byte orderFlag) {
        this.orderFlag = orderFlag;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getRefundOrderId() {
        return refundOrderId;
    }

    public void setRefundOrderId(String refundOrderId) {
        this.refundOrderId = refundOrderId == null ? null : refundOrderId.trim();
    }

    public Byte getAnswerdFlag() {
        return answerdFlag;
    }

    public void setAnswerdFlag(Byte answerdFlag) {
        this.answerdFlag = answerdFlag;
    }

    public String getContentSource() {
        return contentSource;
    }

    public void setContentSource(String contentSource) {
        this.contentSource = contentSource == null ? null : contentSource.trim();
    }
}