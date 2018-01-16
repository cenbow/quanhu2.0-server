/*
 * Copyright (c) 2016-2018 Wuhan Yryz Network Company LTD.
 */
package com.yryz.component.rpc.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 分页对象
 * @author suyongcheng
 * @date 2017年10月27日15:08:03
 * @version 1.0
 */
public class PageList<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1433729327463356559L;

    /**页码*/
    private Integer currentPage = 1;
    /**每页大小*/
    private Integer pageSize = 10;
    /**实体数据集合*/
    private List<T> entities;
    /**总页码*/
    private Long count;

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public List<T> getEntities() {
        return entities;
    }

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "PageList{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", entities=" + entities +
                ", count=" + count +
                '}';
    }

}
