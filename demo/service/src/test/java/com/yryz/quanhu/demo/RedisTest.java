/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月19日
 * Id: RedisTest.java, 2018年1月19日 下午4:03:08 yehao
 */
package com.yryz.quanhu.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yryz.common.response.PageList;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.demo.elasticsearch.entity.User;
import com.yryz.quanhu.demo.redis.PageRedis;
import com.yryz.quanhu.demo.redis.UserRedis;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月19日 下午4:03:08
 * @Description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisTest {
	
	@Autowired
	UserRedis userRedis;
	
	@Autowired
	PageRedis pageRedis;
	
	@Test
	public void set(){
		User user = new User();
		user.setUsername(1);
		user.setRealName("yehao");
		userRedis.save(user);
	}
	
	@Test
	public void get(){
		User user = userRedis.get("yehao-test");
		System.out.println(GsonUtils.parseJson(user));
	}
	
	@Test
	public void setPage(){
		User user = new User();
		user.setCreateTime(DateUtils.currentDatetime());
		user.setRealName("hello");
		List<User> list = new ArrayList<>();
		list.add(user);
		List<List<User>> list1 = new ArrayList<>();
		list1.add(list);
		PageList<List<User>> pageList = new PageList<>();
		pageList.setEntities(list1);
		pageRedis.saveRE(pageList);
	}
	
	@Test
	public void getPage(){
		PageList<List<User>> pageList = pageRedis.getRE();
		System.out.println(GsonUtils.parseJson(pageList));
	}
}
