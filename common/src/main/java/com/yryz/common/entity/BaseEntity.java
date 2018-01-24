package com.yryz.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 */
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = -1599925460585783120L;

    protected Long createUserId;
    protected Date createDate;
    protected Long lastUpdateUserId;
    protected Date lastUpdateDate;

    public Long getCreateUserId() {
        return createUserId;
    }
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public Long getLastUpdateUserId() {
        return lastUpdateUserId;
    }
    public void setLastUpdateUserId(Long lastUpdateUserId) {
        this.lastUpdateUserId = lastUpdateUserId;
    }
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

}
