package com.yryz.quanhu.behavior.comment.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * @Author:sun
 * @version:2.0
 * @Description:评论组装组装
 * @Date:Created in 13:48 2018/2/1
 */
public class CommentAssemble {

    private String content;
    private String img;
    private String title;
    private String link;
    @JsonSerialize(using = ToStringSerializer.class)
    private long targetUserId;
    private Byte viewCode;
    private String moduleEnum;
    @JsonSerialize(using = ToStringSerializer.class)
    private long resourceId;
    @JsonSerialize(using = ToStringSerializer.class)
    private long coterieId;
    private String coterieName;
    private String userNickName;
    private String userImg;
    @JsonSerialize(using = ToStringSerializer.class)
    private long resourceUserId;
    private String bodyImg;
    private String bodyTitle;

    public String getBodyImg() {
        return bodyImg;
    }

    public void setBodyImg(String bodyImg) {
        this.bodyImg = bodyImg;
    }

    public String getBodyTitle() {
        return bodyTitle;
    }

    public void setBodyTitle(String bodyTitle) {
        this.bodyTitle = bodyTitle;
    }

    public long getResourceUserId() {
        return resourceUserId;
    }

    public void setResourceUserId(long resourceUserId) {
        this.resourceUserId = resourceUserId;
    }

    public String getCoterieName() {
        return coterieName;
    }

    public void setCoterieName(String coterieName) {
        this.coterieName = coterieName;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getModuleEnum() {
        return moduleEnum;
    }

    public void setModuleEnum(String moduleEnum) {
        this.moduleEnum = moduleEnum;
    }

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }

    public long getCoterieId() {
        return coterieId;
    }

    public void setCoterieId(long coterieId) {
        this.coterieId = coterieId;
    }

    public Byte getViewCode() {
        return viewCode;
    }

    public void setViewCode(Byte viewCode) {
        this.viewCode = viewCode;
    }

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
