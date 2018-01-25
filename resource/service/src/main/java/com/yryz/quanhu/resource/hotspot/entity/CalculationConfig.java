/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月25日
 * Id: CalculationConfig.java, 2018年1月25日 下午1:38:43 yehao
 */
package com.yryz.quanhu.resource.hotspot.entity;

import java.util.Date;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月25日 下午1:38:43
 * @Description 热度系数衰减配置
 */
public class CalculationConfig {
	
	/**
	 * 计算类型：衰减配置，1-1(人)/1-2(资源)；热度系数类型，2-1(人)，2-2(资源)
	 */
	private String calculationType;
	
	/**
	 * 衰减配置：周期(name)->衰减值(value)；热度类型：事件编码(name)->系数值(value)
	 */
	private String configName;
	
	/**
	 * 衰减配置：周期(name)->衰减值(value)；热度类型：事件编码(name)->系数值(value)
	 */
	private Integer configValue;
	
	/**
	 * 排序值
	 */
	private Integer sort;
	
	/**
	 * 创建时间
	 */
	private Long createTime;
	
	/**
	 * 更新时间
	 */
	private Long updateTime;
	

	/**
	 * 
	 * @exception 
	 */
	public CalculationConfig() {
		super();
	}

	/**
	 * @return the calculationType
	 */
	public String getCalculationType() {
		return calculationType;
	}

	/**
	 * @param calculationType the calculationType to set
	 */
	public void setCalculationType(String calculationType) {
		this.calculationType = calculationType;
	}

	/**
	 * @return the configName
	 */
	public String getConfigName() {
		return configName;
	}

	/**
	 * @param configName the configName to set
	 */
	public void setConfigName(String configName) {
		this.configName = configName;
	}

	/**
	 * @return the configValue
	 */
	public Integer getConfigValue() {
		return configValue;
	}

	/**
	 * @param configValue the configValue to set
	 */
	public void setConfigValue(Integer configValue) {
		this.configValue = configValue;
	}
	
	/**
	 * @return the sort
	 */
	public Integer getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	/**
	 * @return the createTime
	 */
	public Long getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the updateTime
	 */
	public Long getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	
}
