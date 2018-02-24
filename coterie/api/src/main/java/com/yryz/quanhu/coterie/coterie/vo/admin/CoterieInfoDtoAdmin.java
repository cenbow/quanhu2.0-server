package com.yryz.quanhu.coterie.coterie.vo.admin;

import java.io.Serializable;
import java.util.Date;

public class CoterieInfoDtoAdmin implements Serializable{

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

	/**
	 * 审核时间
	 */
	private Date processTime;

	/**
	 * 最后更新时间
	 */
	private Date LastUpdateDate;

	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 私圈名称
	 */
	private String name;

	/**
	 * 圈主ID
	 */
	private String ownerId;

	/**
	 * 申请开时间   起 ,格式yyyy-MM-dd
	 */
	private String startTime;

	/**
	 * 申请时间 止,格式yyyy-MM-dd
	 */
	private String endTime;

	/**
	 * 页码 ，默认为1
	 */
	private Integer pageNum;

	/**
	 * 每页记录数，默认为10
	 */
	private Integer pageSize;

	/**
	 * 值：asc,desc  默认desc
	 */
	private String sortValue;

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

	public String getAuditRemark() {
		return auditRemark;
	}

	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

	public Date getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}

	public Date getLastUpdateDate() {
		return LastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		LastUpdateDate = lastUpdateDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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

	public String getSortValue() {
		return sortValue;
	}

	public void setSortValue(String sortValue) {
		this.sortValue = sortValue;
	}
}
