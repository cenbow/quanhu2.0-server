/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月29日
 * Id: ResourceRedis.java, 2018年1月29日 上午9:28:00 yehao
 */
package com.yryz.quanhu.resource.dao.redis;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.resource.entity.ResourceModel;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月29日 上午9:28:00
 * @Description 资源管理redis访问类
 */
@Service
public class ResourceRedis {
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	/**
	 * 清理首页缓存
	 */
	public void clearAppRecommend(){
		redisTemplate.delete(RedisConstant.APP_RECOMMEND);
	}
	
	/**
	 * 添加首页缓存
	 * @param resourceId
	 */
	public void addAppRecommend(List<String> resourceIds){
		if(CollectionUtils.isEmpty(resourceIds)){
			return ;
		}
		redisTemplate.opsForList().rightPushAll(RedisConstant.APP_RECOMMEND, resourceIds);
	}
	
	/**
	 * 首页推荐数据获取
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<String> getAppRecommendList(int start , int limit){
		if(start < 0 ) {
			start = 0;
		}
		if(limit < 0 ) {
			limit = 10;
		}
		return redisTemplate.opsForList().range(RedisConstant.APP_RECOMMEND, start, start + limit - 1);
	}
	
	/**
	 * 保存资源
	 * @param resourceModel
	 */
	public void saveResource(ResourceModel resourceModel){
		redisTemplate.opsForValue().set(RedisConstant.getResourceModelKey(resourceModel.getResourceId()), 
				GsonUtils.parseJson(resourceModel), 2, TimeUnit.DAYS);
	}
	
	/**
	 * 获取资源
	 * @param resourceId
	 * @return
	 */
	public ResourceModel getResource(String resourceId){
		String val = redisTemplate.opsForValue().get(RedisConstant.getResourceModelKey(resourceId));
		if(StringUtils.isNotEmpty(val)){
			return GsonUtils.json2Obj(val, ResourceModel.class);
		}
		return null;
	}

}
