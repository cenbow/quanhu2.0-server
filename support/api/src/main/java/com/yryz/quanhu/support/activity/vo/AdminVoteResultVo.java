package com.yryz.quanhu.support.activity.vo;


import com.yryz.quanhu.support.activity.entity.ActivityPrizes;

public class AdminVoteResultVo {
	
	private Integer otherFlag;
	
	private int userRollFlag;
	
	private ActivityPrizes prizes;

	public Integer getOtherFlag() {
		return otherFlag;
	}

	public void setOtherFlag(int otherFlag) {
		this.otherFlag = otherFlag;
	}

	public int getUserRollFlag() {
		return userRollFlag;
	}

	public void setUserRollFlag(Integer userRollFlag) {
		this.userRollFlag = userRollFlag;
	}

	public ActivityPrizes getPrizes() {
		return prizes;
	}

	public void setPrizes(ActivityPrizes prizes) {
		this.prizes = prizes;
	}
	
}
