package com.yryz.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 */
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = -1599925460585783120L;

    protected Date createDate;
    protected Date lastUpdateDate;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

}
