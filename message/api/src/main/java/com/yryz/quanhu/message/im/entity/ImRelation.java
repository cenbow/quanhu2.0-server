package com.yryz.quanhu.message.im.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/1/22
 * @description
 */
public class ImRelation implements Serializable {
    /**
     * 当前用户id
     */
    private String userId;
    /**
     * 目标用户id
     */
    private String targetUserId;

    /**
     * 好友备注
     */
    private String nameNotes;

    /**
     * 关系类型,1:黑名单操作，2:静音列表操作
     */
    private String relationType;

    /**
     * 0:取消黑名单或静音，1:加入黑名单或静音
     */
    private String relationValue;

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getRelationValue() {
        return relationValue;
    }

    public void setRelationValue(String relationValue) {
        this.relationValue = relationValue;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getNameNotes() {
        return nameNotes;
    }

    public void setNameNotes(String nameNotes) {
        this.nameNotes = nameNotes;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}