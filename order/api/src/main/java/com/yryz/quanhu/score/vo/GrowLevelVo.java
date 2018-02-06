package com.yryz.quanhu.score.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 成长等级配置表VO
 * @author syc
 *
 */
public class GrowLevelVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4263775304304353941L;
	
	private Long id;
	
	private String grade;
	
	private String level;

	private Integer levelStart;
	
	private Integer levelEnd;
	
	private Date createTime;
	
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Integer getLevelStart() {
		return levelStart;
	}

	public void setLevelStart(Integer levelStart) {
		this.levelStart = levelStart;
	}

	public Integer getLevelEnd() {
		return levelEnd;
	}

	public void setLevelEnd(Integer levelEnd) {
		this.levelEnd = levelEnd;
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
