package com.yryz.quanhu.support.activity.vo;

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
    
    private Byte activityType;
    
    private Date createDate;

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

	public Byte getActivityType() {
		return activityType;
	}

	public void setActivityType(Byte activityType) {
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
