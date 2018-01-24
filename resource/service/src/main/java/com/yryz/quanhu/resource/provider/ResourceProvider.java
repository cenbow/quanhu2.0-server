/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月16日
 * Id: ResourceApiImpl.java, 2018年1月16日 下午4:21:51 yehao
 */
package com.yryz.quanhu.resource.provider;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.constant.CommonConstants;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.resource.api.ResourceApi;
import com.yryz.quanhu.resource.entity.ResourceModel;
import com.yryz.quanhu.resource.service.ResourceConvertService;
import com.yryz.quanhu.resource.service.ResourceService;
import com.yryz.quanhu.resource.vo.ResourceVo;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月16日 下午4:21:51
 * @Description 资源服务实现类
 */
@Service(interfaceClass = ResourceApi.class)
public class ResourceProvider implements ResourceApi {
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private ResourceConvertService resourceConvertService;
	
	/**
	 * 创建/更新核心资源信息
	 * @param resources 资源信息实体
	 */
	public Response<?> commitResource(List<ResourceVo> resources){
		resourceService.commitResource(GsonUtils.parseList(resources, ResourceModel.class));
		return ResponseUtils.returnSuccess();
	}
	
	/**
	 * 更新用户资源
	 * @param resources
	 */
	public Response<?> updateResource(List<ResourceVo> resources){
		resourceService.updateResource(GsonUtils.parseList(resources, ResourceModel.class));
		return ResponseUtils.returnSuccess();
	}
	
	/**
	 * 删除资源
	 * @param resources
	 */
	public Response<?> deleteResource(List<ResourceVo> resources){
		resourceService.deleteResource(GsonUtils.parseList(resources, ResourceModel.class));
		return ResponseUtils.returnSuccess();
	}
	
	/**
	 * 资源列表获取,如果有排序字段，默认按字段倒序排序
	 * @param resource 资源查询条件
	 * @param orderColumn 排序字段，resource的columnName，如果有多个值，以逗号分隔。默认倒序排序
	 * @param start 开始条目
	 * @param limit 结束条目
	 * @param startTime 开始时间，yyyy-MM-dd HH:mm:ss
	 * @param endTime 结束时间，yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public Response<List<ResourceVo>> getResources(ResourceVo resource , String orderColumn , int start , int limit ,String startTime ,String endTime){
		if(start < 0){
			start = 0;
		}
		if(limit <= 0){
			limit = CommonConstants.DEFAULT_SIZE;
		}
		List<ResourceModel> list = resourceService.getResources(GsonUtils.parseObj(resource, ResourceModel.class), orderColumn, start, limit, startTime, endTime);
		List<ResourceVo> listVo = GsonUtils.parseList(list, ResourceVo.class);
		listVo = resourceConvertService.addUser(listVo);
		return ResponseUtils.returnListSuccess(listVo);
	}

	/**
	 * 批量获取资源信息，通过ID列表
	 * @param resourceIds
	 * @return
	 * @see com.yryz.quanhu.resource.api.ResourceApi#getResourcesByIds(java.util.Set)
	 */
	@Override
	public Response<Map<String, ResourceVo>> getResourcesByIds(Set<String> resourceIds) {
		Map<String, ResourceModel> modelMap = resourceService.getResources(resourceIds);
		Map<String, ResourceVo> map = (Map<String, ResourceVo>) GsonUtils.parseMap(modelMap, ResourceVo.class);
		return ResponseUtils.returnObjectSuccess(map);
	}

	/**
	 * 单一ID获取资源
	 * @param resourceId
	 * @return
	 * @see com.yryz.quanhu.resource.api.ResourceApi#getResourcesById(java.lang.String)
	 */
	@Override
	public Response<ResourceVo> getResourcesById(String resourceId) {
		ResourceModel resource = resourceService.getResource(resourceId);
		ResourceVo resourceVo = resourceConvertService.addUser(GsonUtils.parseObj(resource, ResourceVo.class));
		return ResponseUtils.returnObjectSuccess(resourceVo);
	}

}
