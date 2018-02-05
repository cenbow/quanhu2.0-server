package com.yryz.quanhu.order.vo;

import java.io.Serializable;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/2/5 15:44
 * Created by lifan
 */
public class WithdrawCashDto implements Serializable {

    private static final long serialVersionUID = -6937416626426458045L;

    /**
     * 页码
     */
    private Integer currentPage = 1;
    /**
     * 每页大小
     */
    private Integer pageSize = 10;
    /**
     * 订单状态 0 创建 1成功 2失败
     */
    private Integer orderState;

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

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }
}
