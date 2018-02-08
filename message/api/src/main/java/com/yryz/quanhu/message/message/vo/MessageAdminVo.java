package com.yryz.quanhu.message.message.vo;

import com.yryz.common.message.Body;
import com.yryz.common.message.MessageType;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/30 19:12
 * @Author: pn
 */
public class MessageAdminVo implements Serializable{

    /**
     * 消息ID
     */
    private String messageId;

    /**
     * 消息类型（一级分类）
     * @see MessageType
     */
    private Integer type;

    /**
     * 消息标签（二级分类）
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
     * @see MessageViewCode
     */
    private String viewCode;

    /**
     * 行为类型
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
     * @see Body
     */
    private Object body;

    /**
     * JpushId
     */
    private String jpId;

    //=================================================以下为管理后台字段=================================================================

    /**
     * 持久化类型：1：代表在APP消息列表显示，0：不在消息列表显示
     */
    private Integer persistentType;

    /**
     * 消息推送状态分为三种：0:未推送，表示还没到推送时间的消息；1:进行中，表示正在进行推送的消息；2:已推送，表示已经推送过的消息；3:推送失败
     */
    private Integer pushStatus;

    /**
     * 推送时间（可以在未来的某个时间）
     */
    private String pushDate;

    /**
     * 最后更新人
     */
    private String lastUpdateUserId;

    /**
     * 最后更新时间
     */
    private String lastUpdateDate;

    /**
     * 消息来源，1：手动选择，2：从已有内容中选择
     */
    private Integer messageSource;

    /**
     * 推送方式，1：立即推送，2：定时推送（关联推送时间pushDate）
     */
    private Integer pushType;

    /**
     * 推送用户集合
     */
    private List<String> pushUserIds;

    /**
     * 删除标记（0：已删除，1：正常）
     */
    private Integer delFlag;

    /**
     * 推送人数
     */
    private Long pushNumber;

    /**
     * 最后更新人名字
     */
    private String lastUpdateUserName;

    public String getLastUpdateUserName() {
        return lastUpdateUserName;
    }

    public void setLastUpdateUserName(String lastUpdateUserName) {
        this.lastUpdateUserName = lastUpdateUserName;
    }

    public Long getPushNumber() {
        return pushNumber;
    }

    public void setPushNumber(Long pushNumber) {
        this.pushNumber = pushNumber;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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

    public Integer getPersistentType() {
        return persistentType;
    }

    public void setPersistentType(Integer persistentType) {
        this.persistentType = persistentType;
    }

    public Integer getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Integer pushStatus) {
        this.pushStatus = pushStatus;
    }

    public String getPushDate() {
        return pushDate;
    }

    public void setPushDate(String pushDate) {
        this.pushDate = pushDate;
    }

    public String getLastUpdateUserId() {
        return lastUpdateUserId;
    }

    public void setLastUpdateUserId(String lastUpdateUserId) {
        this.lastUpdateUserId = lastUpdateUserId;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Integer getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(Integer messageSource) {
        this.messageSource = messageSource;
    }

    public Integer getPushType() {
        return pushType;
    }

    public void setPushType(Integer pushType) {
        this.pushType = pushType;
    }

    public List<String> getPushUserIds() {
        return pushUserIds;
    }

    public void setPushUserIds(List<String> pushUserIds) {
        this.pushUserIds = pushUserIds;
    }
}
