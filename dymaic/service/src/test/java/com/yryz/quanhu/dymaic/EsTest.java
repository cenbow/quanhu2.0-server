package com.yryz.quanhu.dymaic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yryz.quanhu.dymaic.service.ElasticsearchService;

/**
 * 按照下面模板可以进行单元测试 测试dubbo提供者直接@Autowired
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsTest {
	    @Autowired
	    private ElasticsearchService elasticsearchService;
	
	    @Test
	    public void exampleTest(){
	    	String name=elasticsearchService.findUserName(1L);
	        System.out.printf(name);
	    }
}
