/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月29日
 * Id: RedisConstant.java, 2018年1月29日 上午9:44:20 yehao
 */
package com.yryz.quanhu.resource.dao.redis;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月29日 上午9:44:20
 * @Description redis的key枚举管理
 */
public class RedisConstant {
	
	/**
	 * APP首页的redis的key
	 */
	public static final String APP_RECOMMEND = "RESOURCE:APPRECOMMEND";
	
	/**
	 * 资源缓存的key
	 */
	public static final String RESOURCE_MODEL = "RESOURCE:MODEL";
	
	public static String getResourceModelKey(String resourceId){
		return RESOURCE_MODEL + ":" + resourceId;
	}

}
