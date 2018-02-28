/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年2月5日
 * Id: EventTest.java, 2018年2月5日 上午11:14:19 yehao
 */
package com.yryz.quanhu.order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.score.entity.ScoreFlowQuery;
import com.yryz.quanhu.score.service.EventAPI;
import com.yryz.quanhu.score.service.EventAcountApiService;
import com.yryz.quanhu.score.vo.EventInfo;
import com.yryz.quanhu.score.vo.EventReportVo;
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
	public void getEventAcount(){
		ScoreFlowQuery sfq = new  ScoreFlowQuery();
		sfq.setGrowLevel("4");
		Response<PageList<ScoreFlowReportVo>> VO = eventAcountApiService.getEventAcount(sfq);
	System.out.println(" VO.getData().getCount(): "+VO.getData().getCount());
	}


}
