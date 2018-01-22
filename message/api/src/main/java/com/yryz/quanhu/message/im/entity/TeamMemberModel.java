package com.yryz.quanhu.message.im.entity;

import java.io.Serializable;

/**
 * 群成员数据实体类
 * 
 * @author xiepeng
 *
 */
@SuppressWarnings("serial")
public class TeamMemberModel implements Serializable{
	
	/** 成员用户id */
	private String custId;

	/** 群id */
	private String tid;

	/** 所属圈子id */
	private String appId;

	/** 入群时间 */
	private String createTime;
	/**
	 * 禁言 1-禁言 0-解禁
	 */
	private Byte mute;
	/**
	 * 管理员 0-否 1-是
	 */
	private Byte isManager;
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Byte getMute() {
		return mute;
	}

	public void setMute(Byte mute) {
		this.mute = mute;
	}

	public Byte getIsManager() {
		return isManager;
	}

	public void setIsManager(Byte isManager) {
		this.isManager = isManager;
	}

	/**
	 * 
	 * @exception 
	 */
	public TeamMemberModel() {
		super();
	}

	/**
	 * @param custId
	 * @param tid
	 * @param appId
	 * @exception 
	 */
	public TeamMemberModel(String custId, String tid, String appId) {
		super();
		this.custId = custId;
		this.tid = tid;
		this.appId = appId;
	}

	@Override
	public String toString() {
		return "TeamMemberModel [custId=" + custId + ", tid=" + tid + ", appId=" + appId + ", createTime=" + createTime
				+ ", mute=" + mute + ", isManager=" + isManager + "]";
	}

}
