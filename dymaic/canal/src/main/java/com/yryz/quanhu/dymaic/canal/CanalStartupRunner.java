package com.yryz.quanhu.dymaic.canal;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.yryz.quanhu.dymaic.canal.service.ClusterCanalService;
import com.yryz.quanhu.dymaic.canal.service.SimpleCanalService;

@Component
public class CanalStartupRunner implements CommandLineRunner {
	private static Logger logger = LoggerFactory.getLogger(CanalStartupRunner.class);
//	@Resource
//	private SimpleCanalService simpleCanalService;
	@Resource
	private ClusterCanalService clusterCanalService;
	
	@Override
	public void run(String... args) throws Exception {
		logger.info("CanalStartupRunner starting");
//		simpleCanalService.startup();
		clusterCanalService.startup();
	}

}
