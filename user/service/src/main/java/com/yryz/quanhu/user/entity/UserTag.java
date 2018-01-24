/*
 * UserTag.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2018-01-24 Created
 */
package com.yryz.quanhu.user.entity;

import com.yryz.common.entity.GenericEntity;

/**
 * 用户标签表
 * 
 * @author xxx
 * @version 1.0 2018-01-24
 * @since 1.0
 */
@SuppressWarnings("serial")
public class UserTag extends GenericEntity{
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 标签类型 10-用户自选 11-运营设置达人推荐标签
     */
    private Byte tagType;

    /**
     * 标签id
     */
    private Long tagId;
    
    private Byte delFlag;
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Byte getTagType() {
        return tagType;
    }

    public void setTagType(Byte tagType) {
        this.tagType = tagType;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }


    public Byte getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }
}