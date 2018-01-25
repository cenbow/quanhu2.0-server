/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月25日
 * Id: HotSpotApiImpl.java, 2018年1月25日 下午4:43:51 yehao
 */
package com.yryz.quanhu.resource.hotspot.provider;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.resource.hotspot.api.HotSpotApi;
import com.yryz.quanhu.resource.hotspot.entity.HotSpotEventInfo;
import com.yryz.quanhu.resource.hotspot.service.CalculationService;
import com.yryz.quanhu.resource.hotspot.service.HotspotService;
import com.yryz.quanhu.resource.hotspot.vo.EventInfo;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月25日 下午4:43:51
 * @Description 热度
 */
@Service(interfaceClass=HotSpotApi.class)
public class HotSpotApiProvider implements HotSpotApi {
	
	@Autowired
	private CalculationService calculationService;
	
	@Autowired
	private HotspotService hotspotService;

	/**
	 * @see com.yryz.quanhu.resource.hotspot.api.HotSpotApi#calculation()
	 */
	@Override
	public void calculation() {
		calculationService.calculation();
	}
	
	public void attenuation(){
		calculationService.attenuation();
	}

	/**
	 * @param eventInfo
	 * @see com.yryz.quanhu.resource.hotspot.api.HotSpotApi#processEvent(com.yryz.quanhu.resource.hotspot.vo.EventInfo)
	 */
	@Override
	public void processEvent(EventInfo eventInfo) {
		hotspotService.processEvent(GsonUtils.parseObj(eventInfo, HotSpotEventInfo.class));
	}

	/**
	 * 初始化热度对象
	 * @param type
	 * @param objectId
	 * @see com.yryz.quanhu.resource.hotspot.api.HotSpotApi#saveHeat(java.lang.String, java.lang.String)
	 */
	@Override
	public void saveHeat(String type, String objectId) {
		hotspotService.saveHeat(type, objectId);
	}

	/**
	 * 保存配置
	 * @param eventCode
	 * @param eventValue
	 * @see com.yryz.quanhu.resource.hotspot.api.HotSpotApi#saveConfig(java.lang.String, java.lang.String)
	 */
	@Override
	public void saveConfig(String calculationType ,String eventCode, Integer eventValue) {
		hotspotService.saveConfig(calculationType ,eventCode, eventValue);
	}

}
