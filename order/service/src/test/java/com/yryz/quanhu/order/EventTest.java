/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年2月5日
 * Id: EventTest.java, 2018年2月5日 上午11:14:19 yehao
 */
package com.yryz.quanhu.order;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.Response;
import com.yryz.quanhu.score.entity.ScoreFlowQuery;
import com.yryz.quanhu.score.service.EventAPI;
import com.yryz.quanhu.score.service.EventAcountApiService;
import com.yryz.quanhu.score.vo.EventInfo;
import com.yryz.quanhu.score.vo.ScoreFlowReportVo;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年2月5日 上午11:14:19
 * @Description TODO (这里用一句话描述这个方法的作用)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EventTest {
	
	@Reference
	private EventAPI eventAPI;
	
	
	@Reference
	private  EventAcountApiService  eventAcountApiService;
	
	
	@Test
	public void eventCommit(){
		EventInfo event = new EventInfo();
		event.setEventCode("24");
		event.setResourceId("744548334709260288");
		event.setOwnerId("737469249351843840");
		eventAPI.commit(event);
	}
	
	@Test
	public void getEventAcountAll(){
		ScoreFlowQuery sfq = new  ScoreFlowQuery();
		sfq.setGrowLevel("4");
//		String userId = "123456789";
//		sfq.setUserId(userId);
		Response<List<ScoreFlowReportVo>> VO = eventAcountApiService.getEventAcountAll(sfq);
		System.out.println(" VO.getData().size(): "+VO.getData().size());
	}


}
