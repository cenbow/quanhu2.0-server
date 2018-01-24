package com.yryz.quanhu.dymaic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yryz.common.response.PageList;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.dymaic.vo.CoterieInfoVo;
import com.yryz.quanhu.dymaic.vo.ResourceInfoVo;
import com.yryz.quanhu.dymaic.vo.UserSimpleVo;

@RestController
public class TestController {
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	@Autowired
	private ElasticsearchService elasticsearchService;
	private static final ObjectMapper MAPPER = new ObjectMapper();
	@GetMapping(value = "/test")
	public void test(){
		PageList<ResourceInfoVo>  d1=elasticsearchService.searchTopicInfo("测试", 0, 10);
//		PageList<ResourceInfoVo>  d2=elasticsearchService.searchReleaseInfo("测试", 0, 10);
//		PageList<UserSimpleVo>  d3=elasticsearchService.searchUser("姜昆", 0, 10);
//		PageList<CoterieInfoVo>  d4=elasticsearchService.searchCoterieInfo("sdf", 0, 10);
		try {
			System.out.println(MAPPER.writeValueAsString(d1));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
//		System.out.println(GsonUtils.parseJson(d2));
//		System.out.println(GsonUtils.parseJson(d3));
//		System.out.println(GsonUtils.parseJson(d4));
	}
}
