/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月19日
 * Id: ResourceConvertService.java, 2018年1月19日 下午5:15:00 yehao
 */
package com.yryz.quanhu.resource.service;

import java.util.List;

import com.yryz.quanhu.resource.vo.ResourceVo;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月19日 下午5:15:00
 * @Description 添加用户信息
 */
public interface ResourceConvertService {
	
	
	/**
	 * 批量添加
	 * @param list
	 * @return
	 */
	public List<ResourceVo> addUser(List<ResourceVo> list);
	
	/**
	 * 单个添加
	 * @param resource
	 * @return
	 */
	public ResourceVo addUser(ResourceVo resource);
	
	/**
	 * 添加基础统计数据
	 * @param resource
	 * @return
	 */
	public ResourceVo addBatch(ResourceVo resource);
	
	/**
	 * 批量获取
	 * @param list
	 * @return
	 */
	public List<ResourceVo> addBatch(List<ResourceVo> list);
	
	/**
	 * 添加统计数（批量）
	 * @param list
	 * @return
	 */
	public List<ResourceVo> addCount(List<ResourceVo> list);
	
	/**
	 * 添加统计数
	 * @param resourceVo
	 * @return
	 */
	public ResourceVo addCount(ResourceVo resourceVo);
	
	/**
	 * 添加私圈(批量)
	 * @param list
	 * @return
	 */
	public List<ResourceVo> addCoterie(List<ResourceVo> list);
	
	/**
	 * 添加私圈
	 * @param resourceVo
	 * @return
	 */
	public ResourceVo addCoterie(ResourceVo resourceVo);

}
