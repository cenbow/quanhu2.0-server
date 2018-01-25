/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月25日
 * Id: HotSpotAPI.java, 2018年1月25日 下午1:32:13 yehao
 */
package com.yryz.quanhu.resource.hotspot.api;

import com.yryz.quanhu.resource.hotspot.vo.EventInfo;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月25日 下午1:32:13
 * @Description 热度计算API
 */
public interface HotSpotApi {
	
	/**
	 * 热度计算(JOB)
	 */
	void calculation();
	
	/**
	 * 热度衰减
	 */
	void attenuation();
	
	/**
	 * 处理事件
	 * @param eventInfo
	 */
	void processEvent(EventInfo eventInfo);
	
	/**
	 * 初始化一个热度对象
	 * @param type 1资源；2用户
	 * @param objectId
	 */
	void saveHeat(String type ,String objectId);
	
	/**
	 * 保存热度计算对象
	 * @param eventCode
	 * @param eventValue
	 */
	void saveConfig(String calculationType ,String eventCode ,Integer eventValue);
	
}
