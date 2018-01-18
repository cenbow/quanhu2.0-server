/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月17日
 * Id: JsonTest.java, 2018年1月17日 下午8:56:55 yehao
 */
package com.yryz.quanhu.resource;

import java.io.IOException;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yryz.quanhu.resource.vo.ResourceVo;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月17日 下午8:56:55
 * @Description TODO (这里用一句话描述这个方法的作用)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JsonTest {
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	public void jsonTest(){
		JSONObject json = new JSONObject();
		json.put("resourceId", "testResourceId");
		try {
			ResourceVo resourceVo = objectMapper.readValue(json.toString(), ResourceVo.class);
			System.out.println(resourceVo.getResourceId());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ResourceVo resource = new ResourceVo();
		resource.setResourceId("resoureId");
		try {
			String val = objectMapper.writeValueAsString(resource);
			System.out.println(val);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


}
