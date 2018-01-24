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
import com.yryz.quanhu.resource.service.ResourceService;

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
				ResourceModel resource = resourceMongo.findById(resourceId);
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
		ResourceModel resourceModel = resourceMongo.findById(resourceId);
		return resourceModel;
	}
	
	

}
