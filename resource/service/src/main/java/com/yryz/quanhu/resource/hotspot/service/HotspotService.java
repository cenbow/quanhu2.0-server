/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月25日
 * Id: HotspotService.java, 2018年1月25日 下午1:45:58 yehao
 */
package com.yryz.quanhu.resource.hotspot.service;

import com.yryz.quanhu.resource.hotspot.entity.HeatInfo;
import com.yryz.quanhu.resource.hotspot.entity.HotSpotEventInfo;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月25日 下午1:45:58
 * @Description 热度接口API
 */
public interface HotspotService {
	
	/**
	 * 处理事件
	 * @param eventInfo
	 */
	void processEvent(HotSpotEventInfo eventInfo);
	
	
	/**
	 * 保存资源热度计算值
	 * @param eventInfo
	 */
	void saveResourceHotCalculation(HotSpotEventInfo eventInfo);
	
	/**
	 * 保存用户热度计算值
	 * @param eventInfo
	 */
	void saveUserHotCalculation(HotSpotEventInfo eventInfo);
	
	/**
	 * 初始化一个热度信息
	 * @param type
	 * @param objectId
	 */
	HeatInfo saveHeat(String type ,String objectId , String talentType);
	
	/**
	 * 保存统计对象
	 * @param eventCode
	 * @param eventValue
	 */
	void saveConfig(String calculationType ,String eventCode, Integer eventValue);

}
