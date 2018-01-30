package com.yryz.quanhu.dymaic.canal;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticSearchTest {
	@Resource
    private ElasticsearchTemplate elasticsearchTemplate;
	
	@Test
	public void esTest(){
//		elasticsearchTemplate.deleteIndex("quanhu-v2-userinfo");
		elasticsearchTemplate.deleteIndex("quanhu-v2-resourceinfo");
	}
}
