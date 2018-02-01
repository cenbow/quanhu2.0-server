package com.yryz.quanhu.dymaic.canal.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/2/1
 * @description
 */
public class EventAccountInfo {

    /**
     * 用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 总积分
     */
    @Field(type = FieldType.Integer)
    private Long score;

    /**
     * 成长值
     */
    @Field(type = FieldType.Integer)
    private Long grow = 0L;

    /**
     * 成长级别
     */
    @Field(type = FieldType.text)
    private String growLevel;

    /**
     * 创建时间
     */
    @Field(type=FieldType.Date)
    private Date createTime;

    /**
     * 更新时间
     */
    @Field(type=FieldType.Date)
    private Date updateTime;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getGrow() {
        return grow;
    }

    public void setGrow(Long grow) {
        this.grow = grow;
    }

    public String getGrowLevel() {
        return growLevel;
    }

    public void setGrowLevel(String growLevel) {
        this.growLevel = growLevel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
