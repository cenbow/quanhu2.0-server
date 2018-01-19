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

}
