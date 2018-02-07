package com.yryz.quanhu.behavior.comment.dto;

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
    private long targetUserId;
    private Byte viewCode;
    private String moduleEnum;
    private long resourceId;
    private long coterieId;

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
