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
     * 昵称
     */
    private String userNickName;

    /**
     * 用户手机号码
     */
    private String userPhone;
    
    
    
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
