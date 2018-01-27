/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月16日
 * Id: ResourceServiceImpl.java, 2018年1月16日 下午4:25:44 yehao
 */
package com.yryz.quanhu.resource.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yryz.quanhu.resource.dao.mongo.ResourceMongo;
import com.yryz.quanhu.resource.entity.ResourceModel;
import com.yryz.quanhu.resource.enums.ResourceEnum;
import com.yryz.quanhu.resource.service.ResourceService;
import com.yryz.quanhu.resource.vo.ResourceVo;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月16日 下午4:25:44
 * @Description 资源管理业务逻辑实现
 */
@Service
public class ResourceServiceImpl implements ResourceService {
	
	@Autowired
	private ResourceMongo resourceMongo;

	/**
	 * 提交资源
	 * @param resources
	 * @see com.yryz.quanhu.resource.service.ResourceService#commitResource(java.util.List)
	 */
	@Override
	public void commitResource(List<ResourceModel> resources) {
		if(CollectionUtils.isNotEmpty(resources)){
			for (ResourceModel resourceModel : resources) {
				if(resourceMongo.get(resourceModel) != null){
					resourceMongo.update(resourceModel);
				} else {
					resourceMongo.save(resourceModel);
				}
			}
		}
	}

	/**
	 * 更新资源
	 * @param resources
	 * @see com.yryz.quanhu.resource.service.ResourceService#updateResource(java.util.List)
	 */
	@Override
	public void updateResource(List<ResourceModel> resources) {
		if(CollectionUtils.isNotEmpty(resources)){
			for (ResourceModel resourceModel : resources) {
				resourceMongo.update(resourceModel);
			}
		}
		
	}

	/**
	 * 删除资源
	 * @param resources
	 * @see com.yryz.quanhu.resource.service.ResourceService#deleteResource(java.util.List)
	 */
	@Override
	public void deleteResource(List<ResourceModel> resources) {
		if(CollectionUtils.isNotEmpty(resources)){
			for (ResourceModel resourceModel : resources) {
				resourceMongo.delete(resourceModel);
			}
		}
	}

	/**
	 * 获取资源对象
	 * @param resource
	 * @param orderColumn
	 * @param start
	 * @param limit
	 * @param startTime
	 * @param endTime
	 * @return
	 * @see com.yryz.quanhu.resource.service.ResourceService#getResources(com.yryz.quanhu.resource.entity.ResourceModel, java.lang.String, int, int, java.lang.String, java.lang.String)
	 */
	@Override
	public List<ResourceModel> getResources(ResourceModel resource, String orderColumn, int start, int limit,
			String startTime, String endTime) {
		return resourceMongo.getList(resource, orderColumn, startTime, endTime, start, limit);
	}

	/**
	 * 批量获取资源信息
	 * @param resourceIds
	 * @return
	 * @see com.yryz.quanhu.resource.service.ResourceService#getResources(java.util.Set)
	 */
	@Override
	public Map<String, ResourceModel> getResources(Set<String> resourceIds) {
		Map<String, ResourceModel> map = new HashMap<>();
		if(CollectionUtils.isNotEmpty(resourceIds)){
			for (String resourceId : resourceIds) {
				ResourceModel resource = resourceMongo.get(resourceId);
				if(resource != null){
					map.put(resourceId, resource);
				}
			}
		}
		return map;
	}

	/**
	 * 单一获取资源信息
	 * @param resourceId
	 * @return
	 * @see com.yryz.quanhu.resource.service.ResourceService#getResource(java.lang.String)
	 */
	@Override
	public ResourceModel getResource(String resourceId) {
		ResourceModel resourceModel = resourceMongo.get(resourceId);
		return resourceModel;
	}

	/**
	 * 创建首页缓存
	 * @see com.yryz.quanhu.resource.service.ResourceService#createRecommend()
	 */
	@Override
	public void createRecommend() {
		int pageSize = 10;
		int recommendPageSize = 6;
		int nonePageSize = 4;
		int step = 0;
		
		List<ResourceModel> recommends = getRecommendResource(step * recommendPageSize, 10);
	}
	
	
	
	/**
	 * 返回推荐资源
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<ResourceModel> getRecommendResource(int start , int limit){
		ResourceModel resourceModel = new ResourceModel();
		resourceModel.setRecommend(ResourceEnum.RECOMMEND_TYPE_TRUE);
		resourceModel.setTalentType(ResourceEnum.TALENT_TYPE_TRUE);
		List<ResourceModel> list = resourceMongo.getList(resourceModel, "sort", null, null, start, limit);
		return list;
	}
	
	public static <T> List<T> addList(List<T> from ,List<T> to ,int index, int size){
		if(from == null || CollectionUtils.isEmpty(to)){
			return from;
		}
		for (int i = index; i < (index + size); i++) {
			if(to.size() >= (i +1)){
				from.add(to.get(i));
			}
		}
		return from;
	}
	
	/**
	 * 返回非推荐资源
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<ResourceModel> getNoneRecommendResource(int start , int limit){
		ResourceModel resourceModel = new ResourceModel();
		resourceModel.setRecommend(ResourceEnum.RECOMMEND_TYPE_FALSE);
		resourceModel.setTalentType(ResourceEnum.TALENT_TYPE_TRUE);
		List<ResourceModel> list = resourceMongo.getList(resourceModel, "heat", null, null, start, limit);
		return list;
	}
	

	/**
	 * APP首页推荐
	 * @param start
	 * @param limit
	 * @return
	 * @see com.yryz.quanhu.resource.service.ResourceService#appRecommend(int, int)
	 */
	@Override
	public List<ResourceVo> appRecommend(int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
