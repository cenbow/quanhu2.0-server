package com.yryz.quanhu.resource.advertisement.dto;


import java.io.Serializable;

/**
 * @author pengnian
 * @ClassName: AdvertisementDto
 * @Description: AdvertisementDto
 * @date 2018-01-20 14:41:26
 */
public class AdvertisementDto implements Serializable {

    /**
     * 广告类型(引导页广告10， 首页广告20)
     */
    private Integer advType;

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
     * 首页广告当前时间
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

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public String getNowDate() {
        return nowDate;
    }

    public void setNowDate(String nowDate) {
        this.nowDate = nowDate;
    }

    public Integer getAdvType() {
        return advType;
    }

    public void setAdvType(Integer advType) {
        this.advType = advType;
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
