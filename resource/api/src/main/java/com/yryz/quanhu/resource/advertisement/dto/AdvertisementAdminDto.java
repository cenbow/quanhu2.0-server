package com.yryz.quanhu.resource.advertisement.dto;

import com.yryz.quanhu.resource.advertisement.entity.Advertisement;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/24 13:48
 * @Author: pn
 */
public class AdvertisementAdminDto extends Advertisement {

    /**
     * 页码
     */
    private Integer currentPage = 1;

    /**
     * 每页大小
     */
    private Integer pageSize = 10;

    /**
     * 当前时间(年月日)
     */
    private String nowDate;

    /**
     * 当前时间(时分秒)
     */
    private String nowTime;

    /**
     * 冲突检查开始日期
     */
    private String checkStartDate;

    /**
     * 冲突检查结束日期
     */
    private String checkEndDate;

    /**
     * 冲突检查开始时间
     */
    private String checkStartTime;

    /**
     * 冲突检查结束时间
     */
    private String checkEndTime;

    /**
     * 当前日期时间
     */
    private String nowDateTime;

    public String getNowDateTime() {
        return nowDateTime;
    }

    public void setNowDateTime(String nowDateTime) {
        this.nowDateTime = nowDateTime;
    }

    public String getCheckStartDate() {
        return checkStartDate;
    }

    public void setCheckStartDate(String checkStartDate) {
        this.checkStartDate = checkStartDate;
    }

    public String getCheckEndDate() {
        return checkEndDate;
    }

    public void setCheckEndDate(String checkEndDate) {
        this.checkEndDate = checkEndDate;
    }

    public String getCheckStartTime() {
        return checkStartTime;
    }

    public void setCheckStartTime(String checkStartTime) {
        this.checkStartTime = checkStartTime;
    }

    public String getCheckEndTime() {
        return checkEndTime;
    }

    public void setCheckEndTime(String checkEndTime) {
        this.checkEndTime = checkEndTime;
    }

    public String getNowDate() {
        return nowDate;
    }

    public void setNowDate(String nowDate) {
        this.nowDate = nowDate;
    }

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
