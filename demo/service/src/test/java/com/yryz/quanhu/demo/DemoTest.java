package com.yryz.quanhu.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yryz.quanhu.demo.service.ElasticsearchService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoTest {
	@Autowired
    private ElasticsearchService elasticsearchService;

    @Test
    public void elasticsearchTest(){
    	String name=elasticsearchService.findUserName(100L);
        System.out.printf(name);
    }
}
