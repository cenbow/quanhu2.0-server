package com.yryz.quanhu.message.notice.entity;

import com.yryz.common.entity.GenericEntity;

import java.util.Date;

/**
 * @author pengnian
 * @ClassName: Notice
 * @Description: 公告表实体类
 * @date 2018-01-20 16:54:53
 */
public class Notice extends GenericEntity {
    /**
     * 公告标题
     */
    private String title;

    /**
     * 正文路径
     */
    private String contentPath;

    /**
     * 公告状态 0:下架 1:上架 2:已删除
     */
    private Integer noticeStatus;

    /**
     * 公告类型 0:内链 1:外链
     */
    private Integer noticeType;

    /**
     * 缩略图
     */
    private String smallPic;

    /**
     * 主体内容
     */
    private String content;

    /**
     * 上刊时间
     */
    private Date publishDate;

    /**
     * 简介
     */
    private String noticeDesc;

    /**
     * 应用id
     */
    private String appId;

    /**
     * app名字
     */
    private String appName;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentPath() {
        return this.contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public Integer getNoticeStatus() {
        return this.noticeStatus;
    }

    public void setNoticeStatus(Integer noticeStatus) {
        this.noticeStatus = noticeStatus;
    }

    public Integer getNoticeType() {
        return this.noticeType;
    }

    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }

    public String getSmallPic() {
        return this.smallPic;
    }

    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishDate() {
        return this.publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getNoticeDesc() {
        return this.noticeDesc;
    }

    public void setNoticeDesc(String noticeDesc) {
        this.noticeDesc = noticeDesc;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

}