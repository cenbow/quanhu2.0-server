package com.yryz.quanhu.other.activity.vo;


import com.yryz.quanhu.other.activity.entity.ActivityInfo;

public class AdminActivityInfoSignUpVo extends ActivityInfo {

	private static final long serialVersionUID = -3604051152869161678L;
	/**
	 *活动状态 1未开始 2进行中 3已结束
	 */
	private Integer activityStatus;
	
	/**
	 *是否可编辑 1可编辑
	 */
	private Integer isEdit;
	/**
	 *活动分享数
	 */
	private Integer shareCount;
	/**
	 *货币总收益
	 */
	private Integer currencyTotalIncome;
	/**
	 * 积分总收益
	 */
	private Integer integralTotalIncome;
	
	
	/**
	 * @return the isEdit
	 */
	public Integer getIsEdit() {
		return isEdit;
	}
	/**
	 * @param isEdit the isEdit to set
	 */
	public void setIsEdit(Integer isEdit) {
		this.isEdit = isEdit;
	}
	public Integer getActivityStatus() {
		return activityStatus;
	}
	public void setActivityStatus(Integer activityStatus) {
		this.activityStatus = activityStatus;
	}
	public Integer getShareCount() {
		return shareCount;
	}
	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}
	public Integer getCurrencyTotalIncome() {
		return currencyTotalIncome;
	}
	public void setCurrencyTotalIncome(Integer currencyTotalIncome) {
		this.currencyTotalIncome = currencyTotalIncome;
	}
	public Integer getIntegralTotalIncome() {
		return integralTotalIncome;
	}
	public void setIntegralTotalIncome(Integer integralTotalIncome) {
		this.integralTotalIncome = integralTotalIncome;
	}
}
