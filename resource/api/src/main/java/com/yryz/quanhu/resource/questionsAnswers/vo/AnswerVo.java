package com.yryz.quanhu.resource.questionsAnswers.vo;

import com.yryz.quanhu.user.vo.UserSimpleVO;

import java.util.Date;

public class AnswerVo {

    private Long kid;

    private Long questionId;

    private String imgUrl;

    private Byte shelveFlag;

    private Byte delFlag;

    private Date createDate;

    private Long createUserId;

    private Integer revision;

    private String operatorId;

    private Byte answerType;

    private String answerAudio;

    private String coterieId;

    private Long audioLength;

    private String content;

    private String contentSource;

    private UserSimpleVO user;

    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
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
        this.operatorId = operatorId;
    }

    public Byte getAnswerType() {
        return answerType;
    }

    public void setAnswerType(Byte answerType) {
        this.answerType = answerType;
    }

    public String getAnswerAudio() {
        return answerAudio;
    }

    public void setAnswerAudio(String answerAudio) {
        this.answerAudio = answerAudio;
    }

    public String getCoterieId() {
        return coterieId;
    }

    public void setCoterieId(String coterieId) {
        this.coterieId = coterieId;
    }

    public Long getAudioLength() {
        return audioLength;
    }

    public void setAudioLength(Long audioLength) {
        this.audioLength = audioLength;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
