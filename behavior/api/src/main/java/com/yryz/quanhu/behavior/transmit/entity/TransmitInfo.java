package com.yryz.quanhu.behavior.transmit.entity;

import java.io.Serializable;
import java.util.Date;

public class TransmitInfo implements Serializable {

    /**
     * 唯一id
     * */
    private Long kid;

    /**
     * 原文ID或动态ID
     * */
    private Long parentId;

    /**
     * 资源ID
     * */
    private Long resourceId;

    /**
     * 资源类型
     * */
    private Integer moduleEnum;

    /**
     * 内容
     * */
    private String content;

    /**
     * 创建用户ID
     * */
    private Long createUserId;

    /**
     * 目标用户
     * */
    private Long targetUserId;

    /**
     * 创建时间
     * */
    private Date createDate;

    private Long createDateLong;

    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getModuleEnum() {
        return moduleEnum;
    }

    public void setModuleEnum(Integer moduleEnum) {
        this.moduleEnum = moduleEnum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(Long targetUserId) {
        this.targetUserId = targetUserId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreateDateLong() {
        return createDateLong;
    }

    public void setCreateDateLong(Long createDateLong) {
        this.createDateLong = createDateLong;
    }
}
