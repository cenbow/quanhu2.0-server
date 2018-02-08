package com.yryz.quanhu.score.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import io.swagger.annotations.ApiModelProperty;

/**
 * 用户事件账户表，描述用户总积分值，成长值/等级 数据结构
 * @author syc
 *
 */
public class EventAcount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3825623201525351620L;
	
	/** id */
    @ApiModelProperty(value = "id")
    @JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	
    /** 用户id */
    @ApiModelProperty(value = "用户id")
	private String userId;
	
    /** 总积分 */
    @ApiModelProperty(value = "总积分")
	private Long score;
	
    /** 成长值 */
    @ApiModelProperty(value = "成长值")
	private Long grow = 0L;
	
    /** 成长级别 */
    @ApiModelProperty(value = "成长级别")
	private String growLevel;
	
    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
	private Date createTime;
	
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
	private Date updateTime;

	public EventAcount(){}
	
	public EventAcount(String userId){
		this.userId = userId;
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

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score == null ? 0L : score;
	}

	public Long getGrow() {
		return grow;
	}

	public void setGrow(Long grow) {
		this.grow = grow;
	}
	
	public String getGrowLevel() {
		return growLevel;
	}

	public void setGrowLevel(String growLevel) {
		this.growLevel = growLevel;
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
	
}
