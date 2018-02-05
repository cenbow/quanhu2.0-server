package com.yryz.quanhu.other.activity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.util.Date;

public class ActivityInfoAppListVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2377895227010865943L;

	private Long id;

	@JsonSerialize(using = ToStringSerializer.class)
	private Long kid;

    private String title;

    private String coverPlan;
    
    private Integer activityType;
    
    private Date createDate;

	private Integer listType;
	/**
	 * 功能枚举
	 */
	private  String moduleEnum;

	public String getModuleEnum() {
		return moduleEnum;
	}

	public void setModuleEnum(String moduleEnum) {
		this.moduleEnum = moduleEnum;
	}

	public Integer getListType() {
		return listType;
	}

	public void setListType(Integer listType) {
		this.listType = listType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCoverPlan() {
		return coverPlan;
	}

	public void setCoverPlan(String coverPlan) {
		this.coverPlan = coverPlan;
	}

	public Integer getActivityType() {
		return activityType;
	}

	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getKid() {
		return kid;
	}

	public void setKid(Long kid) {
		this.kid = kid;
	}
}
