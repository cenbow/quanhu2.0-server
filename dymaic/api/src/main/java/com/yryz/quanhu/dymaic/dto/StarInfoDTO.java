package com.yryz.quanhu.dymaic.dto;

import java.io.Serializable;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/1/30
 * @description
 */
public class StarInfoDTO implements Serializable {
    private static final long serialVersionUID = -863086831050185478L;

    /**
     * 当前用户id
     */
    private Long userId;

    /**
     * 标签id
     */
    private Long tagId;

    /**
     * 标签类型 10-用户自选 11-运营设置达人推荐标签
     */
    private Byte tagType;

    /**
     * 页数
     */
    private Integer currentPage;
    /**
     * 页长度
     */
    private Integer pageSize;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Byte getTagType() {
        return tagType;
    }

    public void setTagType(Byte tagType) {
        this.tagType = tagType;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
