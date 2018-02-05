package com.yryz.quanhu.user.vo;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * 达人审核参数
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class StarAuthAuditVo implements Serializable{
	/**
	 * 用户id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long userId;
	/**
	 * 审核状态 {@link #UserStarContants.StarAuditStatus}
	 */
	private Byte auditStatus;
	
	/**
	 * 审核失败原因
	 */
	private String reason;
	/**
	 * 操作人
	 */
	private String operational;
	/**
	 * 应用id
	 */
	private String appId;
	public String getOperational() {
		return operational;
	}

	public void setOperational(String operational) {
		this.operational = operational;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Byte getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Byte auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	
}
