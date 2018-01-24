package com.yryz.quanhu.order.sdk.dto;

import com.yryz.quanhu.order.sdk.constant.OrderEnum;

import java.io.Serializable;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/23 14:06
 * Created by lifan
 * 创建订单入参，全部必填
 */
public class InputOrder implements Serializable {

    private static final long serialVersionUID = -5540978352769994542L;

    //订单枚举
    private OrderEnum orderEnum;
    //借方
    private Long fromId;
    //贷方
    private Long toId;
    //功能枚举
    private String moduleEnum;
    //私圈ID
    private Long coterieId;
    //资源ID
    private Long resourceId;
    //费用
    private Long cost;
    //创建人ID
    private Long createUserId;
    //扩展信息，需要回调返回的数据，非必填
    private String bizContent;

    public OrderEnum getOrderEnum() {
        return orderEnum;
    }

    public void setOrderEnum(OrderEnum orderEnum) {
        this.orderEnum = orderEnum;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public Long getToId() {
        return toId;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    public String getModuleEnum() {
        return moduleEnum;
    }

    public void setModuleEnum(String moduleEnum) {
        this.moduleEnum = moduleEnum;
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

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }
}
