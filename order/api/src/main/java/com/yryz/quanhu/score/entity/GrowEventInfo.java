package com.yryz.quanhu.score.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 积分事件类型维护接口
 * @author lsn on 2017/8/28.
 */
public class GrowEventInfo implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7144295031442534096L;

	private Long id;

    private String eventCode;
    
    private String eventName;

    /**
     * 积分事件类型：分三大类：1：一次性触发  2：每次触发  3：条件日期循环触发
     */
    private String eventType;
    
    /**
     * 成长类型，0：事件触发者成长 1：事件触发的资源目标所有者成长 
     */
    private String growType;

    private Integer eventLoopUnit;

    private Integer eventLimit;

    private Integer eventGrow;

    private Date createTime;

    private Date updateTime;
    
    /**
     * 成长倍数，按交易计算成长时需要用倍数
     */
    private int amountPower;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Integer getEventLoopUnit() {
        return eventLoopUnit;
    }

    public void setEventLoopUnit(Integer eventLoopUnit) {
        this.eventLoopUnit = eventLoopUnit;
    }

    public Integer getEventLimit() {
        return eventLimit;
    }

    public void setEventLimit(Integer eventLimit) {
        this.eventLimit = eventLimit;
    }

    public Integer getEventGrow() {
		return eventGrow;
	}

	public void setEventGrow(Integer eventGrow) {
		this.eventGrow = eventGrow;
	}

	public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public String getGrowType() {
		return growType;
	}

	public void setGrowType(String growType) {
		this.growType = growType;
	}

	public int getAmountPower() {
		return amountPower;
	}

	public void setAmountPower(int amountPower) {
		this.amountPower = amountPower;
	}
    
}
