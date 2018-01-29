package com.yryz.quanhu.support.config.entity;

import com.yryz.common.entity.GenericEntity;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/27 15:22
 * Created by huangxy
 */
public class BasicConfigEntity extends GenericEntity {

    private String appId;

    private Long parentKid;

    private String configName;

    private String configKey;

    private String configType;

    private String configValue;

    private String configDesc;

    private int configStatus;

    private int delFlag;

    private int version;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Long getParentKid() {
        return parentKid;
    }

    public void setParentKid(Long parentKid) {
        this.parentKid = parentKid;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public String getConfigDesc() {
        return configDesc;
    }

    public void setConfigDesc(String configDesc) {
        this.configDesc = configDesc;
    }

    public int getConfigStatus() {
        return configStatus;
    }

    public void setConfigStatus(int configStatus) {
        this.configStatus = configStatus;
    }
}
