package com.yryz.quanhu.behavior.like.entity;

import com.yryz.common.entity.GenericEntity;

/**
 * @Author:sun
 * @version:2.0
 * @Description:点赞
 * @Date:Created in 13:38 2018/1/24
 */
public class Like extends GenericEntity {

    /**
     *资源ID
     */
    private long resourceId;

    /**
     *资源类型
     */
    private String moduleEnum;

    /**
     * 点赞人ID
     */
    private long userId;

    public long getResourceId() {
        return resourceId;
    }

    public Like setResourceId(long resourceId) {
        this.resourceId = resourceId;
        return this;
    }

    public String getModuleEnum() {
        return moduleEnum;
    }

    public Like setModuleEnum(String moduleEnum) {
        this.moduleEnum = moduleEnum;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public Like setUserId(long userId) {
        this.userId = userId;
        return this;
    }
}
