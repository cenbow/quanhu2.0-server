/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月25日
 * Id: UserHotCalculationMongo.java, 2018年1月25日 下午3:13:53 yehao
 */
package com.yryz.quanhu.resource.hotspot.dao;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.yryz.common.mongodb.AbsBaseMongoDAO;
import com.yryz.quanhu.resource.hotspot.entity.UserHotCalculation;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月25日 下午3:13:53
 * @Description 用户热度计算中间值
 */
@Service
public class UserHotCalculationMongo extends AbsBaseMongoDAO<UserHotCalculation> {

	/**
	 * @param mongoTemplate
	 * @see com.yryz.common.mongodb.AbsBaseMongoDAO#setMongoTemplate(org.springframework.data.mongodb.core.MongoTemplate)
	 */
	@Override
	protected void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

}
