/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月25日
 * Id: CalculationServiceImpl.java, 2018年1月25日 下午2:35:53 yehao
 */
package com.yryz.quanhu.resource.hotspot.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.stereotype.Service;

import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.resource.dao.mongo.ResourceMongo;
import com.yryz.quanhu.resource.entity.ResourceModel;
import com.yryz.quanhu.resource.hotspot.dao.HeatInfoMongo;
import com.yryz.quanhu.resource.hotspot.dao.HotMongoConstant;
import com.yryz.quanhu.resource.hotspot.entity.HeatInfo;
import com.yryz.quanhu.resource.hotspot.enums.HeatInfoEnum;
import com.yryz.quanhu.resource.hotspot.service.CalculationService;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月25日 下午2:35:53
 * @Description 热度计算的服务对象
 */
@Service
public class CalculationServiceImpl implements CalculationService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private HeatInfoMongo heatInfoMongo;
	
	@Autowired
	private ResourceMongo resourceMongo;
	
	@Autowired
	MongoTemplate mongoTemplate;

	/**
	 * 热度统计
	 * @see com.yryz.quanhu.resource.hotspot.service.CalculationService#calculation()
	 */
	@Override
	public void calculation() {
		logger.info("execute CalculateDataTask start...");
		calculUserHotTotalValue("start", "end");
		calculResourceHotTotalValue("start", "end");
		logger.info("execute CalculDataTask end...");
	}

	/**
	 * 热度衰减
	 * @see com.yryz.quanhu.resource.hotspot.service.CalculationService#attenuation()
	 */
	@Override
	public void attenuation() {
		List<HeatInfo> infos = heatInfoMongo.getList(HeatInfoEnum.HEAT_TYPE_RESOURCE);
		for (HeatInfo heatInfo : infos) {
			long heat = heatInfo.countHeat();
			heatInfo.setHeat(heat);
			heatInfoMongo.update(heatInfo);
		}
	}

	
	/**
	 * 用户计数统计
	 * @param startTime
	 * @param endTime
	 */
	public void calculUserHotTotalValue(String startTime,String endTime){
		logger.info("start calculUserHotTotalValue...");
		try {

			String map = "function() {	var value={count:1, heat:this.heat};  " + "emit(this.userId,value);  }";
			String reduce = "function(key,values){   " + " var reducedVal = { count: 0, heat: 0 };  "
					+ " for (var i = 0; i < values.length; i++) {  " + "reducedVal.count += values[i].count;  "
					+ " reducedVal.heat += values[i].heat;  " + " }" + "  return reducedVal;  }  ";
			MapReduceResults<HashMap> results = mongoTemplate.mapReduce(HotMongoConstant.USER_HOT_CALCULATION_MODEL, map, reduce, HashMap.class);
			results.forEach(new Consumer<HashMap>(){
				@Override
				public void accept(HashMap m) {
					String resourceId = m.get("_id").toString();
					String eventHotValue = ((Map) m.get("value")).get("heat").toString();
					if(StringUtils.isNotEmpty(eventHotValue)){
						//更新热度对象表
						HeatInfo heatInfo = heatInfoMongo.update(HeatInfoEnum.HEAT_TYPE_USER, resourceId, Long.valueOf(eventHotValue));
						if(heatInfo != null){
							//通知用户热度更新
							
						}
					}
				}
				
			});
			logger.info("end calculUserHotTotalValue...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 资源计数统计
	 * @param startTime
	 * @param endTime
	 */
	public void calculResourceHotTotalValue(String startTime,String endTime){
		logger.info("start calculResourceHotTotalValue...");
		try {
			String map = "function() {	var value={count:1, heat:this.heat};  " + "emit(this.resourceId,value);  }";

			String reduce = "function(key,values){   " + " var reducedVal = { count: 0, heat: 0 };  "
					+ " for (var i = 0; i < values.length; i++) {  " + "reducedVal.count += values[i].count;  "
					+ " reducedVal.heat += values[i].heat;  " + " }" + "  return reducedVal;  }  ";
			MapReduceResults<HashMap> results = mongoTemplate.mapReduce(HotMongoConstant.USER_HOT_CALCULATION_MODEL, map, reduce, HashMap.class);
			results.forEach(new Consumer<HashMap>(){
				@Override
				public void accept(HashMap m) {
					String resourceId = m.get("_id").toString();
					String eventHotValue = ((Map) m.get("value")).get("heat").toString();
					if(StringUtils.isNotEmpty(eventHotValue)){
						//更新热度对象表
						HeatInfo heatInfo = heatInfoMongo.update(HeatInfoEnum.HEAT_TYPE_RESOURCE, resourceId, Long.valueOf(eventHotValue));
						if(heatInfo != null){
							ResourceModel resourceModel = new ResourceModel();
							resourceModel.setResourceId(Integer.parseInt(resourceId));
							resourceModel.setHeat(heatInfo.getHeat());
							resourceMongo.update(resourceModel);
						}
					}
				}
				
			});
			logger.info("end calculResourceHotTotalValue...");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
