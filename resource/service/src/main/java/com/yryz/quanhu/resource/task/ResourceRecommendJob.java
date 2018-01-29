/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月29日
 * Id: ResourceRecommendTask.java, 2018年1月29日 上午11:31:41 yehao
 */
package com.yryz.quanhu.resource.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.yryz.quanhu.resource.service.ResourceService;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月29日 上午11:31:41
 * @Description 资源缓存构建的JOB
 */
@Service
public class ResourceRecommendJob implements SimpleJob{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ResourceService resourceService;

	/**
	 * @param arg0
	 * @see com.dangdang.ddframe.job.api.simple.SimpleJob#execute(com.dangdang.ddframe.job.api.ShardingContext)
	 */
	@Override
	public void execute(ShardingContext arg0) {
		logger.info("execute resource recommend job ...");
		try {
			resourceService.createRecommend();
		} catch (Exception e) {
			logger.info("创建首页资源缓存失败." , e);
		}
	}

}
