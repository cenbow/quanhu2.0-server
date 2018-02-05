package com.yryz.quanhu.resource.release.buyrecord.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/27 17:13
 * Created by lifan
 */
public class ReleaseBuyRecordDto implements Serializable {

    private static final long serialVersionUID = 4860732917232592549L;
    @ApiModelProperty(hidden = true)
    //用户ID
    private Long userId;
    //私圈ID
    private Long coterieId;
    //页码
    private Integer currentPage = 1;
    //每页条数
    private Integer pageSize = 20;

    /**  
    * @Fields beginDate : 开始日期
    */
    private String beginDate;

    /**  
    * @Fields endDate : 结束日期
    */
    private String endDate;
    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCoterieId() {
        return coterieId;
    }

    public void setCoterieId(Long coterieId) {
        this.coterieId = coterieId;
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

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
