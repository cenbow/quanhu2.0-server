package com.yryz.quanhu.behavior.dto;

import com.yryz.common.response.PageList;

import java.io.Serializable;

/**
 * @Author:sun
 * @version:2.0
 * @Description:举报
 * @Date:Created in 16:13 2018/1/20
 */
public class ReportDTO extends PageList implements Serializable {
    private static final long serialVersionUID = 6823220474426141657L;
    private String moduleEnum;
    private byte informType;
    private byte informStatus;
    private String startTime;
    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public ReportDTO setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getModuleEnum() {
        return moduleEnum;
    }

    public ReportDTO setModuleEnum(String moduleEnum) {
        this.moduleEnum = moduleEnum;
        return this;
    }

    public byte getInformType() {
        return informType;
    }

    public ReportDTO setInformType(byte informType) {
        this.informType = informType;
        return this;
    }

    public byte getInformStatus() {
        return informStatus;
    }

    public ReportDTO setInformStatus(byte informStatus) {
        this.informStatus = informStatus;
        return this;
    }

    public String getEndTime() {
        return endTime;
    }

    public ReportDTO setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }
}
