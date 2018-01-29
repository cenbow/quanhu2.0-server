package com.yryz.quanhu.resource.questionsAnswers.service.impl;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.yryz.quanhu.resource.questionsAnswers.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wangheng
 * @ClassName: ContentSyncHeat
 * @Description: 内容 heat 热度值 job [包括 ： release 模块、ask 问答模块 、 topic 话题模块、其他特有模块]
 * @date 2017年4月19日 下午12:33:50
 */
@Service
public class QuestioninValidRefundJob implements SimpleJob {

	private Logger logger = LoggerFactory.getLogger(QuestioninValidRefundJob.class);

	@Autowired
	private QuestionService questionService;

	@Override
	public void execute(ShardingContext shardingContext) {
		logger.info("====开始执行-失效退款");
		doExecute();
	}


	private void doExecute() {
		try {
			questionService.updateInValidQuestionRefund();
		} catch (Exception e) {
			logger.error("失效退款 的 JOb 异常：{}", e.getMessage());
		}
	}
}
