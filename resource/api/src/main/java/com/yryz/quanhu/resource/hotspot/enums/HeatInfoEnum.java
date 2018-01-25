/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月25日
 * Id: HeatInfoEnum.java, 2018年1月25日 下午3:44:43 yehao
 */
package com.yryz.quanhu.resource.hotspot.enums;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月25日 下午3:44:43
 * @Description 热度信息枚举值
 */
public class HeatInfoEnum {
	
	/**
	 * 热度类型:资源
	 */
	public static final String HEAT_TYPE_RESOURCE = "1";
	
	/**
	 * 热度类型：用户
	 */
	public static final String HEAT_TYPE_USER = "2";
	
	/**
	 * 初始热度
	 */
	public static final long INIT_HOT = 1000;
	
	/**
	 * 热度公式切换的N
	 */
	public static final int N = 3;
	
	/**
	 * 热度系数类型->人 配置类型
	 */
	public static final String HOT_CONFIG_TYPE_USER = "2-1";
	
	/**
	 * 热度系数类型->资源 配置类型
	 */
	public static final String HOT_CONFIG_TYPE_RESOURCE = "2-2";

}
