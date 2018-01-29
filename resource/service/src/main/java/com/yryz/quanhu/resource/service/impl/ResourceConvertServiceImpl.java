/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月19日
 * Id: ResourceConvertServiceImpl.java, 2018年1月19日 下午5:15:55 yehao
 */
package com.yryz.quanhu.resource.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.resource.service.ResourceConvertService;
import com.yryz.quanhu.resource.vo.ResourceVo;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserBaseInfoVO;
import com.yryz.quanhu.user.vo.UserSimpleVO;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月19日 下午5:15:55
 * @Description 资源数据字段填充
 */
@Service
public class ResourceConvertServiceImpl implements ResourceConvertService {
	
	@Reference(check=false)
	private UserApi userApi;
	
	@Reference
	private CountApi countApi;

	/**
	 * 填充用户对象
	 * @param list
	 * @return
	 * @see com.yryz.quanhu.resource.service.ResourceConvertService#addUser(java.util.List)
	 */
	@Override
	public List<ResourceVo> addUser(List<ResourceVo> list) {
		if(CollectionUtils.isNotEmpty(list)){
			Set<String> userIds = new HashSet<>();
			for (ResourceVo resourceVo : list) {
				userIds.add(resourceVo.getUserId().toString());
			}
			Response<Map<String,UserSimpleVO>> response = userApi.getUserSimple(userIds);
			if(response.success()){
				Map<String,UserSimpleVO> map = response.getData();
				for (ResourceVo resourceVo : list) {
					resourceVo.setUser(map.get(resourceVo.getUserId().toString()));
				}
			}
		}
		return list;
	}

	/**
	 * 添加用户
	 * @param resource
	 * @return
	 * @see com.yryz.quanhu.resource.service.ResourceConvertService#addUser(com.yryz.quanhu.resource.vo.ResourceVo)
	 */
	@Override
	public ResourceVo addUser(ResourceVo resource) {
		if(resource != null){
			List<ResourceVo> list = new ArrayList<>();
			list.add(resource);
			list = addUser(list);
			if(CollectionUtils.isNotEmpty(list)){
				return list.get(0);
			}
		}
		return null;
	}

}
