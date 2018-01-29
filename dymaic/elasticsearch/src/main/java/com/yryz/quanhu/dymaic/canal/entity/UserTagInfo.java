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

    /**
     * 用户标签信息
     */
    @Field(type= FieldType.Auto)
    private List<TagInfo> userTagInfoList;

    public List<TagInfo> getUserTagInfoList() {
        return userTagInfoList;
    }

    public void setUserTagInfoList(List<TagInfo> userTagInfoList) {
        this.userTagInfoList = userTagInfoList;
    }
}
