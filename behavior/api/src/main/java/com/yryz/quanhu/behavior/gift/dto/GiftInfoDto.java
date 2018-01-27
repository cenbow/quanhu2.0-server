package com.yryz.quanhu.behavior.gift.dto;

import com.yryz.quanhu.behavior.gift.entity.GiftInfo;

/**
* @author wangheng
* @date 2018年1月26日 下午1:39:46
*/
public class GiftInfoDto extends GiftInfo {

    private static final long serialVersionUID = 1L;

    /**页码*/
    private Integer currentPage = 1;
    /**每页大小*/
    private Integer pageSize = 30;

    private Long[] kids;

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

    public Long[] getKids() {
        return kids;
    }

    public void setKids(Long[] kids) {
        this.kids = kids;
    }
}
