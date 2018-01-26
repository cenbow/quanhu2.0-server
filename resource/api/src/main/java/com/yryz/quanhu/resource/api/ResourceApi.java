/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月16日
 * Id: ResourceApi.java, 2018年1月16日 下午2:11:19 yehao
 */
package com.yryz.quanhu.resource.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yryz.common.response.Response;
import com.yryz.quanhu.resource.vo.ResourceVo;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月16日 下午2:11:19
 * @Description 资源管理API
 */
public interface ResourceApi {
	
	/**
	 * 创建/更新核心资源信息
	 * @param resources 资源信息实体
	 */
	public Response<?> commitResource(List<ResourceVo> resources);
	
	/**
	 * 更新用户资源
	 * @param resources
	 */
	public Response<?> updateResource(List<ResourceVo> resources);
	
	/**
	 * 删除资源
	 * @param resources
	 */
	public Response<?> deleteResource(List<ResourceVo> resources);
	
	/**
	 * 删除资源
	 * @param resourceId
	 * @return
	 */
	public Response<?> deleteResourceById(Long resourceId);
	
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
	public Response<List<ResourceVo>> getResources(ResourceVo resource , String orderColumn , int start , int limit ,String startTime ,String endTime);
	
	/**
	 * 批量获取资源信息
	 * @param resourceIds
	 * @return
	 */
	public Response<Map<String, ResourceVo>> getResourcesByIds(Set<String> resourceIds);
	
	/**
	 * 获取资源信息(单个)
	 * @param resourceIds
	 * @return
	 */
	public Response<ResourceVo> getResourcesById(String resourceId);

}
