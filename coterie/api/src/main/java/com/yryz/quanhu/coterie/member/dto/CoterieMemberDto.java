package com.yryz.quanhu.coterie.member.dto;

import java.io.Serializable;

/**
 * @author chengyunfei
 */
public class CoterieMemberDto implements Serializable{

	/**
	 * 私圈ID
	 */
	private Long coterieId;

	/**
	 * 成员ID
	 */
	private Long memberId;

	/**
	 * 设置禁言10/取消禁言20
	 */
	private Integer type;

	/**
	 * 加入原因或踢出原因
	 */
	private String reason;

	/**
	 * 页码 ，默认为1
	 */
	private Integer pageNum;
	
	/**
	 * 每页记录数，默认为10
	 */
	private Integer pageSize;

	public Long getCoterieId() {
		return coterieId;
	}

	public void setCoterieId(Long coterieId) {
		this.coterieId = coterieId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
