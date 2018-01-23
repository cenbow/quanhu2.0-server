package com.yryz.quanhu.dymaic.service;


import java.util.List;
import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.yryz.quanhu.dymaic.dao.elasticsearch.UserRepository;
import com.yryz.quanhu.dymaic.entity.UserInfo;
import com.yryz.quanhu.dymaic.vo.UserSimpleVO;

@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {
	@Resource
	private UserRepository userRepository;
	
	@Override
	public List<UserSimpleVO> searchUser(String keyWord,Integer page,Integer size) {
		List<UserInfo> list=userRepository.search(keyWord,page,size);
		List<UserSimpleVO> rstList=Lists.newArrayList();
		for (int i = 0; i < list.size(); i++) {
			UserInfo info=list.get(i);
			UserSimpleVO vo=new UserSimpleVO();
			BeanUtils.copyProperties(info, vo);
			rstList.add(vo);
		}
		return rstList;
	}

}
