package com.yryz.quanhu.resource.advertisement.dto;

import com.yryz.quanhu.resource.advertisement.entity.Advertisement;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/24 13:48
 * @Author: pn
 */
public class AdvertisementAdminDto extends Advertisement {

    /**
     * 页码
     */
    private Integer currentPage = 1;

    /**
     * 每页大小
     */
    private Integer pageSize = 10;

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
