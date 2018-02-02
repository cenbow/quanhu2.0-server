package com.yryz.quanhu.resource.questionsAnswers.dto;


import java.io.Serializable;

public class QuestionDto implements Serializable {


    private Long kid;

    private String title;

    private String content;

    private String targetId;

    private Byte shelveFlag;

    private Byte delFlag;

    private Long  coterieId;

    private Long chargeAmount;

    private Byte isOnlyShowMe;

    private Byte isAnonymity;

    private String contentSource;

    private Long createUserId;

    private Integer currentPage;

    private  Integer pageSize;

    private String beginDate;

    private String EndDate;

    private String LastAnswerDateBegin;

    private String LastAnswerDateEnd;

    private String  refundFlag;

    private Byte answerdFlag;

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
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
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

    public Long  getCoterieId() {
        return coterieId;
    }

    public void setCoterieId(Long coterieId) {
        this.coterieId = coterieId;
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

    public String getContentSource() {
        return contentSource;
    }

    public void setContentSource(String contentSource) {
        this.contentSource = contentSource;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
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

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getLastAnswerDateBegin() {
        return LastAnswerDateBegin;
    }

    public void setLastAnswerDateBegin(String lastAnswerDateBegin) {
        LastAnswerDateBegin = lastAnswerDateBegin;
    }

    public String getLastAnswerDateEnd() {
        return LastAnswerDateEnd;
    }

    public void setLastAnswerDateEnd(String lastAnswerDateEnd) {
        LastAnswerDateEnd = lastAnswerDateEnd;
    }

    public String getRefundFlag() {
        return refundFlag;
    }

    public void setRefundFlag(String refundFlag) {
        this.refundFlag = refundFlag;
    }

    public Byte getAnswerdFlag() {
        return answerdFlag;
    }

    public void setAnswerdFlag(Byte answerdFlag) {
        this.answerdFlag = answerdFlag;
    }
}
