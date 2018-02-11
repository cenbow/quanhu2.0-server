/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年2月10日
 * Id: PageRedis.java, 2018年2月10日 下午6:05:52 yehao
 */
package com.yryz.quanhu.demo.redis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
	
	public void save(PageList<User> page){
		redisTemplate = redisTemplateBuilder.buildRedisTemplate(PageList.class, User.class);
		redisTemplate.opsForValue().set("yehao", page);
	}
	
	public PageList<User> get(){
		redisTemplate = redisTemplateBuilder.buildRedisTemplate(PageList.class, User.class);
		return redisTemplate.opsForValue().get("yehao");
	}

}
