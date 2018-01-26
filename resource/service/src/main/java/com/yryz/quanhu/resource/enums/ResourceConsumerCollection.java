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
	
	private static Set<Integer> RESOURCE_CONSUMERS = new HashSet<>();
	
	static {
		RESOURCE_CONSUMERS.add(1003);
		RESOURCE_CONSUMERS.add(1004);
		RESOURCE_CONSUMERS.add(1005);
		RESOURCE_CONSUMERS.add(1006);
		RESOURCE_CONSUMERS.add(1007);
	}
	
	public static boolean check(Integer moduleEnum){
		return RESOURCE_CONSUMERS.contains(moduleEnum);
	}

}
