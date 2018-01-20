package com.yryz.quanhu.score.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户事件账户表，描述用户总积分值，成长值/等级 数据结构
 * @author lsn
 *
 */
public class EventAcount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3825623201525351620L;
	
	private Long id;
	
	private String custId;
	
	private Long score;
	
	private Long grow = 0L;
	
	private String growLevel;
	
	private Date createTime;
	
	private Date updateTime;

	public EventAcount(){}
	
	public EventAcount(String custId){
		this.custId = custId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
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
