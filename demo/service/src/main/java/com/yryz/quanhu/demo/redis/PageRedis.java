/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年2月10日
 * Id: PageRedis.java, 2018年2月10日 下午6:05:52 yehao
 */
package com.yryz.quanhu.demo.redis;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.reflect.TypeToken;
import com.yryz.common.response.PageList;
import com.yryz.common.utils.DateUtils;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.demo.elasticsearch.entity.User;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年2月10日 下午6:05:52
 * @Description TODO (这里用一句话描述这个方法的作用)
 */
@Service
public class PageRedis {
	
    @Autowired
    private RedisTemplateBuilder redisTemplateBuilder;
	
	RedisTemplate<String, PageList> redisTemplate;
	
	RedisTemplate<String, PageList<List<User>>> redisTemplateRE;
	
	/**
	 * (不推荐，建议采用TypeReference注入对象)
	 * 通过传入对象类型和泛型类型两个参数来构建redisTemplate，仅支持一级泛型，即：PageList<T> 这种形式
	 * save，get
	 * @param page
	 */
	public void save(PageList<User> page){
		redisTemplate = redisTemplateBuilder.buildRedisTemplate(PageList.class, User.class);
		redisTemplate.opsForValue().set("yehao", page);
	}
	
	public PageList<User> get(){
		redisTemplate = redisTemplateBuilder.buildRedisTemplate(PageList.class, User.class);
		return redisTemplate.opsForValue().get("yehao");
	}
	
	
	/**
	 * (最佳实践)
	 * 通过构建TypeReference来传入对象，可支持多级泛型传入
	 * saveRE，getRE
	 * @param page
	 */
	public void saveRE(PageList<List<User>> page){
		redisTemplateRE = redisTemplateBuilder.buildRedisTemplate(new TypeReference<PageList<List<User>>>() {});
		redisTemplateRE.opsForValue().set("yehao-re", page);
	}
	
	public PageList<List<User>> getRE(){
		TypeReference<PageList<List<User>>> typeReference = new TypeReference<PageList<List<User>>>() {};
		redisTemplateRE = redisTemplateBuilder.buildRedisTemplate(typeReference);
		return redisTemplateRE.opsForValue().get("yehao-re");
	}

}
