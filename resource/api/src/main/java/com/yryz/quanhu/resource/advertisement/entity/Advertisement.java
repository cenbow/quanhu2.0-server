package com.yryz.quanhu.resource.advertisement.entity;

import com.yryz.common.entity.GenericEntity;

import java.util.Date;

/**
 * @author pengnian
 * @ClassName: Advertisement
 * @Description: 广告表实体类
 * @date 2018-01-20 14:41:26
 */
public class Advertisement extends GenericEntity {
    /**
     * 广告图片
     */
    private String imgUrl;


    /**
     * 广告名称
     */
    private String advName;


    /**
     * 广告链接
     */
    private String advUrl;


    /**
     * 跳转类型(外链：5001（app外页面）; 内链：5002，H5页，例：详情页，平台活动（app内http页）; 内链：5003，原生页面（协议页）。例：个人主页、账户)
     */
    private Integer skipType;


    /**
     * 广告类型(引导页广告10， 首页广告20)
     */
    private Integer advType;


    /**
     * 广告排序
     */
    private Integer advSort;


    /**
     * 状态(待开始10，进行中20，已下架30)
     */
    private Integer advStatus;


    /**
     * 是否默认广告(是10， 否20)
     */
    private Integer advDef;


    /**
     * 开始日期
     */
    private String startDate;


    /**
     * 开始时间
     */
    private String startTime;


    /**
     * 结束日期
     */
    private String endDate;


    /**
     * 结束时间
     */
    private String endTime;


    /**
     * 备注
     */
    private String note;

    /**
     * 枚举id
     */
    private String moduleEnum;

    public String getModuleEnum() {
        return moduleEnum;
    }

    public void setModuleEnum(String moduleEnum) {
        this.moduleEnum = moduleEnum;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAdvName() {
        return this.advName;
    }

    public void setAdvName(String advName) {
        this.advName = advName;
    }

    public String getAdvUrl() {
        return this.advUrl;
    }

    public void setAdvUrl(String advUrl) {
        this.advUrl = advUrl;
    }

    public Integer getSkipType() {
        return this.skipType;
    }

    public void setSkipType(Integer skipType) {
        this.skipType = skipType;
    }

    public Integer getAdvType() {
        return this.advType;
    }

    public void setAdvType(Integer advType) {
        this.advType = advType;
    }

    public Integer getAdvSort() {
        return this.advSort;
    }

    public void setAdvSort(Integer advSort) {
        this.advSort = advSort;
    }

    public Integer getAdvStatus() {
        return this.advStatus;
    }

    public void setAdvStatus(Integer advStatus) {
        this.advStatus = advStatus;
    }

    public Integer getAdvDef() {
        return this.advDef;
    }

    public void setAdvDef(Integer advDef) {
        this.advDef = advDef;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}