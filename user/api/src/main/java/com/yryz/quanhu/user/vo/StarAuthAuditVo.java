package com.yryz.quanhu.user.vo;

import java.io.Serializable;

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
	private Long userId;
	/**
	 * 审核状态 0:待审核 1:审核通过 2:审核失败 3:取消认证
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
	
	
}
