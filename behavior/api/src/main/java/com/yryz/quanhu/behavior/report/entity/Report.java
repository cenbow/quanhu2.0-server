package com.yryz.quanhu.behavior.report.entity;

import com.yryz.common.entity.GenericEntity;

/**
 * @Author:sun
 * @version:2.0
 * @Description:举报
 * @Date:Created in 11:29 2018/1/22
 */
public class Report extends GenericEntity {

    //资源类型
    private String moduleEnum;
    //资源ID
    private long resourceId;
    //举报类型
    private byte informType;
    //违规描述
    private String informDesc;
    //处理状态(10待处理，11已处理)
    private byte informStatus;
    //举报人
    private long reportUserId;
    //被举报人
    private long beReportUserId;
    //处理时间
    private String disposeTime;

    public String getModuleEnum() {
        return moduleEnum;
    }

    public Report setModuleEnum(String moduleEnum) {
        this.moduleEnum = moduleEnum;
        return this;
    }

    public long getResourceId() {
        return resourceId;
    }

    public Report setResourceId(long resourceId) {
        this.resourceId = resourceId;
        return this;
    }

    public byte getInformType() {
        return informType;
    }

    public Report setInformType(byte informType) {
        this.informType = informType;
        return this;
    }

    public String getInformDesc() {
        return informDesc;
    }

    public Report setInformDesc(String informDesc) {
        this.informDesc = informDesc;
        return this;
    }

    public byte getInformStatus() {
        return informStatus;
    }

    public Report setInformStatus(byte informStatus) {
        this.informStatus = informStatus;
        return this;
    }

    public long getReportUserId() {
        return reportUserId;
    }

    public Report setReportUserId(long reportUserId) {
        this.reportUserId = reportUserId;
        return this;
    }

    public long getBeReportUserId() {
        return beReportUserId;
    }

    public Report setBeReportUserId(long beReportUserId) {
        this.beReportUserId = beReportUserId;
        return this;
    }

    public String getDisposeTime() {
        return disposeTime;
    }

    public Report setDisposeTime(String disposeTime) {
        this.disposeTime = disposeTime;
        return this;
    }
}
