package com.yryz.quanhu.support.activity.entity;

import com.yryz.common.entity.GenericEntity;

import java.util.Date;

/**
 * 
  * @ClassName: ActivityPrizes
  * @Description: 投票活动关联奖品表实体类
  * @author jiangzhichao
  * @date 2018-01-20 14:23:02
  *
 */
public class ActivityPrizes extends GenericEntity{
	
	/**
	 * 活动id
	 */	 
    private  Long activityInfoId;
    
	/**
	 * 奖品名称
	 */	 
    private  String prizesName;
    
	/**
	 * 奖品类型（1投票卷 2自定义奖品）
	 */	 
    private  Integer prizesType;
    
	/**
	 * 发放张数
	 */	 
    private  Integer issueNum;
    
	/**
	 * 可使用次数
	 */	 
    private  Integer canNum;
    
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
	 * 排序
	 */	 
    private  Integer sort;
    

	public Long getActivityInfoId() {
		return this.activityInfoId;
	}
	
	public void setActivityInfoId(Long activityInfoId) {
		this.activityInfoId = activityInfoId;
	}
		
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
		
	public Integer getIssueNum() {
		return this.issueNum;
	}
	
	public void setIssueNum(Integer issueNum) {
		this.issueNum = issueNum;
	}
		
	public Integer getCanNum() {
		return this.canNum;
	}
	
	public void setCanNum(Integer canNum) {
		this.canNum = canNum;
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
		
	public Integer getSort() {
		return this.sort;
	}
	
	public void setSort(Integer sort) {
		this.sort = sort;
	}
		
}