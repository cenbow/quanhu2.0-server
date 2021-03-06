/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月19日
 * Id: OrderTest.java, 2018年1月19日 下午2:10:59 yehao
 */
package com.yryz.quanhu.order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.Response;
import com.yryz.quanhu.grow.entity.GrowFlowQuery;
import com.yryz.quanhu.grow.service.GrowAPI;
import com.yryz.quanhu.order.grow.service.GrowFlowService;
import com.yryz.quanhu.score.entity.ScoreFlowQuery;
import com.yryz.quanhu.score.service.EventAPI;
import com.yryz.quanhu.score.service.EventAcountApiService;
import com.yryz.quanhu.score.service.ScoreAPI;
import com.yryz.quanhu.score.vo.EventInfo;
import com.yryz.quanhu.score.vo.EventReportVo;
import com.yryz.quanhu.score.vo.EventSign;
import com.yryz.quanhu.score.vo.ScoreFlowReportVo;

/**
 * @author syc
 * @version 2.0
 * @date 2018年1月19日 下午2:10:59
 * @Description 积分单元测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ScoreTest {

	
	@Reference
	private static GrowFlowService growFlowService;
	


	@Reference
	private static EventAPI eventAPI;
	
	
	@Reference
	private static ScoreAPI scoreAPI;
	
	
	@Reference
	private static EventAcountApiService eventAcountApiService;
	
	
	@Reference
	private static GrowAPI growAPI;

	
	@Test
    public void commitTest() {
        EventInfo info = new EventInfo();
        info.setUserId("123456789");
        info.setCircleId("62jqe12bhauv");
        info.setCoterieId("ctf8gkwjvo1q");
//        info.setEventCode("11"); //ventType=1：一次性触发 //  成长值eventType=31：事件类型eventType=1：一次性触发
        info.setEventCode("15"); //eventType=2：每次触发 //  成长值eventType=1：事件类型eventType=2： 每次触发
//        info.setEventCode("3"); //eventType=3：循环触发 //  成长值eventType=3：事件类型eventType=3：循环触发 
//        info.setEventCode("15"); //eventType=4：签到区间循环 //  成长值eventType=31：一次性触发
        
//		resourceId = data.get("resourceId");
//		userId = data.get("ownerId");
        info.setResourceId("999999");
        info.setOwnerId("123456789");
        info.setEventNum(1);
        info.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        eventAPI.commit(info);

        System.out.println("commit event result");
    }
	
	

	@Test
    public  void getEventSign()  {
		String custId = "123456789";
		String eventCode = "1";
		EventSign  sign = eventAcountApiService.getEventSign(custId, eventCode);
		System.out.println("sign list: "+sign);

     // System.out.println("ScoreFlow list: "+list.size());
    }
	

	@Test
    public  void getScoreFlow()  {
		String userId = "123456789";
		String consumeFlag = "";
		ScoreFlowQuery sfq =new ScoreFlowQuery();
		sfq.setUserId(userId);
//		sfq.setConsumeFlag(consumeFlag);
//		PageList<ScoreFlow> sfslist = eventAcountApiService.getScoreFlow(sfq);
//		
//		System.out.println("sfslist size(): "+sfslist.getCount());

     // System.out.println("ScoreFlow list: "+list.size());
    }
	
	
	@Test
    public  void getScoreFlowAll()  {
		String userId = "123456789";
		ScoreFlowQuery sfq =new ScoreFlowQuery();
		sfq.setUserId(userId);
		List<ScoreFlowReportVo> list  = scoreAPI.getScoreFlowAll(sfq);
		System.out.println("list.size()"+list.size());
    }
	
	

	@Test
    public  void getGrowFlow()  {
		String userId = "123456789";
		String consumeFlag = "";
		GrowFlowQuery sfq =new GrowFlowQuery();
		sfq.setUserId(userId);
		sfq.setConsumeFlag(consumeFlag);
//		PageList<GrowFlow> sfslist = eventAcountApiService.getGrowFlow(sfq);
//		
//		System.out.println("sfslist size(): "+sfslist.getCount());

     // System.out.println("ScoreFlow list: "+list.size());
    }
	

 
	@Test
	public void getEventAcountBatch(){
		Set<Long> userIds= new HashSet<>();
		userIds.add(Long.valueOf("123456789"));
		userIds.add(Long.valueOf("727061873573347328"));
		userIds.add(Long.valueOf("729669966696603648"));
		System.out.println(eventAcountApiService.getEventAcountBatch(userIds));
		
		
	}
	
	
	@Test
	public void getGrowLevelAll(){
		System.out.println("growAPI.getGrowLevelAll():"+growAPI.getGrowLevelAll());
         
	}
	
	@Test
	public void getScoreFlowList(){
		EventInfo event = new EventInfo();
		event.setUserId("738941993638281216");
	Response<EventReportVo> VO = eventAPI.getScoreFlowList(event);
	System.out.println(" VO.getData().getGrowLevel(): "+VO.getData().getGrowLevel());
         
	}
	
	@Test
	public void promoteGrowLevel(){
	int i = 	growAPI.promoteGrowLevel("737469249351843840", "5", "19");
	System.out.println("i "+i);
	}
	
	
	@Test
	public void consumeScore(){
		scoreAPI.consumeScore("359985349648384", 200, "36");
//	int i = 	growAPI.promoteGrowLevel("737469249351843840", "5", "19");
//	System.out.println("i "+i);
	}

	
}
