package com.yryz.quanhu.behavior.common.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/2/1 15:22
 * Created by lifan
 */
public class RemoteResource implements Serializable {

    private static final long serialVersionUID = 1223194778536100568L;
    /**
     * 资源类型模块ID
     * 1003文章,1004话题,1005帖子,1006问题,1007答案,1008活动,10081活动作品
     */
    private String moduleEnum;
    /**
     * 资源ID
     */
    private Long resourceId;
    /**
     * 私圈ID
     */
    private Long coterieId;
    /**
     * 封面图
     */
    private String coverPlanUrl;
    /**
     * 标题
     */
    private String title;
    /**
     * 正文（只有文字）
     */
    private String content;
    /**
     * 图片url
     */
    private String imgUrl;
    /**
     * 首张图片url
     */
    private String firstImgUrl;
    /**
     * 音频url
     */
    private String audioUrl;

    /**
     * 视频url
     */
    private String videoUrl;

    /**
     * 视频首帧图url
     */
    private String videoThumbnailUrl;
    /**
     * 删除标记
     * 10正常，11已删除
     */
    private Integer delFlag;
    /**
     * 创建用户ID
     */
    private Long createUserId;
    /**
     * 发布时间
     */
    private Date createDate;

    /**
     * 问题数据，查询答案时会同时返回问题对象
     */
    private Question question = new Question();

    public String getModuleEnum() {
        return moduleEnum;
    }

    public void setModuleEnum(String moduleEnum) {
        this.moduleEnum = moduleEnum;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Long getCoterieId() {
        return coterieId;
    }

    public void setCoterieId(Long coterieId) {
        this.coterieId = coterieId;
    }

    public String getCoverPlanUrl() {
        return coverPlanUrl;
    }

    public void setCoverPlanUrl(String coverPlanUrl) {
        this.coverPlanUrl = coverPlanUrl;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getFirstImgUrl() {
        return firstImgUrl;
    }

    public void setFirstImgUrl(String firstImgUrl) {
        this.firstImgUrl = firstImgUrl;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
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

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Question getQuestion() {
        return question;
    }

    public static class Question implements Serializable {
        private static final long serialVersionUID = -4612888343951697295L;
        /**
         * 正文（只有文字）
         */
        private String content;
        /**
         * 删除标记
         * 10正常，11已删除
         */
        private Integer delFlag;
        /**
         * 创建用户ID
         */
        private Long createUserId;
        /**
         * 发布时间
         */
        private Date createDate;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Integer getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(Integer delFlag) {
            this.delFlag = delFlag;
        }

        public Long getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(Long createUserId) {
            this.createUserId = createUserId;
        }

        public Date getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Date createDate) {
            this.createDate = createDate;
        }
    }
}
