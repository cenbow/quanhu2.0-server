/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月19日
 * Id: RedisSupport.java, 2018年1月19日 下午3:37:33 yehao
 */
package com.yryz.common.redis;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.yryz.common.mongodb.ReflectionUtils;
import com.yryz.framework.core.cache.RedisTemplateBuilder;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月19日 下午3:37:33
 * @Description Redis工作方法支持
 */
public class RedisSupport<T> {
	
	protected RedisTemplate<String, T> redisTemplate;
	
	@Autowired
	protected RedisTemplateBuilder redisTemplateBuilder;
	
	@PostConstruct
	public void init(){
		redisTemplate = redisTemplateBuilder.buildRedisTemplate(getEntityClass());
	}
	
    /** 
     * 获取需要操作的实体类class 
     *  
     * @return 
     */  
    private Class<T> getEntityClass(){  
        return ReflectionUtils.getSuperClassGenricType(getClass());
    }  
	

}
