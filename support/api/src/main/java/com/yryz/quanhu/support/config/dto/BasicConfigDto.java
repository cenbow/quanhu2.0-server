package com.yryz.quanhu.support.config.dto;

import com.yryz.quanhu.support.config.entity.BasicConfigEntity;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/27 15:28
 * Created by huangxy
 */
public class BasicConfigDto extends BasicConfigEntity {
    private int pageSize;
    private int pageNo;

    private String parentConfigName;


    public String getParentConfigName() {
        return parentConfigName;
    }

    public void setParentConfigName(String parentConfigName) {
        this.parentConfigName = parentConfigName;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
}
