package com.yryz.quanhu.dymaic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * 按照下面模板可以进行单元测试 测试dubbo提供者直接@Autowired
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsTest {
//	    @Autowired
//	    private ElasticsearchService elasticsearchService;
	    
	    @Autowired
	    private ElasticsearchTemplate elasticsearchTemplate;
	    
	    @Test
	    public void exampleTest(){
//	    	List<UserSimpleVo> list=elasticsearchService.searchUser("姜昆",0,3);
//	        System.out.println(list);
	    	elasticsearchTemplate.deleteIndex("quanhu-v2-resourceinfo");
	    	elasticsearchTemplate.deleteIndex("quanhu-v2");
	    }
}
