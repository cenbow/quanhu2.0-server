/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月12日
 * Id: BasicIllegalWordsInfo.java, 2017年9月12日 上午10:53:41 Administrator
 */
package com.yryz.quanhu.message.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年9月12日 上午10:53:41
 * @Description 敏感词
 */
@SuppressWarnings("serial")
public class BasicIllegalWordsInfo implements Serializable {
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
