package com.yryz.quanhu.score.vo;

/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月16日
 * Id: ResourceVo.java, 2018年1月16日 下午2:13:23 syc
 */

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiepeng
 * @version 1.0
 * @date 2017年8月25日
 */
public class EventInfo implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8234067721074858571L;
	
	/** 事件用户ID */
    @ApiModelProperty(value = "事件用户ID")
    private String custId;

    /**
     * 圈子ID
     */
	@ApiModelProperty(value="圈子ID")
    private String circleId;

    /**
     * 私圈ID
     */
	@ApiModelProperty(value="私圈ID")
    private String coterieId;

    /**
     * 资源ID
     */
	@ApiModelProperty(value="资源ID")
    private String resourceId;

    /**
     * 资源作者ID
     */
	@ApiModelProperty(value="资源作者ID")
    private String ownerId;

    /**
     * 事件编码
     */
	@ApiModelProperty(value="事件编码")
    private String eventCode;

    /**
     * 事件计数
     */
	@ApiModelProperty(value="事件计数")
    private Integer eventNum;
	
	
	
	@ApiModelProperty(value="创建时间")
    private String createTime;
    
    /**
     * 事件产生的交易金额，如相关事件产生交易改变，则传入该值，根据金额计算积分/成长等，如未产生交易，该值可不传
     */
	@ApiModelProperty(value="计数")
    private Double amount;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCircleId() {
        return circleId;
    }

    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }

    public String getCoterieId() {
        return coterieId;
    }

    public void setCoterieId(String coterieId) {
        this.coterieId = coterieId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public Integer getEventNum() {
        return eventNum;
    }

    public void setEventNum(Integer eventNum) {
        this.eventNum = eventNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
