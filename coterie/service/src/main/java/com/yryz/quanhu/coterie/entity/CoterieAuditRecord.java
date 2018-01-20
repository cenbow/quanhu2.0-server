/*
 * CoterieAuditRecord.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2017-08-31 Created
 */
package com.yryz.quanhu.coterie.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 私圈审批记录表
 * 
 * @author xxx
 * @version 1.0 2017-08-31
 * @since 1.0
 */
public class CoterieAuditRecord implements Serializable{
	private static final long serialVersionUID = -7933447710785294092L;

	private Long id;

    /**
     * 审核人ID
     */
    private String custId;

    /**
     * 审核人姓名
     */
    private String custName;

    /**
     * 审核说明
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;

    /**
     * 私圈ID
     */
    private String coterieId;

    /**
     * 审核结果:2审批未通过，3上架，4下架
     */
    private Byte status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId == null ? null : custId.trim();
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getCoterieId() {
        return coterieId;
    }

    public void setCoterieId(String coterieId) {
        this.coterieId = coterieId == null ? null : coterieId.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

	@Override
	public String toString() {
		return "CoterieAuditRecord [id=" + id + ", custId=" + custId + ", custName=" + custName + ", remark=" + remark
				+ ", createDate=" + createDate + ", lastUpdateTime=" + lastUpdateTime + ", coterieId=" + coterieId
				+ ", status=" + status + "]";
	}
    
}