package com.yryz.quanhu.user.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 违规记录
 * 
 * @author
 *
 */
@SuppressWarnings("serial")
public class ViolationInfo implements Serializable {

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 违规范围
	 */
	private String scopeDesc;

	/**
	 * 违规截图
	 */
	private String picUrl;

	/**
	 * 违规原因
	 */
	private String reason;

	/**
	 * 违规提示消息
	 */
	private String message;

	/**
	 * 违规类型 {@link #ViolatType}
	 */
	private Byte type;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 创建人
	 */
	private String operational;

	/**
	 * 禁言时间
	 */
	private Date freezeTime;
	
	private String appId;
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	public String getScopeDesc() {
		return scopeDesc;
	}

	public void setScopeDesc(String scopeDesc) {
		this.scopeDesc = scopeDesc == null ? null : scopeDesc.trim();
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl == null ? null : picUrl.trim();
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason == null ? null : reason.trim();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message == null ? null : message.trim();
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOperational() {
		return operational;
	}

	public void setOperational(String operational) {
		this.operational = operational == null ? null : operational.trim();
	}

	public Date getFreezeTime() {
		return freezeTime;
	}

	public void setFreezeTime(Date freezeTime) {
		this.freezeTime = freezeTime;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Override
	public String toString() {
		return "ViolationInfo [userId=" + userId + ", scopeDesc=" + scopeDesc + ", picUrl=" + picUrl + ", reason="
				+ reason + ", message=" + message + ", type=" + type + ", createTime=" + createTime + ", operational="
				+ operational + ", freezeTime=" + freezeTime + "]";
	}



}
