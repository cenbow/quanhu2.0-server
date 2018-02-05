/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月25日
 * Id: HeatInfoMongo.java, 2018年1月25日 下午3:41:20 yehao
 */
package com.yryz.quanhu.resource.hotspot.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.yryz.common.mongodb.AbsBaseMongoDAO;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.resource.entity.ResourceModel;
import com.yryz.quanhu.resource.hotspot.entity.HeatInfo;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月25日 下午3:41:20
 * @Description 热度信息的mongo对象
 */
@Service
public class HeatInfoMongo extends AbsBaseMongoDAO<HeatInfo>{

	/**
	 * @param mongoTemplate
	 * @see com.yryz.common.mongodb.AbsBaseMongoDAO#setMongoTemplate(org.springframework.data.mongodb.core.MongoTemplate)
	 */
	@Override
	protected void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	public HeatInfo save(HeatInfo heatInfo){
		return super.save(heatInfo);
	}
	
	public HeatInfo get(String type,String objectId){
		Query query = new Query();
		query.addCriteria(Criteria.where("objectId").is(objectId).andOperator(Criteria.where("type").is(type)));
		return findOne(query);
	}
	
	public HeatInfo update(HeatInfo heatInfo){
		Query query = new Query();
		query.addCriteria(Criteria.where("objectId").is(heatInfo.getObjectId()).andOperator(Criteria.where("type").is(heatInfo.getType())));
		String obj = GsonUtils.parseJson(heatInfo);
		JSONObject json = new JSONObject(obj);
		Set<String> keys = json.keySet();
		Update update = new Update();
		for (String key : keys) {
			update.set(key, json.get(key));
		}
		mongoTemplate.updateMulti(query, update, HeatInfo.class);
		return get(heatInfo.getType(), heatInfo.getObjectId());
	}
	
	public HeatInfo update(String type ,String objectId ,Long behaviorHeat){
		HeatInfo heatInfo = get(type, objectId);
		if(heatInfo != null){
			heatInfo.setBehaviorHeat(behaviorHeat);
			Long heat = heatInfo.countHeat();
			heatInfo.setHeat(heat);
			return update(heatInfo);
		}
		return null;
	}
	
	/**
	 * 按类型获取大于0的热度对象，性能较差，可能会出现内存不够用的问题
	 * @param type
	 * @return
	 */
	public List<HeatInfo> getList(String type){
		Query query = new Query();
		Criteria criteria = new Criteria();
		query.addCriteria(Criteria.where("type").is(type).andOperator(Criteria.where("heat").lt(0)));
		query.addCriteria(criteria);
		return find(query);
	}

}
