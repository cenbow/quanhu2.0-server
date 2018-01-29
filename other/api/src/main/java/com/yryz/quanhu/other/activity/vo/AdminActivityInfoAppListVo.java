package com.yryz.quanhu.other.activity.vo;


import java.io.Serializable;

public class AdminActivityInfoAppListVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2377895227010865943L;

	private Long id;

    private String title;

    private String coverPlan;
    
    private Byte activityType;

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
}
