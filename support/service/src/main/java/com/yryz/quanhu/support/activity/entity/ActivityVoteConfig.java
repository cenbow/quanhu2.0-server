package com.yryz.quanhu.support.activity.entity;

import com.yryz.common.entity.GenericEntity;

import java.util.Date;

/**
 * 
  * @ClassName: ActivityVoteConfig
  * @Description: 投票活动配置表实体类
  * @author jiangzhichao
  * @date 2018-01-20 14:24:05
  *
 */
public class ActivityVoteConfig extends GenericEntity{
	
	/**
	 * 活动id
	 */	 
    private  Long activityInfoId;
    
	/**
	 * 是否可评论(10否 11是)
	 */	 
    private  Integer commentFlag;
    
	/**
	 * 配置数据
	 */	 
    private  String configSources;
    
	/**
	 * 无奖励文案配置
	 */	 
    private  String noRewardContent;
    
	/**
	 * 活动参加开始时间
	 */	 
    private  Date activityJoinBegin;
    
	/**
	 * 活动参加结束时间
	 */	 
    private  Date activityJoinEnd;
    
	/**
	 * 投票开始时间
	 */	 
    private  Date activityVoteBegin;
    
	/**
	 * 投票结束时间
	 */	 
    private  Date activityVoteEnd;
    
	/**
	 * app内投票规则   11,整个活动时间内的总投票数  12,活动期间每天可投
	 */	 
    private  Integer inAppVoteType;
    
	/**
	 * app内配置票数
	 */	 
    private  Integer inAppVoteConfigCount;
    
	/**
	 * app外投票规则   11,整个活动时间内的总投票数  12,活动期间每天可投
	 */	 
    private  Integer otherAppVoteType;
    
	/**
	 * app外配置票数
	 */	 
    private  Integer otherAppVoteConfigCount;
    
	/**
	 * 用户是否可参与(10否 11是)
	 */	 
    private  Integer userFlag;
    
	/**
	 * 是否有奖品(10否 11是)
	 */	 
    private  Integer prizesFlag;
    
	/**
	 * 参与人数上限
	 */	 
    private  Integer userNum;
    
	/**
	 * 参与获得积分值
	 */	 
    private  Integer amount;
    

	public Long getActivityInfoId() {
		return this.activityInfoId;
	}
	
	public void setActivityInfoId(Long activityInfoId) {
		this.activityInfoId = activityInfoId;
	}
		
	public Integer getCommentFlag() {
		return this.commentFlag;
	}
	
	public void setCommentFlag(Integer commentFlag) {
		this.commentFlag = commentFlag;
	}
		
	public String getConfigSources() {
		return this.configSources;
	}
	
	public void setConfigSources(String configSources) {
		this.configSources = configSources;
	}
		
	public String getNoRewardContent() {
		return this.noRewardContent;
	}
	
	public void setNoRewardContent(String noRewardContent) {
		this.noRewardContent = noRewardContent;
	}
		
	public Date getActivityJoinBegin() {
		return this.activityJoinBegin;
	}
	
	public void setActivityJoinBegin(Date activityJoinBegin) {
		this.activityJoinBegin = activityJoinBegin;
	}
		
	public Date getActivityJoinEnd() {
		return this.activityJoinEnd;
	}
	
	public void setActivityJoinEnd(Date activityJoinEnd) {
		this.activityJoinEnd = activityJoinEnd;
	}
		
	public Date getActivityVoteBegin() {
		return this.activityVoteBegin;
	}
	
	public void setActivityVoteBegin(Date activityVoteBegin) {
		this.activityVoteBegin = activityVoteBegin;
	}
		
	public Date getActivityVoteEnd() {
		return this.activityVoteEnd;
	}
	
	public void setActivityVoteEnd(Date activityVoteEnd) {
		this.activityVoteEnd = activityVoteEnd;
	}
		
	public Integer getInAppVoteType() {
		return this.inAppVoteType;
	}
	
	public void setInAppVoteType(Integer inAppVoteType) {
		this.inAppVoteType = inAppVoteType;
	}
		
	public Integer getInAppVoteConfigCount() {
		return this.inAppVoteConfigCount;
	}
	
	public void setInAppVoteConfigCount(Integer inAppVoteConfigCount) {
		this.inAppVoteConfigCount = inAppVoteConfigCount;
	}
		
	public Integer getOtherAppVoteType() {
		return this.otherAppVoteType;
	}
	
	public void setOtherAppVoteType(Integer otherAppVoteType) {
		this.otherAppVoteType = otherAppVoteType;
	}
		
	public Integer getOtherAppVoteConfigCount() {
		return this.otherAppVoteConfigCount;
	}
	
	public void setOtherAppVoteConfigCount(Integer otherAppVoteConfigCount) {
		this.otherAppVoteConfigCount = otherAppVoteConfigCount;
	}
		
	public Integer getUserFlag() {
		return this.userFlag;
	}
	
	public void setUserFlag(Integer userFlag) {
		this.userFlag = userFlag;
	}
		
	public Integer getPrizesFlag() {
		return this.prizesFlag;
	}
	
	public void setPrizesFlag(Integer prizesFlag) {
		this.prizesFlag = prizesFlag;
	}
		
	public Integer getUserNum() {
		return this.userNum;
	}
	
	public void setUserNum(Integer userNum) {
		this.userNum = userNum;
	}
		
	public Integer getAmount() {
		return this.amount;
	}
	
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
		
}