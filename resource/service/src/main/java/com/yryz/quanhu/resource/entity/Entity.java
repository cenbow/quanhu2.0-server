/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月16日
 * Id: Entity.java, 2018年1月16日 下午2:02:31 yehao
 */
package com.yryz.quanhu.resource.entity;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月16日 下午2:02:31
 * @Description 实体类扩展类
 */
public abstract class Entity {
	
	/**
	 * 资源分类搜索
	 */
	private String resourceType;

	/**
	 * @return the resourceType
	 */
	public String getResourceType() {
		return resourceType;
	}

	/**
	 * @param resourceType the resourceType to set
	 */
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	
}
