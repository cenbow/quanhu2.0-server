package com.yryz.quanhu.support.activity.entity;

import com.yryz.common.entity.GenericEntity;

/**
 * 
  * @ClassName: ActivityEnrolConfig
  * @Description: 报名活动配置表实体类
  * @author jiangzhichao
  * @date 2018-01-20 14:21:28
  *
 */
public class ActivityEnrolConfig extends GenericEntity{
	
	/**
	 * 活动id
	 */	 
    private  Long activityInfoId;
    
	/**
	 * 报名类型(11报名需支付货币 12报名需支付积分 13免费报名)
	 */	 
    private  Integer signUpType;
    
	/**
	 * 货币/积分数量
	 */	 
    private  Integer amount;
    
	/**
	 * 报名上限
	 */	 
    private  Integer enrolUpper;
    
	/**
	 * 手机号是否必填(10否 11是)
	 */	 
    private  Integer phoneRequired;
    
	/**
	 * 配置数据
	 */	 
    private  String configSources;
    

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
		
	public Integer getEnrolUpper() {
		return this.enrolUpper;
	}
	
	public void setEnrolUpper(Integer enrolUpper) {
		this.enrolUpper = enrolUpper;
	}
		
	public Integer getPhoneRequired() {
		return this.phoneRequired;
	}
	
	public void setPhoneRequired(Integer phoneRequired) {
		this.phoneRequired = phoneRequired;
	}
		
	public String getConfigSources() {
		return this.configSources;
	}
	
	public void setConfigSources(String configSources) {
		this.configSources = configSources;
	}
		
}