package com.yryz.quanhu.behavior.like.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.yryz.common.mongodb.Page;
import com.yryz.common.response.PageList;

/**
 * @Author:sun
 * @version:2.0
 * @Description:点赞
 * @Date:Created in 10:20 2018/1/25
 */
public class LikeFrontDTO extends PageList {

    /**
     * 资源ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private long resourceId;

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }
}
