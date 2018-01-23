package com.yryz.quanhu.support.activity.entity;

import com.yryz.common.entity.GenericEntity;

/**
 * 
  * @ClassName: ActivityRecord
  * @Description: 报名记录表实体类
  * @author jiangzhichao
  * @date 2018-01-20 14:23:22
  *
 */
public class ActivityRecord extends GenericEntity{
	
	/**
	 * 活动id
	 */	 
    private  Long activityInfoId;
    
	/**
	 * 手机号
	 */	 
    private  String phone;
    
	/**
	 * 报名类型（11报名需支付货币 12报名需支付积分 13免费报名）
	 */	 
    private  Integer signUpType;
    
	/**
	 * 货币/积分数量
	 */	 
    private  Integer amount;
    
	/**
	 * 报名数据
	 */	 
    private  String enrolSources;

	/**
	 * 是否上下线（10上线 11下线）
	 */
	private  Integer shelveFlag;


	public Long getActivityInfoId() {
		return this.activityInfoId;
	}
	
	public void setActivityInfoId(Long activityInfoId) {
		this.activityInfoId = activityInfoId;
	}
		
	public String getPhone() {
		return this.phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
		
	public Integer getSignUpType() {
		return this.signUpType;
	}
	
	public void setSignUpType(Integer signUpType) {
		this.signUpType = signUpType;
	}
		
	public Integer getAmount() {
		return this.amount;
	}
	
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
		
	public String getEnrolSources() {
		return this.enrolSources;
	}
	
	public void setEnrolSources(String enrolSources) {
		this.enrolSources = enrolSources;
	}

    public Integer getShelveFlag() {
        return shelveFlag;
    }

    public void setShelveFlag(Integer shelveFlag) {
        this.shelveFlag = shelveFlag;
    }
}