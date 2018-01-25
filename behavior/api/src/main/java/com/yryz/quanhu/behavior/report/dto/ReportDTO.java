package com.yryz.quanhu.behavior.report.dto;

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

    public String getModuleEnum() {
        return moduleEnum;
    }

    public void setModuleEnum(String moduleEnum) {
        this.moduleEnum = moduleEnum;
    }

    public byte getInformType() {
        return informType;
    }

    public void setInformType(byte informType) {
        this.informType = informType;
    }

    public byte getInformStatus() {
        return informStatus;
    }

    public void setInformStatus(byte informStatus) {
        this.informStatus = informStatus;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
