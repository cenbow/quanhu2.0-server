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

    private Integer pageNum;

    private  Integer pageSize;

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

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
