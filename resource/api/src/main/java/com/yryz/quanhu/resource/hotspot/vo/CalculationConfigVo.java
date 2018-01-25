/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月25日
 * Id: CalculationConfigVo.java, 2018年1月25日 下午5:28:32 yehao
 */
package com.yryz.quanhu.resource.hotspot.vo;

import java.util.Date;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月25日 下午5:28:32
 * @Description TODO (这里用一句话描述这个方法的作用)
 */
public class CalculationConfigVo {
	
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
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
	

}
