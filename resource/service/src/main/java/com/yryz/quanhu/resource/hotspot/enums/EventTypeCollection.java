/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月25日
 * Id: EventTypeCollection.java, 2018年1月25日 下午1:43:58 yehao
 */
package com.yryz.quanhu.resource.hotspot.enums;

import java.util.HashSet;
import java.util.Set;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月25日 下午1:43:58
 * @Description 事件类型枚举
 */
public class EventTypeCollection {
	
	private static Set<String> resourceHots = new HashSet<>();
	private static Set<String> userHots = new HashSet<>();
	
	static {
		
		//资源热度计算
		resourceHots.add("4");
		resourceHots.add("5");
		resourceHots.add("6");
		resourceHots.add("7");
		resourceHots.add("13");
		
		//用户热度计算
		userHots.add("21");
		userHots.add("4");
		userHots.add("5");
		userHots.add("6");
		userHots.add("7");
		
	}
	
	/**
	 * 检查是否资源计算类型
	 * @param eventCode
	 * @return
	 */
	public static boolean checkResourceHotCalculation(String eventCode){
		return resourceHots.contains(eventCode);
	}
	
	/**
	 * 检查是否用户计算类型
	 * @param eventCode
	 * @return
	 */
	public static boolean checkUserHotCalculation(String eventCode){
		return userHots.contains(eventCode);
	}
	
}
