package com.yryz.quanhu.dymaic.canal.entity;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/1/27
 * @description
 */
public class UserTagInfo {
    @Field(type= FieldType.Long)
    private Long userId;


    /**
     * 用户标签信息
     */
    @Field(type= FieldType.Auto)
    private List<TagInfo> userTagInfo;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<TagInfo> getUserTagInfo() {
        return userTagInfo;
    }

    public void setUserTagInfo(List<TagInfo> userTagInfo) {
        this.userTagInfo = userTagInfo;
    }


}
