package com.yryz.quanhu.dymaic.canal;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.yryz.quanhu.dymaic.canal.service.CanalService;

@Component
public class CanalStartupRunner implements CommandLineRunner {
	private static Logger logger = LoggerFactory.getLogger(CanalStartupRunner.class);
	@Resource
	private CanalService canalService;
	
	@Override
	public void run(String... args) throws Exception {
		logger.info("CanalStartupRunner starting");
		canalService.startup();
	}

}
