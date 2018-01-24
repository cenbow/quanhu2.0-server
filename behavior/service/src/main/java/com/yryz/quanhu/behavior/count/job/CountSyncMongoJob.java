package com.yryz.quanhu.behavior.count.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.yryz.quanhu.behavior.count.service.CountService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: liupan
 * @Path: com.yryz.quanhu.behavior.count.job
 * @Desc:
 * @Date: 2018/1/24.
 */
public class CountSyncMongoJob implements SimpleJob {
    private static final Log logger = LogFactory.getLog(CountSyncMongoJob.class);

    @Autowired
    private CountService countService;

    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("elastic job CountSyncMongoJob start..." + shardingContext.getJobName());
        countService.excuteCountSyncMongoJob();
    }
}
