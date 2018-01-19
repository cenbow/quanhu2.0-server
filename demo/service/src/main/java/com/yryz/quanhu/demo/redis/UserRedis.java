/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月19日
 * Id: OrderRedis.java, 2018年1月19日 下午3:50:07 yehao
 */
package com.yryz.quanhu.demo.redis;

import org.springframework.stereotype.Service;

import com.yryz.common.redis.RedisSupport;
import com.yryz.quanhu.demo.elasticsearch.entity.User;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月19日 下午3:50:07
 * @Description 用户redis写法示例
 */
@Service
public class UserRedis extends RedisSupport<User> {
	
	public void save(User user){
		redisTemplate.opsForValue().set("yehao-test", user);
	}
	
	public User get(String id){
		return redisTemplate.opsForValue().get(id);
	}

}
