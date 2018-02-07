package com.yryz.quanhu.user.dto;

import java.io.Serializable;

import com.yryz.quanhu.user.contants.Constants.ImgAuditStatus;

@SuppressWarnings("serial")
public class UserImgAuditFindDTO implements Serializable {
	
	private Integer pageNo = 1;
	private Integer pageSize = 10;
	private Long userId;
	/**
	 * 审核状态 {@link ImgAuditStatus}
	 */
	private Integer auditStatus;
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	public UserImgAuditFindDTO() {
		super();
	}
	public UserImgAuditFindDTO(Integer pageNo, Integer pageSize, Long userId, Integer auditStatus) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.userId = userId;
		this.auditStatus = auditStatus;
	}
}
