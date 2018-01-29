/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月29日
 * Id: ResourceAttenuationJob.java, 2018年1月29日 下午2:04:12 yehao
 */
package com.yryz.quanhu.resource.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.yryz.quanhu.resource.hotspot.service.CalculationService;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月29日 下午2:04:12
 * @Description 资源热度衰减的JOB
 */
public class ResourceAttenuationJob implements SimpleJob {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	CalculationService calculationService;

	/**
	 * @param arg0
	 * @see com.dangdang.ddframe.job.api.simple.SimpleJob#execute(com.dangdang.ddframe.job.api.ShardingContext)
	 */
	@Override
	public void execute(ShardingContext arg0) {
		logger.info("execute resource attenuation job ...");
		try {
			calculationService.attenuation();
		} catch (Exception e) {
			logger.info("执行热度衰减失败.",e);
		}
	}

}
