package com.yryz.quanhu.order.sdk.entity;


import com.yryz.common.entity.GenericEntity;

public class Order extends GenericEntity {

    private static final long serialVersionUID = 1749796312167167652L;

    private Long cost;

    private Integer payType;

    private Integer orderType;

    private String productId;

    private Integer productType;

    private String productDesc;

    private String orderDesc;

    private String remark;

    private Integer orderState;

    private String callback;

    private String bizContent;

    private String moduleEnum;
    //私圈ID
    private Long coterieId;
    //资源ID
    private Long resourceId;

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
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
}
