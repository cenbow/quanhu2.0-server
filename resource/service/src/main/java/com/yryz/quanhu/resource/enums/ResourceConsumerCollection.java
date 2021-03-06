/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月26日
 * Id: ResourceConsumerCollection.java, 2018年1月26日 下午2:53:35 yehao
 */
package com.yryz.quanhu.resource.enums;

import java.util.HashSet;
import java.util.Set;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月26日 下午2:53:35
 * @Description 资源消费队列处理判断集合
 */
public class ResourceConsumerCollection {
	
	private static Set<String> RESOURCE_CONSUMERS = new HashSet<>();
	
	static {
		RESOURCE_CONSUMERS.add("1003");
		RESOURCE_CONSUMERS.add("1004");
		RESOURCE_CONSUMERS.add("1005");
		RESOURCE_CONSUMERS.add("1006");
		RESOURCE_CONSUMERS.add("1007");
		RESOURCE_CONSUMERS.add("1008");
		RESOURCE_CONSUMERS.add("10081");
	}
	
	/**
	 * 检查是否包含类型
	 * @param moduleEnum
	 * @return
	 */
	public static boolean check(String moduleEnum){
		return RESOURCE_CONSUMERS.contains(moduleEnum);
	}

}
