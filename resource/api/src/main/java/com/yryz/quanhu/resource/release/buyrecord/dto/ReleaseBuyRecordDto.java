package com.yryz.quanhu.resource.release.buyrecord.dto;

import java.io.Serializable;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/27 17:13
 * Created by lifan
 */
public class ReleaseBuyRecordDto implements Serializable {

    private static final long serialVersionUID = 4860732917232592549L;
    //用户ID
    private Long userId;
    //私圈ID
    private Long coterieId;
    //页码
    private Integer pageNo;
    //每页条数
    private Integer pageSize;

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

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
