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
