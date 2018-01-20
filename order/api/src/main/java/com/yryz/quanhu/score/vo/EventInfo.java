package com.yryz.quanhu.score.vo;

import java.io.Serializable;

/**
 * @author xiepeng
 * @version 1.0
 * @date 2017年8月25日
 */
public class EventInfo implements Serializable {
    /**
     * 事件用户ID
     */
    private String custId;

    /**
     * 圈子ID
     */
    private String circleId;

    /**
     * 私圈ID
     */
    private String coterieId;

    /**
     * 资源ID
     */
    private String resourceId;

    /**
     * 资源作者ID
     */
    private String ownerId;

    /**
     * 事件编码
     */
    private String eventCode;

    /**
     * 事件计数
     */
    private Integer eventNum;

    private String createTime;
    
    /**
     * 事件产生的交易金额，如相关事件产生交易改变，则传入该值，根据金额计算积分/成长等，如未产生交易，该值可不传
     */
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
