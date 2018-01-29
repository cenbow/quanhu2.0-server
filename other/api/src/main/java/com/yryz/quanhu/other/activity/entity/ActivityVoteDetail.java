package com.yryz.quanhu.other.activity.entity;

import com.yryz.common.entity.GenericEntity;

/**
 * 
  * @ClassName: ActivityVoteDetail
  * @Description: 投票活动参与表实体类
  * @author jiangzhichao
  * @date 2018-01-20 14:24:28
  *
 */
public class ActivityVoteDetail extends GenericEntity{
	
	/**
	 * 活动id
	 */
	private  Long activityInfoId;

	/**
	 * 编号
	 */
	private  Integer voteNo;

	/**
	 * 参与获得积分
	 */
	private  Integer obtainIntegral;

	/**
	 * 文本
	 */
	private  String content;

	/**
	 * 封面图
	 */
	private  String coverPlan;

	/**
	 * 图片url
	 */
	private  String imgUrl;

	/**
	 * 视频url
	 */
	private  String videoUrl;

	/**
	 * 视频首帧图url
	 */
	private  String videoThumbnailUrl;

	/**
	 * 投票数
	 */
	private  Integer voteCount;
    
	/**
	 * 后台增加的票数
	 */	 
    private  Integer addVote;
    
	/**
	 * 功能id
	 */	 
    private  String moduleEnum;
    
	/**
	 * 是否上下线（10上线 11下线）
	 */	 
    private  Integer shelveFlag;
    
	/**
	 * 总访问量
	 */	 
    private  Integer amountOfAccess;
    
	/**
	 * 文本1
	 */	 
    private  String content1;
    
	/**
	 * 文本2
	 */	 
    private  String content2;
    

	public Long getActivityInfoId() {
		return this.activityInfoId;
	}
	
	public void setActivityInfoId(Long activityInfoId) {
		this.activityInfoId = activityInfoId;
	}
		
	public Integer getVoteNo() {
		return this.voteNo;
	}
	
	public void setVoteNo(Integer voteNo) {
		this.voteNo = voteNo;
	}
		
	public Integer getObtainIntegral() {
		return this.obtainIntegral;
	}
	
	public void setObtainIntegral(Integer obtainIntegral) {
		this.obtainIntegral = obtainIntegral;
	}
		
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
		
	public String getCoverPlan() {
		return this.coverPlan;
	}
	
	public void setCoverPlan(String coverPlan) {
		this.coverPlan = coverPlan;
	}
		
	public String getImgUrl() {
		return this.imgUrl;
	}
	
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
		
	public String getVideoUrl() {
		return this.videoUrl;
	}
	
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
		
	public String getVideoThumbnailUrl() {
		return this.videoThumbnailUrl;
	}
	
	public void setVideoThumbnailUrl(String videoThumbnailUrl) {
		this.videoThumbnailUrl = videoThumbnailUrl;
	}
		
	public Integer getVoteCount() {
		return this.voteCount;
	}
	
	public void setVoteCount(Integer voteCount) {
		this.voteCount = voteCount;
	}
		
	public Integer getAddVote() {
		return this.addVote;
	}
	
	public void setAddVote(Integer addVote) {
		this.addVote = addVote;
	}
		
	public String getModuleEnum() {
		return this.moduleEnum;
	}
	
	public void setModuleEnum(String moduleEnum) {
		this.moduleEnum = moduleEnum;
	}
		
	public Integer getShelveFlag() {
		return this.shelveFlag;
	}
	
	public void setShelveFlag(Integer shelveFlag) {
		this.shelveFlag = shelveFlag;
	}
		
	public Integer getAmountOfAccess() {
		return this.amountOfAccess;
	}
	
	public void setAmountOfAccess(Integer amountOfAccess) {
		this.amountOfAccess = amountOfAccess;
	}
		
	public String getContent1() {
		return this.content1;
	}
	
	public void setContent1(String content1) {
		this.content1 = content1;
	}
		
	public String getContent2() {
		return this.content2;
	}
	
	public void setContent2(String content2) {
		this.content2 = content2;
	}
		
}