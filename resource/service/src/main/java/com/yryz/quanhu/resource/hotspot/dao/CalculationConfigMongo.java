/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月25日
 * Id: CalculationConfigMongo.java, 2018年1月25日 下午3:17:04 yehao
 */
package com.yryz.quanhu.resource.hotspot.dao;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.yryz.common.mongodb.AbsBaseMongoDAO;
import com.yryz.quanhu.resource.hotspot.entity.CalculationConfig;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月25日 下午3:17:04
 * @Description 热度计数的配置类
 */
@Service
public class CalculationConfigMongo extends AbsBaseMongoDAO<CalculationConfig> {
	
	/**
	 * @param mongoTemplate
	 * @see com.yryz.common.mongodb.AbsBaseMongoDAO#setMongoTemplate(org.springframework.data.mongodb.core.MongoTemplate)
	 */
	@Override
	protected void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	/**
	 * 获取配置对象
	 * @param calculationType
	 * @param configName
	 * @return
	 */
	public Integer getConfigValue(String calculationType ,String configName ){
		Query query = new Query();
		query.addCriteria(Criteria.where("calculationType").is(calculationType).andOperator(Criteria.where("configName").is(configName)));
		CalculationConfig config = findOne(query);
		if(config != null){
			return config.getConfigValue();
		} else {
			return null;
		}
	}
	
	/**
	 * 获取列表对象
	 * @param calculationType
	 * @return
	 */
	public List<CalculationConfig> getConfigByType(String calculationType){
		return null;
	}

	
}