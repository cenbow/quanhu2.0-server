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
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
import com.yryz.quanhu.coterie.coterie.service.CoterieApi;
import com.yryz.quanhu.coterie.coterie.vo.Coterie;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
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
	
	@Reference
	private UserApi userApi;
	
	@Reference
	private CountApi countApi;
	
	@Reference
	private CoterieApi coterieApi;
	
	public List<ResourceVo> addBatch(List<ResourceVo> list){
		list = addUser(list);
		list = addCount(list);
		list = addCoterie(list);
		return list;
	}

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
	
	public List<ResourceVo> addCount(List<ResourceVo> list){
		if(CollectionUtils.isNotEmpty(list)){
			for (ResourceVo resourceVo : list) {
				Map<String, Long> map = countApi.getCount(BehaviorEnum.Read.getCode(), Long.parseLong(resourceVo.getResourceId()), null).getData();
				if(map != null && map.containsKey(BehaviorEnum.Read.getCode())){
					Long readNum = map.get(BehaviorEnum.Read.getCode());
					resourceVo.setReadNum(readNum);
				}
			}
		}
		return list;
	}
	
	public ResourceVo addCount(ResourceVo resourceVo){
		Map<String, Long> map = countApi.getCount(BehaviorEnum.Read.getCode(), Long.parseLong(resourceVo.getResourceId()), null).getData();
		if(map != null && map.containsKey(BehaviorEnum.Read.getCode())){
			Long readNum = map.get(BehaviorEnum.Read.getCode());
			resourceVo.setReadNum(readNum);
		}
		return resourceVo;
	}
	
	/**
	 * 添加私圈信息
	 * @param list
	 * @return
	 */
	public List<ResourceVo> addCoterie(List<ResourceVo> list){
		if(CollectionUtils.isNotEmpty(list)){
			for (ResourceVo resourceVo : list) {
				if(StringUtils.isNotEmpty(resourceVo.getCoterieId())){
					CoterieInfo coterieInfo = coterieApi.queryCoterieInfo(Long.parseLong(resourceVo.getCoterieId())).getData();
					resourceVo.setCoterie(coterieInfo);
				}
			}
		}
		return list;
	}
	
	/**
	 * 获取私圈信息
	 * @param resourceVo
	 * @return
	 */
	public ResourceVo addCoterie(ResourceVo resourceVo){
		if(StringUtils.isNotEmpty(resourceVo.getCoterieId())){
			CoterieInfo coterieInfo = coterieApi.queryCoterieInfo(Long.parseLong(resourceVo.getCoterieId())).getData();
			resourceVo.setCoterie(coterieInfo);
		}
		return resourceVo;
	}

	/**
	 * @param resource
	 * @return
	 * @see com.yryz.quanhu.resource.service.ResourceConvertService#addBatch(com.yryz.quanhu.resource.vo.ResourceVo)
	 */
	@Override
	public ResourceVo addBatch(ResourceVo resource) {
		resource = addUser(resource);
		resource = addCoterie(resource);
		resource = addCount(resource);
		return resource;
	}

}
