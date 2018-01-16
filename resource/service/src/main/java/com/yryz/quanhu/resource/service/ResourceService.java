/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月16日
 * Id: ResourceService.java, 2018年1月16日 下午4:25:27 yehao
 */
package com.yryz.quanhu.resource.service;

import java.util.List;

import com.yryz.quanhu.resource.entity.ResourceModel;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月16日 下午4:25:27
 * @Description 资源管理服务接口声明
 */
public interface ResourceService {
	
	/**
	 * 创建/更新核心资源信息
	 * @param resources 资源信息实体
	 */
	public void commitResource(List<ResourceModel> resources);
	
	/**
	 * 更新用户资源
	 * @param resources
	 */
	public void updateResource(List<ResourceModel> resources);
	
	/**
	 * 删除资源
	 * @param resources
	 */
	public void deleteResource(List<ResourceModel> resources);
	
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
	public List<ResourceModel> getResources(ResourceModel resource , String orderColumn , long start , long limit ,String startTime ,String endTime);

}
