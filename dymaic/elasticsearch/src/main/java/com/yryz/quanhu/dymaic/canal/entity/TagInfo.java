package com.yryz.quanhu.dymaic.canal.entity;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/1/27
 * @description
 */
public class TagInfo {
    /**
     * userId
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 标签类型 10-用户自选 11-运营设置达人推荐标签
     */
    @Field(type=FieldType.Integer)
    private Byte tagType;

    /**
     * 标签id
     */
    @Field(type=FieldType.Long)
    private Long tagId;



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
}