package com.yryz.quanhu.score.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import io.swagger.annotations.ApiModelProperty;

/**
 * 签到状态记录实体类
 * @author syc
 *
 */
public class EventSign implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5784041224148862394L;

	/** id */
    @ApiModelProperty(value = "id")
    @JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	
	/** 用户ID */
    @ApiModelProperty(value = "用户ID")
	private String userId;
	
	/** 事件编码 */
    @ApiModelProperty(value = "事件编码")
	private String eventCode;
	
	/** 连续签到天数 */
    @ApiModelProperty(value = "连续签到天数")
	private int signCount;
	
	/** 最近一次签到时间 */
    @ApiModelProperty(value = "最近一次签到时间")
	private Date lastSignTime;
	
	/** 记录创建时间 */
    @ApiModelProperty(value = "记录创建时间")
	private Date createTime;
	
	/** 记录更新时间 */
    @ApiModelProperty(value = "记录更新时间")
	private Date updateTime;
	
	/** 签到状态 */
    @ApiModelProperty(value = "签到状态")
	private boolean signFlag;
	
	public EventSign(){
		
	}
	
	public EventSign(String userId , String eventCode){
		this.userId = userId;
		this.eventCode = eventCode;
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

	public int getSignCount() {
		return signCount;
	}

	public void setSignCount(int signCount) {
		this.signCount = signCount;
	}

	public Date getLastSignTime() {
		return lastSignTime;
	}

	public void setLastSignTime(Date lastSignTime) {
		this.lastSignTime = lastSignTime;
	}

	public boolean isSignFlag() {
		return signFlag;
	}

	public void setSignFlag(boolean signFlag) {
		this.signFlag = signFlag;
	}
}
