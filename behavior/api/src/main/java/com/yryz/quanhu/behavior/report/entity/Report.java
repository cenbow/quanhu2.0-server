package com.yryz.quanhu.behavior.report.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
    @JsonSerialize(using = ToStringSerializer.class)
    private long resourceId;
    //举报类型
    private String informTypeName;
    //违规描述
    private String informDesc;
    //处理状态(10待处理，11已处理)
    private byte informStatus;
    //举报人
    @JsonSerialize(using = ToStringSerializer.class)
    private long reportUserId;
    //被举报人
    @JsonSerialize(using = ToStringSerializer.class)
    private long beReportUserId;
    //处理时间
    private String disposeTime;

    public String getInformTypeName() {
        return informTypeName;
    }

    public void setInformTypeName(String informTypeName) {
        this.informTypeName = informTypeName;
    }

    public String getModuleEnum() {
        return moduleEnum;
    }

    public void setModuleEnum(String moduleEnum) {
        this.moduleEnum = moduleEnum;
    }

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }

    public String getInformDesc() {
        return informDesc;
    }

    public void setInformDesc(String informDesc) {
        this.informDesc = informDesc;
    }

    public byte getInformStatus() {
        return informStatus;
    }

    public void setInformStatus(byte informStatus) {
        this.informStatus = informStatus;
    }

    public long getReportUserId() {
        return reportUserId;
    }

    public void setReportUserId(long reportUserId) {
        this.reportUserId = reportUserId;
    }

    public long getBeReportUserId() {
        return beReportUserId;
    }

    public void setBeReportUserId(long beReportUserId) {
        this.beReportUserId = beReportUserId;
    }

    public String getDisposeTime() {
        return disposeTime;
    }

    public void setDisposeTime(String disposeTime) {
        this.disposeTime = disposeTime;
    }
}
