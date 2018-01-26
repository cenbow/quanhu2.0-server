package com.yryz.quanhu.support.activity.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ActivityVoteDetailVo implements Serializable {

    /**
     * 主键
     * */
    private Long id;

    /**
     * 唯一id
     * */
    private Long kid;

    /**
     * 活动id
     */
    private  Long activityInfoId;

    /**
     * 活动标题
     * */
    private String activityTitle;

    /**
     * 后 一名票数 差
     * */
    private Integer afterVoteDiffer;

    /**
     * 前一名票数 差
     * */
    private Integer frontVoteDiffer;

    /**
     * 是否有评论 0否 1是
     * */
    private Integer commentFlag;

    /**
     * 文本
     */
    private  String content;

    /**
     * 文本1
     */
    private  String content1;

    /**
     * 文本2
     */
    private  String content2;

    /**
     * 编号
     */
    private  Integer voteNo;

    /**
     * 参与获得积分
     */
    private  Integer obtainIntegral;

    /**
     * 封面图
     */
    private  String coverPlan;

    /**
     * 图片url
     */
    private  String imgUrl;

    /**
     * 视频url
     */
    private  String videoUrl;

    /**
     * 视频首帧图url
     */
    private  String videoThumbnailUrl;

    /**
     * 投票数
     */
    @JsonProperty(value = "totalCount")
    private Integer voteCount;

    @JsonIgnore
    private Integer addVote;

    /**
     * 功能id
     */
    private  String moduleEnum;

    /**
     * 是否上下线（10上线 11下线）
     */
    private  Integer shelveFlag;

    /**
     * 总访问量
     */
    private  Integer amountOfAccess;

    /**
     * 在app内投票配置数
     */
    private Integer inAppVoteConfigCount;

    /**
     * 在app内实际投票数
     */
    private Integer inAppVoteCount;

    /**
     * 11活动开始至结束固定票数,12每天可投
     */
    private Integer inAppVoteType;

    /**
     * 在app外投票配置数
     */
    private Integer otherAppVoteConfigCount;

    /**
     * 在app外实际投票数
     */
    private Integer otherAppVoteCount;

    /**
     * 11活动开始至结束固定票数,12每天可投
     */
    private Integer otherAppVoteType;

    /**
     * 用户是否有可用投票卷 10否 11是
     */
    private Integer userRollFlag;

    private Long createUserId;

    private UserActivityVo user;

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

    public Long getActivityInfoId() {
        return activityInfoId;
    }

    public void setActivityInfoId(Long activityInfoId) {
        this.activityInfoId = activityInfoId;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public Integer getAfterVoteDiffer() {
        return afterVoteDiffer;
    }

    public void setAfterVoteDiffer(Integer afterVoteDiffer) {
        this.afterVoteDiffer = afterVoteDiffer;
    }

    public Integer getFrontVoteDiffer() {
        return frontVoteDiffer;
    }

    public void setFrontVoteDiffer(Integer frontVoteDiffer) {
        this.frontVoteDiffer = frontVoteDiffer;
    }

    public Integer getCommentFlag() {
        return commentFlag;
    }

    public void setCommentFlag(Integer commentFlag) {
        this.commentFlag = commentFlag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent1() {
        return content1;
    }

    public void setContent1(String content1) {
        this.content1 = content1;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }

    public Integer getVoteNo() {
        return voteNo;
    }

    public void setVoteNo(Integer voteNo) {
        this.voteNo = voteNo;
    }

    public Integer getObtainIntegral() {
        return obtainIntegral;
    }

    public void setObtainIntegral(Integer obtainIntegral) {
        this.obtainIntegral = obtainIntegral;
    }

    public String getCoverPlan() {
        return coverPlan;
    }

    public void setCoverPlan(String coverPlan) {
        this.coverPlan = coverPlan;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoThumbnailUrl() {
        return videoThumbnailUrl;
    }

    public void setVideoThumbnailUrl(String videoThumbnailUrl) {
        this.videoThumbnailUrl = videoThumbnailUrl;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getAddVote() {
        return addVote;
    }

    public void setAddVote(Integer addVote) {
        this.addVote = addVote;
    }

    public String getModuleEnum() {
        return moduleEnum;
    }

    public void setModuleEnum(String moduleEnum) {
        this.moduleEnum = moduleEnum;
    }

    public Integer getShelveFlag() {
        return shelveFlag;
    }

    public void setShelveFlag(Integer shelveFlag) {
        this.shelveFlag = shelveFlag;
    }

    public Integer getAmountOfAccess() {
        return amountOfAccess;
    }

    public void setAmountOfAccess(Integer amountOfAccess) {
        this.amountOfAccess = amountOfAccess;
    }

    public Integer getInAppVoteConfigCount() {
        return inAppVoteConfigCount;
    }

    public void setInAppVoteConfigCount(Integer inAppVoteConfigCount) {
        this.inAppVoteConfigCount = inAppVoteConfigCount;
    }

    public Integer getInAppVoteCount() {
        return inAppVoteCount;
    }

    public void setInAppVoteCount(Integer inAppVoteCount) {
        this.inAppVoteCount = inAppVoteCount;
    }

    public Integer getInAppVoteType() {
        return inAppVoteType;
    }

    public void setInAppVoteType(Integer inAppVoteType) {
        this.inAppVoteType = inAppVoteType;
    }

    public Integer getOtherAppVoteConfigCount() {
        return otherAppVoteConfigCount;
    }

    public void setOtherAppVoteConfigCount(Integer otherAppVoteConfigCount) {
        this.otherAppVoteConfigCount = otherAppVoteConfigCount;
    }

    public Integer getOtherAppVoteCount() {
        return otherAppVoteCount;
    }

    public void setOtherAppVoteCount(Integer otherAppVoteCount) {
        this.otherAppVoteCount = otherAppVoteCount;
    }

    public Integer getOtherAppVoteType() {
        return otherAppVoteType;
    }

    public void setOtherAppVoteType(Integer otherAppVoteType) {
        this.otherAppVoteType = otherAppVoteType;
    }

    public Integer getUserRollFlag() {
        return userRollFlag;
    }

    public void setUserRollFlag(Integer userRollFlag) {
        this.userRollFlag = userRollFlag;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public UserActivityVo getUser() {
        return user;
    }

    public void setUser(UserActivityVo user) {
        this.user = user;
    }
}
