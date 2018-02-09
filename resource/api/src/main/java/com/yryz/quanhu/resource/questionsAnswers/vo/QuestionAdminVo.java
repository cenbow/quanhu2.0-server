package com.yryz.quanhu.resource.questionsAnswers.vo;

import com.alibaba.druid.util.DaemonThreadFactory;


import com.yryz.quanhu.resource.topic.vo.BehaviorVo;
import com.yryz.quanhu.user.vo.UserSimpleVO;

import java.io.Serializable;
import java.util.Date;

public class QuestionAdminVo implements Serializable {

    private Long kid;

    private String title;

    private String content;

    private Byte questionType;

    private Byte shelveFlag;

    private Byte delFlag;

    private Date createDate;

    private String coterieId;

    private Long chargeAmount;

    private Byte isOnlyShowMe;

    private Byte isAnonymity;

    private Byte isValid;

    private Byte answerdFlag;

    private String contentSource;

    private UserSimpleVO user;

    private String moduleEnum;

    private String orderId;

    private String coterieName;

    private Date answerDate;

    private Long lastUpdateUserId;

    private Long createUserId;

    private Byte orderFlag;

    
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

    
    public String getCoterieId() {
        return coterieId;
    }

    public void setCoterieId(String coterieId) {
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

    public Byte getIsValid() {
        return isValid;
    }

    public void setIsValid(Byte isValid) {
        this.isValid = isValid;
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
        this.contentSource = contentSource;
    }

    public UserSimpleVO getUser() {
        return user;
    }

    public void setUser(UserSimpleVO user) {
        this.user = user;
    }

    public String getModuleEnum() {
        return moduleEnum;
    }

    public void setModuleEnum(String moduleEnum) {
        this.moduleEnum = moduleEnum;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCoterieName() {
        return coterieName;
    }

    public void setCoterieName(String coterieName) {
        this.coterieName = coterieName;
    }

    public Date getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(Date answerDate) {
        this.answerDate = answerDate;
    }

    public Long getLastUpdateUserId() {
        return lastUpdateUserId;
    }

    public void setLastUpdateUserId(Long lastUpdateUserId) {
        this.lastUpdateUserId = lastUpdateUserId;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Byte getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(Byte orderFlag) {
        this.orderFlag = orderFlag;
    }
}
