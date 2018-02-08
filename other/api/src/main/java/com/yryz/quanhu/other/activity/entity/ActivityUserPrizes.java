package com.yryz.quanhu.other.activity.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.yryz.common.entity.GenericEntity;

import java.util.Date;

/**
 * 
  * @ClassName: ActivityUserPrizes
  * @Description: 用户与奖品关联表实体类
  * @author jiangzhichao
  * @date 2018-01-20 14:23:45
  *
 */
public class ActivityUserPrizes extends GenericEntity{
	
	/**
	 * 奖品名称
	 */	 
    private  String prizesName;
    
	/**
	 * 1投票卷 2自定义奖品
	 */	 
    private  Integer prizesType;
    
	/**
	 * 可用的投票卷数量
	 */	 
    private  Integer canNum;
    
	/**
	 * 手机号
	 */	 
    private  String phone;
    
	/**
	 * 唯一编码
	 */	 
    private  String onlyCode;
    
	/**
	 * 奖品数值
	 */	 
    private  Integer prizesNum;
    
	/**
	 * 数值单位
	 */	 
    private  String prizesUnit;
    
	/**
	 * 使用开始时间
	 */	 
    private  Date beginTime;
    
	/**
	 * 使用结束时间
	 */	 
    private  Date endTime;
    
	/**
	 * 使用说明
	 */	 
    private  String remark;
    
	/**
	 * 奖品使用情况 （1可使用 2已使用）
	 */	 
    private  Integer state;
    
	/**
	 * 活动id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
    private  Long activityInfoId;
    

	public String getPrizesName() {
		return this.prizesName;
	}
	
	public void setPrizesName(String prizesName) {
		this.prizesName = prizesName;
	}
		
	public Integer getPrizesType() {
		return this.prizesType;
	}
	
	public void setPrizesType(Integer prizesType) {
		this.prizesType = prizesType;
	}
		
	public Integer getCanNum() {
		return this.canNum;
	}
	
	public void setCanNum(Integer canNum) {
		this.canNum = canNum;
	}
		
	public String getPhone() {
		return this.phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
		
	public String getOnlyCode() {
		return this.onlyCode;
	}
	
	public void setOnlyCode(String onlyCode) {
		this.onlyCode = onlyCode;
	}
		
	public Integer getPrizesNum() {
		return this.prizesNum;
	}
	
	public void setPrizesNum(Integer prizesNum) {
		this.prizesNum = prizesNum;
	}
		
	public String getPrizesUnit() {
		return this.prizesUnit;
	}
	
	public void setPrizesUnit(String prizesUnit) {
		this.prizesUnit = prizesUnit;
	}
		
	public Date getBeginTime() {
		return this.beginTime;
	}
	
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
		
	public Date getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
		
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
		
	public Integer getState() {
		return this.state;
	}
	
	public void setState(Integer state) {
		this.state = state;
	}
		
	public Long getActivityInfoId() {
		return this.activityInfoId;
	}
	
	public void setActivityInfoId(Long activityInfoId) {
		this.activityInfoId = activityInfoId;
	}
		
}