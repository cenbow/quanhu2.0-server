package com.yryz.quanhu.user.vo;

import java.io.Serializable;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/2/1
 * @description
 */
public class UserTagVO implements Serializable {
    /**
     *
     */
    private Long kid;

    /**
     * userId
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

    /**
     * 删除字段
     */
    private Byte delFlag;

    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
    }

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
