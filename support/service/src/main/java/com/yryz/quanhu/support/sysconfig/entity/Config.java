/*
 * Config.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2016-10-29 Created
 */
package com.yryz.quanhu.support.sysconfig.entity;

import java.util.Date;

/**
 * 
 * 
 * @author danshiyu
 * @version 1.0 2016-10-29
 * @since 1.0
 */
public class Config {
    private Integer id;

    /**
     * 配置id
     */
    private String configId;

    /**
     * 配置类型
     */
    private String configType;

    /**
     * 配置名称
     */
    private String configName;

    /**
     * 配置值
     */
    private String configValue;

    /**
     * 执行人
     */
    private String operationName;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;
    /**
     * 配置说明
     */
    private String configDesc;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId == null ? null : configId.trim();
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType == null ? null : configType.trim();
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName == null ? null : configName.trim();
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue == null ? null : configValue.trim();
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName == null ? null : operationName.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

	public String getConfigDesc() {
		return configDesc;
	}

	public void setConfigDesc(String configDesc) {
		this.configDesc = configDesc;
	}

	/**
	 * 
	 * @exception 
	 */
	public Config() {
		super();
	}

	/**
	 * @param configId
	 * @param configType
	 * @param configName
	 * @param configValue
	 * @exception 
	 */
	public Config(String configId, String configType, String configName, String configValue) {
		super();
		this.configId = configId;
		this.configType = configType;
		this.configName = configName;
		this.configValue = configValue;
	}

	/**
	 * @param configId
	 * @param configType
	 * @param configName
	 * @param configValue
	 * @param operationName
	 * @param configDesc
	 * @exception 
	 */
	public Config(String configId, String configType, String configName, String configValue, String operationName,
			String configDesc) {
		super();
		this.configId = configId;
		this.configType = configType;
		this.configName = configName;
		this.configValue = configValue;
		this.operationName = operationName;
		this.configDesc = configDesc;
	}

	@Override
	public String toString() {
		return "Config [id=" + id + ", configId=" + configId + ", configType=" + configType + ", configName="
				+ configName + ", configValue=" + configValue + ", operationName=" + operationName + ", createDate="
				+ createDate + ", updateDate=" + updateDate + ", configDesc=" + configDesc + "]";
	}
    
    
}