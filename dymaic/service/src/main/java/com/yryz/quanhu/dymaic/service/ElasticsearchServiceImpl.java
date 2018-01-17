package com.yryz.quanhu.dymaic.service;


import java.util.Optional;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.quanhu.dymaic.dao.elasticsearch.UserRepository;
import com.yryz.quanhu.dymaic.entity.User;

@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {
	@Resource
	private UserRepository userRepository;
	
	@Override
	public String findUserName(Long userId) {
		Optional<User> optUser=userRepository.findById(100L);
		if(optUser.isPresent()){
			System.out.println(optUser.get());
			return optUser.get().getRealName();
		}
		return null;
	}

}
