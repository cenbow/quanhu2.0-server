package com.yryz.quanhu.dymaic.dto;

import com.yryz.common.response.PageList;

import java.io.Serializable;

/**
 * 动态查询
 * @author xiepeng
 * @version 1.0
 * @data 2018/2/2 0002 25
 */
public class QueryDymaicDTO extends PageList implements Serializable{

    /**
     * 删除状态
     */
    private Integer delFlag;

    /**
     * 发布起始时间
     */
    private String startDate;

    /**
     * 发布起始时间
     */
    private String endDate;

    /**
     * 关键字
     */
    private String key;


    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
