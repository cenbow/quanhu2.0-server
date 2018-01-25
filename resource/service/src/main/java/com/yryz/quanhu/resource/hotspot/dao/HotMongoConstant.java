/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月28日
 * Id: HotMongoConstant.java, 2017年9月28日 上午11:52:30 yehao
 */
package com.yryz.quanhu.resource.hotspot.dao;

import com.yryz.common.context.Context;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年9月28日 上午11:52:30
 * @Description 热度数据mongo计算类
 */
public class HotMongoConstant {
	
	/**
	 * event事件库名称
	 */
	public static final String DB_EVENT = Context.getProperty("MONGO_DATABASE");
	
	public static final String DB_SERVER = Context.getProperty("MONGO_SERVER");
	
	public static final int DB_PORT=Integer.valueOf(Context.getProperty("MONGO_PORT"));
	/**
	 * 计算结果mongo
	 */
	public static final String CALCULATION_INFO_MODEL = "CalculationInfo";
	
	/**
	 * 圈子计算中间数据表名
	 */
	public static final String CIRCLE_HOT_CALCULATION_MODEL = "CircleHotCalculation";
	
	/**
	 * 私圈计算中间数据表名
	 */
	public static final String COTERIE_HOT_CALCULATION_MODEL = "CoterieHotCalculation";
	
	/**
	 * 资源计算中间数据表名
	 */
	public static final String RESOURCE_HOT_CALCULATION_MODEL = "ResourceHotCalculation";
	
	/**
	 * 用户计算中间数据表名
	 */
	public static final String USER_HOT_CALCULATION_MODEL = "UserHotCalculation";
	
	/**
	 * 资源参与计算中间数据表名
	 */
	public static final String RESOURCE_PART_CALCULATION_MODEL = "ResourcePartCalculation";
	
	/**
	 * 资源阅读中间数据表名
	 */
	public static final String RESOURCE_READ_CALCULATION_MODEL = "ResourceReadCalculation";

}
