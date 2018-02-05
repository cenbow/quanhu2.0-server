/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月25日
 * Id: HotspotServiceImpl.java, 2018年1月25日 下午2:33:30 yehao
 */
package com.yryz.quanhu.resource.hotspot.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.resource.enums.ResourceEnum;
import com.yryz.quanhu.resource.hotspot.dao.CalculationConfigMongo;
import com.yryz.quanhu.resource.hotspot.dao.HeatInfoMongo;
import com.yryz.quanhu.resource.hotspot.dao.ResourceHotCalculationMongo;
import com.yryz.quanhu.resource.hotspot.dao.UserHotCalculationMongo;
import com.yryz.quanhu.resource.hotspot.entity.CalculationConfig;
import com.yryz.quanhu.resource.hotspot.entity.HeatInfo;
import com.yryz.quanhu.resource.hotspot.entity.HotSpotEventInfo;
import com.yryz.quanhu.resource.hotspot.entity.ResourceHotCalculation;
import com.yryz.quanhu.resource.hotspot.entity.UserHotCalculation;
import com.yryz.quanhu.resource.hotspot.enums.EventTypeCollection;
import com.yryz.quanhu.resource.hotspot.enums.HeatInfoEnum;
import com.yryz.quanhu.resource.hotspot.service.HotspotService;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月25日 下午2:33:30
 * @Description 热度服务接口实现
 */
@Service
public class HotspotServiceImpl implements HotspotService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CalculationConfigMongo configMongo;
	
	@Autowired
	private ResourceHotCalculationMongo resourceHotCalculationMongo;
	
	@Autowired
	private UserHotCalculationMongo userHotCalculationMongo;
	
	@Autowired
	private HeatInfoMongo heatInfoMongo;
	
	/**
	 * 处理事件
	 * @param eventInfo
	 * @see com.yryz.quanhu.resource.hotspot.service.HotspotService#processEvent(com.yryz.quanhu.resource.hotspot.entity.HotSpotEventInfo)
	 */
	@Override
	public void processEvent(HotSpotEventInfo eventInfo) {
		String eventCode = eventInfo.getEventCode();
		if(StringUtils.isEmpty(eventCode)){
			return ;
		}
		if(EventTypeCollection.checkResourceHotCalculation(eventCode)){
			saveResourceHotCalculation(eventInfo);
		}
		if(EventTypeCollection.checkUserHotCalculation(eventCode)){
			saveUserHotCalculation(eventInfo);
		}
	}
	
	

	/**
	 * 保存资源热度统计中间值
	 * @param eventInfo
	 * @see com.yryz.quanhu.resource.hotspot.service.HotspotService#saveResourceHotCalculation(com.yryz.quanhu.resource.hotspot.entity.HotSpotEventInfo)
	 */
	@Override
	public void saveResourceHotCalculation(HotSpotEventInfo eventInfo) {
		//获取热度计算的配置公式
		Integer snum = configMongo.getConfigValue(HeatInfoEnum.HOT_CONFIG_TYPE_RESOURCE, eventInfo.getEventCode());
		if(snum == null){
			logger.warn("saveResourceHotCalculation error ,eventCode --> num is null, data : " + GsonUtils.parseJson(eventInfo));
			return ;
		}
		int num = snum == null ? 0 : snum.intValue();
		ResourceHotCalculation calculation = new ResourceHotCalculation();
		if(eventInfo.getResourceId() != null){
			calculation.setResourceId(eventInfo.getResourceId());
			calculation.setCreateTime(new Date().getTime());
			calculation.setEventCode(eventInfo.getEventCode());
			calculation.setEventId(eventInfo.getEventId());
			calculation.setEventNum(eventInfo.getEventNum().longValue());
			calculation.setHeat((num * eventInfo.getEventNum().longValue()));
			resourceHotCalculationMongo.save(calculation);
		}		
	}

	/**
	 * 保存用户资源统计中间值
	 * @param eventInfo
	 * @see com.yryz.quanhu.resource.hotspot.service.HotspotService#saveUserHotCalculation(com.yryz.quanhu.resource.hotspot.entity.HotSpotEventInfo)
	 */
	@Override
	public void saveUserHotCalculation(HotSpotEventInfo eventInfo) {
		//获取热度计算的配置公式
		Integer snum = configMongo.getConfigValue(HeatInfoEnum.HOT_CONFIG_TYPE_USER, eventInfo.getEventCode());
		if(snum == null){
			logger.warn("saveResourceHotCalculation error ,eventCode --> num is null, data : " + GsonUtils.parseJson(eventInfo));
			return ;
		}
		int num = snum == null ? 0 : snum.intValue();
		UserHotCalculation calculation = new UserHotCalculation();
		if(eventInfo.getOwnerId() != null){
			calculation.setUserId(eventInfo.getOwnerId());
			calculation.setCreateTime(new Date().getTime());
			calculation.setEventCode(eventInfo.getEventCode());
			calculation.setEventId(eventInfo.getEventId());
			calculation.setEventNum(eventInfo.getEventNum().longValue());
			calculation.setHeat((num * eventInfo.getEventNum().longValue()));
			userHotCalculationMongo.save(calculation);
		}
	}



	/**
	 * 初始化热度值
	 * @param type
	 * @param objectId
	 * @see com.yryz.quanhu.resource.hotspot.service.HotspotService#saveHeat(java.lang.String, java.lang.String)
	 */
	@Override
	public HeatInfo saveHeat(String type, String objectId , String talentType) {
		HeatInfo heatInfo = new HeatInfo();
		heatInfo.setObjectId(objectId);
		heatInfo.setType(type);
		if(StringUtils.isNotEmpty(talentType) && ResourceEnum.TALENT_TYPE_TRUE.equals(talentType)){
			heatInfo.setInitHeat(HeatInfoEnum.INIT_TALENT_HOT);
		} else {
			heatInfo.setInitHeat(HeatInfoEnum.INIT_USER_HOT);
		}
		heatInfo.setAttenuation(0L);
		heatInfo.setBehaviorHeat(0L);
		heatInfo.setCreateTime(System.currentTimeMillis());
		heatInfo.setUpdateTime(System.currentTimeMillis());
		heatInfo.setHeat(heatInfo.countHeat());
		if(heatInfoMongo.get(type, objectId) != null){
			return heatInfoMongo.update(heatInfo);
		} else {
			return heatInfoMongo.save(heatInfo);
		}
	}



	/**
	 * 保存热度配置
	 * @param eventCode
	 * @param eventValue
	 * @see com.yryz.quanhu.resource.hotspot.service.HotspotService#saveConfig(java.lang.String, java.lang.String)
	 */
	@Override
	public void saveConfig(String calculationType ,String eventCode, Integer eventValue) {
		CalculationConfig calculationConfig = new CalculationConfig();
		calculationConfig.setCalculationType(calculationType);
		calculationConfig.setConfigName(eventCode);
		calculationConfig.setConfigValue(eventValue);
		this.configMongo.save(calculationConfig);
	}

}
