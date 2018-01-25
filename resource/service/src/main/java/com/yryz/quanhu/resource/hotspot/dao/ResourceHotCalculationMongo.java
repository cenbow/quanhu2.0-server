/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月25日
 * Id: ResourceHotCalculationMongo.java, 2018年1月25日 下午3:13:39 yehao
 */
package com.yryz.quanhu.resource.hotspot.dao;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.yryz.common.mongodb.AbsBaseMongoDAO;
import com.yryz.quanhu.resource.hotspot.entity.ResourceHotCalculation;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月25日 下午3:13:39
 * @Description 资源计算的热度统计值
 */
@Service
public class ResourceHotCalculationMongo extends AbsBaseMongoDAO<ResourceHotCalculation> {
	
	/**
	 * mongotemplate设置方法
	 * @param mongoTemplate
	 * @see com.yryz.common.mongodb.AbsBaseMongoDAO#setMongoTemplate(org.springframework.data.mongodb.core.MongoTemplate)
	 */
	@Override
	protected void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

}
