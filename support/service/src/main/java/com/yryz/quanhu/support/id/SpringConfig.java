package com.yryz.quanhu.support.id;

import com.yryz.quanhu.support.id.service.impl.DefaultUidServiceImpl;
import com.yryz.quanhu.support.id.service.impl.DisposableWorkerIdAssigner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/1/19
 * @description
 */
@Configuration
public class SpringConfig {
    @Value("${uid.timeBits}")
    private Integer timeBits;

    @Value("${uid.workerBits}")
    private Integer workerBits;

    @Value("${uid.seqBits}")
    private Integer seqBits;

    @Value("${uid.epochStr}")
    private String epochStr;


    @Bean
    ThreadPoolTaskExecutor expandKeyExecutor() {
        ThreadPoolTaskExecutor expandKeyExecutor = new ThreadPoolTaskExecutor();
        expandKeyExecutor.setCorePoolSize(2);
        expandKeyExecutor.setMaxPoolSize(2);
        expandKeyExecutor.setQueueCapacity(5);
        return expandKeyExecutor;
    }

    @Bean
    ThreadPoolTaskExecutor loadPrepBuffExecutor() {
        ThreadPoolTaskExecutor loadPrepBuffExecutor = new ThreadPoolTaskExecutor();
        loadPrepBuffExecutor.setCorePoolSize(3);
        loadPrepBuffExecutor.setMaxPoolSize(3);
        loadPrepBuffExecutor.setQueueCapacity(5);
        return loadPrepBuffExecutor;
    }

    @Bean
    DisposableWorkerIdAssigner disposableWorkerIdAssigner() {
        return new DisposableWorkerIdAssigner();
    }

    @Bean
    DefaultUidServiceImpl defaultUidGenerator() {
        DefaultUidServiceImpl defaultUidGenerator = new DefaultUidServiceImpl();
        defaultUidGenerator.setWorkerIdAssigner(disposableWorkerIdAssigner());
        defaultUidGenerator.setTimeBits(timeBits);
        defaultUidGenerator.setWorkerBits(workerBits);
        defaultUidGenerator.setSeqBits(seqBits);
        defaultUidGenerator.setEpochStr(epochStr);
        return defaultUidGenerator;
    }
}
