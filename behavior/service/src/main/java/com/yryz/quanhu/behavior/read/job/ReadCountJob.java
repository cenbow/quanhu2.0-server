package com.yryz.quanhu.behavior.read.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.yryz.quanhu.behavior.read.service.ReadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author wangheng
 * @ClassName: ReadCountJob
 * @Description:
 * @date 2017年4月19日 下午12:33:50
 */
public class ReadCountJob implements SimpleJob {

    private Logger logger = LoggerFactory.getLogger(ReadCountJob.class);

    @Autowired
    public ReadService readService;


    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("开始执行-更新阅读数JOB");
        try {
            readService.excuteViewCountJob();
        } catch (Exception e) {
            logger.error("更新阅读数JOB 异常：{}", e.getMessage());
        }
        logger.info("执行完成-更新阅读数JOB");
    }

}
