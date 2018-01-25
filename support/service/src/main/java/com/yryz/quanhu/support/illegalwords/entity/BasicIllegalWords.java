/*
 * BasicIllegalWords.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2017-09-12 Created
 */
package com.yryz.quanhu.support.illegalwords.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 违规词
 * 
 * @author danshiyu
 * @version 1.0 2017-09-12
 * @since 1.0
 */
@SuppressWarnings("serial")
public class BasicIllegalWords implements Serializable{
    private Long id;

    /**
     * 违规词
     */
    private String word;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 操作时间
     */
    private Date lastUpdateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word == null ? null : word.trim();
    }

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