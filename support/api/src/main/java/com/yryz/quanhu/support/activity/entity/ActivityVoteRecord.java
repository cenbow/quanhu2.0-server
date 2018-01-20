package com.yryz.quanhu.support.activity.entity;

import com.yryz.common.entity.GenericEntity;

/**
 * 
  * @ClassName: ActivityVoteRecord
  * @Description: 活动投票记录表实体类
  * @author jiangzhichao
  * @date 2018-01-20 14:24:45
  *
 */
public class ActivityVoteRecord extends GenericEntity{
	
	/**
	 * 活动id
	 */	 
    private  Long activityInfoId;
    
	/**
	 * 参与者id
	 */	 
    private  Long candidateId;
    
	/**
	 * 编号
	 */	 
    private  Integer voteNo;
    
	/**
	 * 是否第三方(10否 11是)
	 */	 
    private  Integer otherFlag;
    
	/**
	 * 10免费投票,11投票券投票
	 */	 
    private  Integer freeVoteFlag;
    

	public Long getActivityInfoId() {
		return this.activityInfoId;
	}
	
	public void setActivityInfoId(Long activityInfoId) {
		this.activityInfoId = activityInfoId;
	}
		
	public Long getCandidateId() {
		return this.candidateId;
	}
	
	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}
		
	public Integer getVoteNo() {
		return this.voteNo;
	}
	
	public void setVoteNo(Integer voteNo) {
		this.voteNo = voteNo;
	}
		
	public Integer getOtherFlag() {
		return this.otherFlag;
	}
	
	public void setOtherFlag(Integer otherFlag) {
		this.otherFlag = otherFlag;
	}
		
	public Integer getFreeVoteFlag() {
		return this.freeVoteFlag;
	}
	
	public void setFreeVoteFlag(Integer freeVoteFlag) {
		this.freeVoteFlag = freeVoteFlag;
	}
		
}