package com.yryz.quanhu.support.activity.dto;

import java.io.Serializable;

public class ActivityVoteDto implements Serializable {

    /**页码*/
    private Integer currentPage = 1;
    /**每页大小*/
    private Integer pageSize = 10;
    /** 活动id */
    private Long activityInfoId;
    /** 参与者id */
    private Long candidateId;
    /** 查询条件（编号） */
    private String queryCondition;
    /** 文本 */
    private String content;
    /** 文本1 */
    private String content1;
    /** 文本2 */
    private String content2;
    /** 封面图 */
    private String coverPlan;
    /** 图片url */
    private String imgUrl;
    /** 视频首帧图 */
    private String videoThumbnailUrl;
    /** 视频url */
    private String videoUrl;
    /** 创建用户id */
    private Long createUserId;

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

    public Long getActivityInfoId() {
        return activityInfoId;
    }

    public void setActivityInfoId(Long activityInfoId) {
        this.activityInfoId = activityInfoId;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
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

    public String getVideoThumbnailUrl() {
        return videoThumbnailUrl;
    }

    public void setVideoThumbnailUrl(String videoThumbnailUrl) {
        this.videoThumbnailUrl = videoThumbnailUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }
}
