package com.yryz.quanhu.resource.topic.entity;

import java.io.Serializable;

public class TopicPostWithBLOBs extends TopicPost implements Serializable {
    private String content;

    private String contentSource;

    private String imgUrl;

    private String imgThumbnailUrl;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getContentSource() {
        return contentSource;
    }

    public void setContentSource(String contentSource) {
        this.contentSource = contentSource == null ? null : contentSource.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public String getImgThumbnailUrl() {
        return imgThumbnailUrl;
    }

    public void setImgThumbnailUrl(String imgThumbnailUrl) {
        this.imgThumbnailUrl = imgThumbnailUrl == null ? null : imgThumbnailUrl.trim();
    }
}