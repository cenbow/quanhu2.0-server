package com.yryz.quanhu.support.activity.entity;

import com.yryz.common.entity.GenericEntity;

/**
 * 
  * @ClassName: ActivityPayRecord
  * @Description: 报名支付记录表实体类
  * @author jiangzhichao
  * @date 2018-01-20 14:22:36
  *
 */
public class ActivityPayRecord extends GenericEntity{
	
	/**
	 * 活动id
	 */	 
    private  Long activityInfoId;
    
	/**
	 * 报名类型  11报名需支付货币 12报名需支付积分
	 */	 
    private  Integer signUpType;
    
	/**
	 * 货币/积分数量
	 */	 
    private  Integer amount;
    

	public Long getActivityInfoId() {
		return this.activityInfoId;
	}
	
	public void setActivityInfoId(Long activityInfoId) {
		this.activityInfoId = activityInfoId;
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
		
}