package com.yryz.quanhu.dymaic.canal.job;

import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

public class EsSyncDiffJob  implements SimpleJob{
	private static final Log logger = LogFactory.getLog(EsSyncDiffJob.class);
	@Resource
	private DiffExecutor diffExecutor;
	
    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("es data diff job:" + shardingContext.getJobName());
        switch (shardingContext.getShardingItem()) {
            case 0:
            	diffExecutor.process();
        }
    }
    
}
