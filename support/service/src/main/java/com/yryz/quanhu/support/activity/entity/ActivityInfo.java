package com.yryz.quanhu.support.activity.entity;

import com.yryz.common.entity.GenericEntity;

import java.util.Date;

/**
 * 
  * @ClassName: ActivityInfo
  * @Description: 活动主表实体类
  * @author jiangzhichao
  * @date 2018-01-20 14:22:12
  *
 */
public class ActivityInfo extends GenericEntity{
	
	/**
	 * 标题
	 */	 
    private  String title;
    
	/**
	 * 活动介绍
	 */	 
    private  String content;
    
	/**
	 * 活动介绍元数据
	 */	 
    private  String contentSources;
    
	/**
	 * 封面图
	 */	 
    private  String coverPlan;
    
	/**
	 * 活动类型(11报名 12投票)
	 */	 
    private  Integer activityType;
    
	/**
	 * 上线时间
	 */	 
    private  Date onlineTime;
    
	/**
	 * 开始时间
	 */	 
    private  Date beginTime;
    
	/**
	 * 结束时间
	 */	 
    private  Date endTime;
    
	/**
	 * 是否推荐(10未推荐 11推荐)
	 */	 
    private  Integer recommend;
    
	/**
	 * 推荐时间
	 */	 
    private  Date recommendDate;
    
	/**
	 * 备注
	 */	 
    private  String remark;
    
	/**
	 * 排序
	 */	 
    private  Integer sort;
    
	/**
	 * 报名/参与数
	 */	 
    private  Integer joinCount;
    
	/**
	 * 是否上下线（10上线 11下线）
	 */	 
    private  Integer shelveFlag;
    
	/**
	 * 是否发布中奖公告(10否 11是)
	 */	 
    private  Integer prizesAnnouncementFlag;
    
	/**
	 * 中奖公告数据
	 */	 
    private  String prizesSources;
    
	/**
	 * 活动奖励介绍
	 */	 
    private  String introduceSources;
    
	/**
	 * 活动渠道码
	 */	 
    private  String activityChannelCode;
    
	/**
	 * 功能枚举
	 */	 
    private  String moduleEnum;
    

	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
		
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
		
	public String getContentSources() {
		return this.contentSources;
	}
	
	public void setContentSources(String contentSources) {
		this.contentSources = contentSources;
	}
		
	public String getCoverPlan() {
		return this.coverPlan;
	}
	
	public void setCoverPlan(String coverPlan) {
		this.coverPlan = coverPlan;
	}
		
	public Integer getActivityType() {
		return this.activityType;
	}
	
	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}
		
	public Date getOnlineTime() {
		return this.onlineTime;
	}
	
	public void setOnlineTime(Date onlineTime) {
		this.onlineTime = onlineTime;
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
		
	public Integer getRecommend() {
		return this.recommend;
	}
	
	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}
		
	public Date getRecommendDate() {
		return this.recommendDate;
	}
	
	public void setRecommendDate(Date recommendDate) {
		this.recommendDate = recommendDate;
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
		
	public Integer getJoinCount() {
		return this.joinCount;
	}
	
	public void setJoinCount(Integer joinCount) {
		this.joinCount = joinCount;
	}
		
	public Integer getShelveFlag() {
		return this.shelveFlag;
	}
	
	public void setShelveFlag(Integer shelveFlag) {
		this.shelveFlag = shelveFlag;
	}
		
	public Integer getPrizesAnnouncementFlag() {
		return this.prizesAnnouncementFlag;
	}
	
	public void setPrizesAnnouncementFlag(Integer prizesAnnouncementFlag) {
		this.prizesAnnouncementFlag = prizesAnnouncementFlag;
	}
		
	public String getPrizesSources() {
		return this.prizesSources;
	}
	
	public void setPrizesSources(String prizesSources) {
		this.prizesSources = prizesSources;
	}
		
	public String getIntroduceSources() {
		return this.introduceSources;
	}
	
	public void setIntroduceSources(String introduceSources) {
		this.introduceSources = introduceSources;
	}
		
	public String getActivityChannelCode() {
		return this.activityChannelCode;
	}
	
	public void setActivityChannelCode(String activityChannelCode) {
		this.activityChannelCode = activityChannelCode;
	}
		
	public String getModuleEnum() {
		return this.moduleEnum;
	}
	
	public void setModuleEnum(String moduleEnum) {
		this.moduleEnum = moduleEnum;
	}
		
}