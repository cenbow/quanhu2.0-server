package com.yryz.quanhu.message.notice.vo;

import java.io.Serializable;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/23 15:53
 * @Author: pn
 */
public class NoticeDetailVo implements Serializable {

    /**
     * 公告id
     */
    private Long kid;

    /**
     * 公告类型 :(参照广告跳转类型)
     */
    private Long noticeType;

    /**
     * 主体内容
     */
    private String content;

    /**
     * 正文路径
     */
    private String contentPath;

    /**
     * 公告标题
     */
    private String title;


    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
    }

    public Long getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Long noticeType) {
        this.noticeType = noticeType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
