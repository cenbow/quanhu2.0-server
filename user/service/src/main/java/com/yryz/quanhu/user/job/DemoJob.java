package com.yryz.quanhu.user.job;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * @author xiepeng
 * @version 1.0
 * @data 2018/1/17 0017 08
 */
public class DemoJob implements SimpleJob{
    private static final Log logger = LogFactory.getLog(DemoJob.class);
    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("elastic job demo..." + shardingContext.getJobName());

        switch (shardingContext.getShardingItem()) {
            case 0:
                // do something by sharding item 0
                break;
            case 1:
                // do something by sharding item 1
                break;
            default:
                // do something by sharding default
                break;
        }
    }
}