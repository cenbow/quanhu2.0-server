package com.yryz.quanhu.resource.release.buyrecord.entity;

import com.yryz.common.entity.GenericEntity;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/27 17:13
 * Created by lifan
 */
public class ReleaseBuyRecord extends GenericEntity {
    private static final long serialVersionUID = 1727971729909640194L;

    //订单ID
    private Long orderId;
    //私圈ID
    private Long coterieId;
    //资源ID
    private Long resourceId;
    //支付的金额
    private Long amount;
    //支付类型（10:扣费，11加费或退款）
    private Integer payType;
    //购买类型'10资源购买付费
    private Integer buyType;
    //支付备注
    private String remark;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCoterieId() {
        return coterieId;
    }

    public void setCoterieId(Long coterieId) {
        this.coterieId = coterieId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getBuyType() {
        return buyType;
    }

    public void setBuyType(Integer buyType) {
        this.buyType = buyType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
