package com.yryz.quanhu.dymaic.service;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@Resource
	ElasticsearchService elasticsearchService;
	
	@GetMapping("/test")
	public void test(){
//		elasticsearchService.rebuildCoterieInfo();
		elasticsearchService.rebuildResourceInfo();
//		elasticsearchService.rebuildUserInfo();
	}
}
