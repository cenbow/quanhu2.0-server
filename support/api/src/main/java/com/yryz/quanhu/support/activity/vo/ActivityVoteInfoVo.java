package com.yryz.quanhu.support.activity.vo;

import java.io.Serializable;
import java.util.Date;

public class ActivityVoteInfoVo implements Serializable {

    /**
     * 唯一id
     * */
    private Long kid;

    /**
     * 标题
     */
    private String title;

    /**
     * 活动介绍
     */
    private  String content;

    /**
     * 活动介绍元数据
     */
    private  String contentSources;

    /**
     * 封面图
     */
    private String coverPlan;

    /**
     * 活动类型(11报名 12投票)
     */
    private Integer activityType;

    /**
     * 上线时间
     */
    private Date onlineTime;

    /**
     * 开始时间
     */
    private  Date beginTime;

    /**
     * 结束时间
     */
    private  Date endTime;

    /**
     * 是否推荐(10未推荐 11推荐)
     */
    private Integer recommend;

    /**
     * 推荐时间
     */
    private Date recommendDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 报名/参与数
     */
    private Integer joinCount;

    /**
     * 是否上下线（10上线 11下线）
     */
    private Integer shelveFlag;

    /**
     * 是否发布中奖公告(10否 11是)
     */
    private Integer prizesAnnouncementFlag;

    /**
     * 中奖公告数据
     */
    private String prizesSources;

    /**
     * 活动奖励介绍
     */
    private String introduceSources;

    /**
     * 活动渠道码
     */
    private String activityChannelCode;

    /**
     * 功能枚举
     */
    private String moduleEnum;

    /**
     * 当前时间
     * */
    private Date currentDate;

    /**
     * 是否有奖品 10否 11是
     * */
    private Integer prizesFlag;

    /**
     *  用户是否可参与 10否 11是
     * */
    private Integer userFlag;

    /**
     *  当前用户是否参加这个活动 10否 11是
     * */
    private Integer joinFlag;

    /**
     * 参与人数上限
     * */
    private Integer userNum;

    /**
     *  活动参加开始时间
     * */
    private Date activityJoinBegin;

    /**
     *  活动参加结束时间
     * */
    private Date activityJoinEnd;

    /**
     *  活动投票开始时间
     * */
    private Date activityVoteBegin;

    /**
     *  活动投票结束时间
     * */
    private Date activityVoteEnd;

    /**
     *  参与获得积分值
     * */
    private Integer amount;

    /**
     *  活动状态 11未开始 12进行中 13结束
     * */
    private Integer activityStatus;

    /**
     *  总访问量
     * */
    private Long amountOfAccess;

    /**
     * 是否可评论(10否 11是)
     */
    private  Integer commentFlag;

    /**
     * app内投票规则   11,整个活动时间内的总投票数  12,活动期间每天可投
     */
    private  Integer inAppVoteType;

    /**
     * app内配置票数
     */
    private  Integer inAppVoteConfigCount;

    /**
     * app外投票规则   11,整个活动时间内的总投票数  12,活动期间每天可投
     */
    private  Integer otherAppVoteType;

    /**
     * app外配置票数
     */
    private  Integer otherAppVoteConfigCount;



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

    public String getContentSources() {
        return contentSources;
    }

    public void setContentSources(String contentSources) {
        this.contentSources = contentSources;
    }

    public String getCoverPlan() {
        return coverPlan;
    }

    public void setCoverPlan(String coverPlan) {
        this.coverPlan = coverPlan;
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }

    public Date getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Date onlineTime) {
        this.onlineTime = onlineTime;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public Date getRecommendDate() {
        return recommendDate;
    }

    public void setRecommendDate(Date recommendDate) {
        this.recommendDate = recommendDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(Integer joinCount) {
        this.joinCount = joinCount;
    }

    public Integer getShelveFlag() {
        return shelveFlag;
    }

    public void setShelveFlag(Integer shelveFlag) {
        this.shelveFlag = shelveFlag;
    }

    public Integer getPrizesAnnouncementFlag() {
        return prizesAnnouncementFlag;
    }

    public void setPrizesAnnouncementFlag(Integer prizesAnnouncementFlag) {
        this.prizesAnnouncementFlag = prizesAnnouncementFlag;
    }

    public String getPrizesSources() {
        return prizesSources;
    }

    public void setPrizesSources(String prizesSources) {
        this.prizesSources = prizesSources;
    }

    public String getIntroduceSources() {
        return introduceSources;
    }

    public void setIntroduceSources(String introduceSources) {
        this.introduceSources = introduceSources;
    }

    public String getActivityChannelCode() {
        return activityChannelCode;
    }

    public void setActivityChannelCode(String activityChannelCode) {
        this.activityChannelCode = activityChannelCode;
    }

    public String getModuleEnum() {
        return moduleEnum;
    }

    public void setModuleEnum(String moduleEnum) {
        this.moduleEnum = moduleEnum;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Integer getPrizesFlag() {
        return prizesFlag;
    }

    public void setPrizesFlag(Integer prizesFlag) {
        this.prizesFlag = prizesFlag;
    }

    public Integer getUserFlag() {
        return userFlag;
    }

    public void setUserFlag(Integer userFlag) {
        this.userFlag = userFlag;
    }

    public Integer getJoinFlag() {
        return joinFlag;
    }

    public void setJoinFlag(Integer joinFlag) {
        this.joinFlag = joinFlag;
    }

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public Date getActivityJoinBegin() {
        return activityJoinBegin;
    }

    public void setActivityJoinBegin(Date activityJoinBegin) {
        this.activityJoinBegin = activityJoinBegin;
    }

    public Date getActivityJoinEnd() {
        return activityJoinEnd;
    }

    public void setActivityJoinEnd(Date activityJoinEnd) {
        this.activityJoinEnd = activityJoinEnd;
    }

    public Date getActivityVoteBegin() {
        return activityVoteBegin;
    }

    public void setActivityVoteBegin(Date activityVoteBegin) {
        this.activityVoteBegin = activityVoteBegin;
    }

    public Date getActivityVoteEnd() {
        return activityVoteEnd;
    }

    public void setActivityVoteEnd(Date activityVoteEnd) {
        this.activityVoteEnd = activityVoteEnd;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(Integer activityStatus) {
        this.activityStatus = activityStatus;
    }

    public Long getAmountOfAccess() {
        return amountOfAccess;
    }

    public void setAmountOfAccess(Long amountOfAccess) {
        this.amountOfAccess = amountOfAccess;
    }

    public Integer getCommentFlag() {
        return commentFlag;
    }

    public void setCommentFlag(Integer commentFlag) {
        this.commentFlag = commentFlag;
    }

    public Integer getInAppVoteType() {
        return inAppVoteType;
    }

    public void setInAppVoteType(Integer inAppVoteType) {
        this.inAppVoteType = inAppVoteType;
    }

    public Integer getInAppVoteConfigCount() {
        return inAppVoteConfigCount;
    }

    public void setInAppVoteConfigCount(Integer inAppVoteConfigCount) {
        this.inAppVoteConfigCount = inAppVoteConfigCount;
    }

    public Integer getOtherAppVoteType() {
        return otherAppVoteType;
    }

    public void setOtherAppVoteType(Integer otherAppVoteType) {
        this.otherAppVoteType = otherAppVoteType;
    }

    public Integer getOtherAppVoteConfigCount() {
        return otherAppVoteConfigCount;
    }

    public void setOtherAppVoteConfigCount(Integer otherAppVoteConfigCount) {
        this.otherAppVoteConfigCount = otherAppVoteConfigCount;
    }
}
