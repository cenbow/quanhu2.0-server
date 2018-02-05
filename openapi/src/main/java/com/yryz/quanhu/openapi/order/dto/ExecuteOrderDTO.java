/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018年1月23日
 * Id: ExecuteOrderDTO.java, 2018年1月23日 上午11:34:26 yehao
 */
package com.yryz.quanhu.openapi.order.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月23日 上午11:34:26
 * @Description 支付订单的DTO
 */
public class ExecuteOrderDTO implements Serializable {

    private static final long serialVersionUID = 8226876268508831154L;
    /**
     * 订单ID
     */
    @ApiModelProperty("订单ID")
    private String orderId;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", hidden = true)
    private String custId;

    /**
     * 支付密码
     */
    @ApiModelProperty("支付密码")
    private String payPassword;

    /**
     * @return the orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * @return the custId
     */
    public String getCustId() {
        return custId;
    }

    /**
     * @param custId the custId to set
     */
    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }
}
