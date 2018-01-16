package com.yryz.common.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleJobTest implements SimpleJob {

	private static final Logger logger = LoggerFactory.getLogger(SimpleJobTest.class);


	@Override
	public void execute(ShardingContext shardingContext) {
		switch (shardingContext.getShardingItem()) {
		case 0:
			// 目前不分片
			logger.info("**********EsCustJob execute**********");
			System.out.println("ssdsfdf");
			break;
		}
	}

}
