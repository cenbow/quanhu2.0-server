package com.yryz.quanhu.coterie.coterie.vo;

import java.io.Serializable;
import java.util.Date;

public class CoterieUpdateAdmin implements Serializable{
	private static final long serialVersionUID = -3326016770793173068L;

	/**
     * 私圈id
     */
    private Long coterieId;

    /**
	 * 状态：10待审核，11审批通过，12审批未通过
     */
    private Byte status;
    
    /**
     * 是否推荐，10否，11是
     */
    private Byte recommend;

	/**
	 * 上下架状态(10：上架,11：下架)
	 */
	private Byte shelveFlag;

	/**
	 * 审核用户ID
	 */
	private Long auditUserId;

	/**
	 *  审核备注
	 */
	private String auditRemark;


	private Date processTime;

	private Date LastUpdateDate;

	public Long getCoterieId() {
		return coterieId;
	}

	public void setCoterieId(Long coterieId) {
		this.coterieId = coterieId;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Byte getRecommend() {
		return recommend;
	}

	public void setRecommend(Byte recommend) {
		this.recommend = recommend;
	}

	public Byte getShelveFlag() {
		return shelveFlag;
	}

	public void setShelveFlag(Byte shelveFlag) {
		this.shelveFlag = shelveFlag;
	}

	public Long getAuditUserId() {
		return auditUserId;
	}

	public void setAuditUserId(Long auditUserId) {
		this.auditUserId = auditUserId;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Date getLastUpdateDate() {
		return LastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		LastUpdateDate = lastUpdateDate;
	}

	public Date getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}

	public String getAuditRemark() {
		return auditRemark;
	}

	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}
}
