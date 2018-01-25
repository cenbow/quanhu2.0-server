/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月25日
 * Id: HotSpotTest.java, 2018年1月25日 下午5:17:59 yehao
 */
package com.yryz.quanhu.resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yryz.common.utils.IdGen;
import com.yryz.quanhu.resource.hotspot.api.HotSpotApi;
import com.yryz.quanhu.resource.hotspot.enums.HeatInfoEnum;
import com.yryz.quanhu.resource.hotspot.vo.EventInfo;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月25日 下午5:17:59
 * @Description TODO (这里用一句话描述这个方法的作用)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class HotSpotTest {
	
	@Autowired
	HotSpotApi hotSpotApi;
	
	@Test
	public void saveHeat(){
		hotSpotApi.saveHeat("1", "10001");
		hotSpotApi.saveHeat("1", "10002");
		hotSpotApi.saveHeat("1", "10003");
		hotSpotApi.saveHeat("1", "10004");
	}
	
	@Test
	public void saveConfig(){
		hotSpotApi.saveConfig(HeatInfoEnum.HOT_CONFIG_TYPE_RESOURCE, "4", 2);
		hotSpotApi.saveConfig(HeatInfoEnum.HOT_CONFIG_TYPE_RESOURCE, "5", 2);
		hotSpotApi.saveConfig(HeatInfoEnum.HOT_CONFIG_TYPE_RESOURCE, "6", 2);
		hotSpotApi.saveConfig(HeatInfoEnum.HOT_CONFIG_TYPE_RESOURCE, "7", 2);
		hotSpotApi.saveConfig(HeatInfoEnum.HOT_CONFIG_TYPE_RESOURCE, "13", 2);
		hotSpotApi.saveConfig(HeatInfoEnum.HOT_CONFIG_TYPE_USER, "21", 2);
		hotSpotApi.saveConfig(HeatInfoEnum.HOT_CONFIG_TYPE_USER, "4", 2);
		hotSpotApi.saveConfig(HeatInfoEnum.HOT_CONFIG_TYPE_USER, "5", 2);
		hotSpotApi.saveConfig(HeatInfoEnum.HOT_CONFIG_TYPE_USER, "6", 2);
		hotSpotApi.saveConfig(HeatInfoEnum.HOT_CONFIG_TYPE_USER, "7", 2);
	}
	
	@Test
	public void processEvent(){
		EventInfo eventInfo = new EventInfo();
		eventInfo.setCoterieId("testcote");
		eventInfo.setEventCode("4");
		eventInfo.setEventId(IdGen.uuid());
		eventInfo.setEventNum(1);
		eventInfo.setOwnerId(100L);
		eventInfo.setResourceId(10001L);
		eventInfo.setUserId(100L);
		for (int i = 0; i < 10; i++) {
			hotSpotApi.processEvent(eventInfo);
		}
		eventInfo.setEventCode("5");
		for (int i = 0; i < 10; i++) {
			hotSpotApi.processEvent(eventInfo);
		}
	}

	@Test
	public void calculation(){
		hotSpotApi.calculation();
	}
	
}
