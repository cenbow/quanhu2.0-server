package com.yryz.quanhu.message.message.vo;

import java.io.Serializable;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/20 17:58
 * @Author: pn
 */
public class MessageVo implements Serializable {
    /**
     * 消息ID
     */
    private String messageId;

    /**
     * 消息类型（一级分类）
     *
     * @see MessageType
     */
    private Integer type;

    /**
     * 消息标签（二级分类）
     *
     * @see MessageLabel
     */
    private Integer label;

    /**
     * 消息枚举类型 ，唯一
     */
    private String msgEnumType;
    /**
     * 目标用户ID
     */
    private String toCust;

    /**
     * 展示类型
     *
     * @see MessageViewCode
     */
    private String viewCode;

    /**
     * 行为类型
     *
     * @see MessageActionCode
     */
    private String actionCode;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 图片
     */
    private String img;

    /**
     * 链接
     */
    private String link;

    /**
     * 发布时间
     */
    private String createTime;

    /**
     * 圈子工程名称,可选，有就填写
     */
    private String circleRoute;

    /**
     * 功能ID，可选，有就填写
     */
    private String moduleEnum;

    /**
     * 资源ID，可选，如果是资源就填写资源ID
     */
    private String resourceId;

    /**
     * 私圈ID，可选，如果是私圈或者私圈资源，则填写
     */
    private String coterieId;

    /**
     * 圈子ID
     */
    private String circleId;

    /**
     * 拓展数据：资源信息,请填入Body的实体映射表
     *
     * @see Body
     */
    private Object body;

    /**
     * JpushId
     */
    private String jpId;

    public MessageVo() {
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLabel() {
        return label;
    }

    public void setLabel(Integer label) {
        this.label = label;
    }

    public String getMsgEnumType() {
        return msgEnumType;
    }

    public void setMsgEnumType(String msgEnumType) {
        this.msgEnumType = msgEnumType;
    }

    public String getToCust() {
        return toCust;
    }

    public void setToCust(String toCust) {
        this.toCust = toCust;
    }

    public String getViewCode() {
        return viewCode;
    }

    public void setViewCode(String viewCode) {
        this.viewCode = viewCode;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCircleRoute() {
        return circleRoute;
    }

    public void setCircleRoute(String circleRoute) {
        this.circleRoute = circleRoute;
    }

    public String getModuleEnum() {
        return moduleEnum;
    }

    public void setModuleEnum(String moduleEnum) {
        this.moduleEnum = moduleEnum;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getCoterieId() {
        return coterieId;
    }

    public void setCoterieId(String coterieId) {
        this.coterieId = coterieId;
    }

    public String getCircleId() {
        return circleId;
    }

    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getJpId() {
        return jpId;
    }

    public void setJpId(String jpId) {
        this.jpId = jpId;
    }
}
