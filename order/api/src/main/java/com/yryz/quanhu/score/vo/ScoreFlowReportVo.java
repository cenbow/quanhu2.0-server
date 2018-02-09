package com.yryz.quanhu.score.vo;

import java.io.Serializable;




/**
 * @author syc on 2018/1/22.
 */
public class ScoreFlowReportVo implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3577121435443411098L;
	
	
	private Long id;

	private int consumeFlag;
	
    private String userId;

    private String eventCode;
    
    private String eventName;

    private Integer newScore;

    private Long allScore;

    private String createTime;

    private String updateTime;

    /**
     * 积分事件类型：分三大类：1：一次性触发  2：每次触发  3：条件日期循环触发 
     */
    private String eventType;

	private Integer eventLoopUnit;

    private Integer eventLimit;

    private Integer eventScore;

	/**
     * 昵称
     */
    private String userNickName;

    /**
     * 用户手机号码
     */
    private String userPhone;
    
    /**
     * 积分倍数，按交易计算积分时需要用倍数
     */
    private int amountPower;
    
    

    public int getAmountPower() {
		return amountPower;
	}

	public void setAmountPower(int amountPower) {
		this.amountPower = amountPower;
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

	public Integer getEventScore() {
		return eventScore;
	}

	public void setEventScore(Integer eventScore) {
		this.eventScore = eventScore;
	}

    

    public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public Integer getNewScore() {
        return newScore;
    }

    public void setNewScore(Integer newScore) {
        this.newScore = newScore;
    }

    public Long getAllScore() {
        return allScore;
    }

    public void setAllScore(Long allScore) {
        this.allScore = allScore;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

	public int getConsumeFlag() {
		return consumeFlag;
	}

	public void setConsumeFlag(int consumeFlag) {
		this.consumeFlag = consumeFlag;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
}
