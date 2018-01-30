/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月16日
 * Id: ResourceServiceImpl.java, 2018年1月16日 下午4:25:44 yehao
 */
package com.yryz.quanhu.resource.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yryz.quanhu.resource.dao.canal.ResourceCanalDao;
import com.yryz.quanhu.resource.dao.mongo.ResourceMongo;
import com.yryz.quanhu.resource.dao.redis.ResourceRedis;
import com.yryz.quanhu.resource.entity.ResourceModel;
import com.yryz.quanhu.resource.enums.ResourceEnum;
import com.yryz.quanhu.resource.hotspot.service.HotspotService;
import com.yryz.quanhu.resource.service.ResourceService;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月16日 下午4:25:44
 * @Description 资源管理业务逻辑实现
 */
@Service
public class ResourceServiceImpl implements ResourceService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ResourceMongo resourceMongo;
	
	@Autowired
	private ResourceRedis resourceRedis;
	
	@Autowired
	HotspotService hotspotService;
	
	@Autowired
	ResourceCanalDao resourceCanalDao;
	
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
					updateCache(resourceModel.getResourceId());
				} else {
					resourceMongo.save(resourceModel);
					//创建资源时候要创建对应的热度对象
					hotspotService.saveHeat("1", resourceModel.getResourceId() , resourceModel.getTalentType());
					resourceCanalDao.sendToCannel(resourceModel.getResourceId(), 0L);
					updateCache(resourceModel.getResourceId());
				}
			}
		}
	}
	
	/**
	 * 更新redis缓存
	 * @param resourceId
	 */
	public void updateCache(String resourceId){
		ResourceModel model = resourceMongo.get(resourceId);
		if(model != null){
			resourceRedis.saveResource(model);
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
				updateCache(resourceModel.getResourceId());
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
				updateCache(resourceModel.getResourceId());
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
		logger.info("start createRecommend ...");
		resourceRedis.clearAppRecommend();
		ResourcePageNode pageNode = new ResourcePageNode();
		for (int i = 0; i < 50; i++) {
			logger.info("current pageNo : " + pageNode.getCurrentPageNo());
			if(pageNode.getCurrentPageNo() > 40 ){
				logger.info("PageNo is end ...");
				break;
			}
			if(pageNode.getRecommendPageSize() == 0 && pageNode.getNonePageSize() == 0 ){
				logger.info("raws is empty");
				break;
			}
			pageNode = pushResourceByPageNode(pageNode);
		}
		logger.info("end createRecommend ...");
	}
	
	/**
	 * 按照Page组织列表数据，并存入redis
	 * @param resourcePageNode
	 * @return
	 */
	public ResourcePageNode pushResourceByPageNode(ResourcePageNode resourcePageNode){
		List<ResourceModel> total = new ArrayList<>();
		List<ResourceModel> recommends = new ArrayList<>();
		List<ResourceModel> nonerecommends = new ArrayList<>();
		if(resourcePageNode.getRecommendPageSize() > 0 ){
			recommends = getRecommendResource((
					resourcePageNode.getCurrentPageNo() -1) * resourcePageNode.getRecommendPageSize(), 
					resourcePageNode.getRecommendPageSize());
		}
		if(CollectionUtils.isEmpty(recommends)){
			resourcePageNode.setRecommendPageSize(0);
		}
		
		if(resourcePageNode.getNonePageSize() > 0 ){
			nonerecommends = getNoneRecommendResource(
					resourcePageNode.getCurrentStep(),resourcePageNode.getNonePageSize());
		}
		if(CollectionUtils.isEmpty(nonerecommends)){
			resourcePageNode.setNonePageSize(0);
		}
		addList(total, recommends, 0, 3);
		addList(total, nonerecommends, 0, 2);
		addList(total, recommends, 3, 3);
		addList(total, nonerecommends, 2, 2);
		resourcePageNode.addPageNo();
		resourcePageNode.addStep(4);
		if(total.size() < 10){
			int size = 10 - total.size();
			nonerecommends = getNoneRecommendResource(
					resourcePageNode.getCurrentStep(), size);
			addList(total, nonerecommends, 0, nonerecommends.size());
			resourcePageNode.addStep(size);
		}
		//List结果插入redis
		insertIntoRedis(total);
		
		return resourcePageNode;
	}
	
	private void insertIntoRedis(List<ResourceModel> resourceModels){
		List<String> resourceIds = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(resourceModels)){
			for (ResourceModel resourceModel : resourceModels) {
				resourceIds.add(resourceModel.getResourceId());
			}
		}
		resourceRedis.addAppRecommend(resourceIds);
	}
	
	public class ResourcePageNode {
		
		private int currentPageNo;
		private int currentStep;
		private int recommendPageSize;
		private int nonePageSize;
		/**
		 * 
		 * @exception 
		 */
		public ResourcePageNode() {
			this.currentPageNo = 1;
			this.currentStep = 0;
			this.recommendPageSize = 6;
			this.nonePageSize = 4;
		}
		
		public void addStep(int step){
			this.currentStep += step;
		}
		
		public void addPageNo(){
			this.currentPageNo += 1;
		}

		/**
		 * @return the currentPageNo
		 */
		public int getCurrentPageNo() {
			return currentPageNo;
		}

		/**
		 * @return the currentStep
		 */
		public int getCurrentStep() {
			return currentStep;
		}

		/**
		 * @return the recommendPageSize
		 */
		public int getRecommendPageSize() {
			return recommendPageSize;
		}

		/**
		 * @return the nonePageSize
		 */
		public int getNonePageSize() {
			return nonePageSize;
		}

		/**
		 * @param recommendPageSize the recommendPageSize to set
		 */
		public void setRecommendPageSize(int recommendPageSize) {
			this.recommendPageSize = recommendPageSize;
		}

		/**
		 * @param nonePageSize the nonePageSize to set
		 */
		public void setNonePageSize(int nonePageSize) {
			this.nonePageSize = nonePageSize;
		}
		
	}
	
	/**
	 * 添加列表集合
	 * @param from
	 * @param to
	 * @param index
	 * @param size
	 * @return
	 */
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
	 * 返回推荐资源
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<ResourceModel> getRecommendResource(int start , int limit){
		ResourceModel resourceModel = new ResourceModel();
		resourceModel.setRecommend(ResourceEnum.RECOMMEND_TYPE_TRUE);
//		resourceModel.setTalentType(ResourceEnum.TALENT_TYPE_TRUE);
		List<ResourceModel> list = resourceMongo.getList(resourceModel, "sort", null, null, start, limit);
		return list;
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
		resourceModel.setPublicState(ResourceEnum.PUBLIC_STATE_TRUE);
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
	public List<ResourceModel> appRecommend(int start, int limit) {
		List<String> resourceIds = resourceRedis.getAppRecommendList(start, limit);
		List<ResourceModel> resources = new ArrayList<>();
		for (String resourceId : resourceIds) {
			resources.add(get(resourceId));
		}
		return resources;
	}
	
	/**
	 * 获取资源详情，优先缓存，缓存不在，从mongo中取
	 * @param resourceId
	 * @return
	 */
	public ResourceModel get(String resourceId){
		ResourceModel resourceModel = resourceRedis.getResource(resourceId);
		if(resourceModel == null){
			resourceModel = resourceMongo.get(resourceId);
			if(resourceModel != null){
				resourceRedis.saveResource(resourceModel);
			}
		}
		return resourceModel;
	}
	
}
