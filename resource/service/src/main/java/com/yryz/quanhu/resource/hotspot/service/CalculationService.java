/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月25日
 * Id: CalculationService.java, 2018年1月25日 下午2:35:11 yehao
 */
package com.yryz.quanhu.resource.hotspot.service;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月25日 下午2:35:11
 * @Description 热度计算
 */
public interface CalculationService {
	
	/**
	 * 热度统计
	 */
	void calculation();
	
	/**
	 * 热度衰减
	 */
	void attenuation();

}
