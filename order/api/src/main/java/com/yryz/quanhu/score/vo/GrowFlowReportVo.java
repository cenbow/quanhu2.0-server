package com.yryz.quanhu.score.vo;

import java.io.Serializable;




/**
 * @author syc on 2017/8/28.
 */
public class GrowFlowReportVo implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3577121435443411098L;

	
	private Long id;

    private String userId;

    private String eventCode;
    
    private String eventName;

    private Integer newGrow;

    private Long allGrow;

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

    public Integer getNewGrow() {
		return newGrow;
	}

	public void setNewGrow(Integer newGrow) {
		this.newGrow = newGrow;
	}

	public Long getAllGrow() {
		return allGrow;
	}

	public void setAllGrow(Long allGrow) {
		this.allGrow = allGrow;
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

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
    
}
