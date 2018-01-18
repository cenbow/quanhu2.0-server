package com.yryz.quanhu.demo;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.yryz.quanhu.demo.service.DemoReadService;
import com.yryz.quanhu.demo.service.ElasticsearchService;
import com.yryz.quanhu.demo.vo.DemoVo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoTest {
	@Autowired
    private ElasticsearchService elasticsearchService;
	
	@Resource
	private DemoReadService demoReadService;
	
    @Test
    public void elasticsearchTest(){
    	String name=elasticsearchService.findUserName(100L);
        System.out.printf(name);
    }
    
    @Test
    public void redisTest(){
    	DemoVo vo=demoReadService.find(11L);
    	System.out.println(vo);
    }
}
