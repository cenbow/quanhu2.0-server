package com.yryz.quanhu.dymaic.vo;

import java.io.Serializable;

/**
 * 动态基础信息
 *
 * @author xiepeng
 * @version 1.0
 * @data 2018/1/19 0019 29
 */
public class Dymaic implements Serializable {


    /**
     * 动态ID
     */
    private Long kid;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 资源类型模块ID
     * 1000私圈,1001用户,1002转发,1003文章,1004话题,1005帖子,1006问题,1007答案
     */
    private Integer moduleEnum;

    /**
     * 资源ID
     */
    private Integer resourceId;

    /**
     * 文章分类ID
     */
    private Integer classifyId;

    /**
     * 私圈ID
     */
    private String coterieId;

    /**
     * 转发理由
     */
    private String transmitNote;

    /**
     * 转发资源类型模块ID
     * 1000私圈,1001用户,1002转发,1003文章,1004话题,1005帖子,1006问题,1007答案
     */
    private Integer transmitType;

    /**
     * 动态标题
     */
    private String title;

    /**
     * 动态正文
     */
    private String content;

    /**
     * 单张封面图
     */
    private String coverPlanUrl;

    /**
     * 多图相册
     */
    private String imgUrl;

    /**
     * 视频地址
     */
    private String videoUrl;

    /**
     * 视频预览图
     */
    private String videoThumbnailUrl;

    /**
     * 音频地址
     */
    private String audioUrl;

    /**
     * 发布说明
     */
    private String releaseNote;

    /**
     * 删除标记
     * 10正常，11已删除
     */
    private Integer delFlag;

    /**
     * 发布时间
     */
    private String createDate;


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

    public Integer getModuleEnum() {
        return moduleEnum;
    }

    public void setModuleEnum(Integer moduleEnum) {
        this.moduleEnum = moduleEnum;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Integer classifyId) {
        this.classifyId = classifyId;
    }

    public String getCoterieId() {
        return coterieId;
    }

    public void setCoterieId(String coterieId) {
        this.coterieId = coterieId;
    }

    public String getTransmitNote() {
        return transmitNote;
    }

    public void setTransmitNote(String transmitNote) {
        this.transmitNote = transmitNote;
    }

    public Integer getTransmitType() {
        return transmitType;
    }

    public void setTransmitType(Integer transmitType) {
        this.transmitType = transmitType;
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

    public String getCoverPlanUrl() {
        return coverPlanUrl;
    }

    public void setCoverPlanUrl(String coverPlanUrl) {
        this.coverPlanUrl = coverPlanUrl;
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

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getReleaseNote() {
        return releaseNote;
    }

    public void setReleaseNote(String releaseNote) {
        this.releaseNote = releaseNote;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
