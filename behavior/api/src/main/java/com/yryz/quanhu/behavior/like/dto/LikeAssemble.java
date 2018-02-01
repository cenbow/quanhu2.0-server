package com.yryz.quanhu.behavior.like.dto;

/**
 * @Author:sun
 * @version:2.0
 * @Description:点赞组装
 * @Date:Created in 13:48 2018/2/1
 */
public class LikeAssemble {

    private String content;
    private String img;
    private String title;
    private String link;
    private long targetUserId;

    public long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(long targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
