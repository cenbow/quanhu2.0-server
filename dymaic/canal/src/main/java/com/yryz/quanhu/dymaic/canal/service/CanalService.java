package com.yryz.quanhu.dymaic.canal.service;

import javax.annotation.Resource;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.otter.canal.client.CanalConnector;
import com.yryz.quanhu.dymaic.canal.config.CanalProperties;

@Component
public class CanalService extends AbstractCanalService {
	private CanalService client;
	@Resource
	private CanalConnector connector;
	@Autowired
	private CanalProperties properties;
	
	// 根据ip，直接创建链接，无HA的功能
	public void startup() {
		if(properties==null){
			logger.warn("-------------canal 配置信息缺失--------------");
			logger.warn("-------see com.yryz.quanhu.dymaic.canal.config.ConnectorProperties -------");
			logger.warn("------------------------------------------");
			return;
		}
		init();
		this.setConnector(connector);
		this.start();
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					logger.info("## stop the canal client");
					client.stop();
				} catch (Throwable e) {
					logger.warn("##something goes wrong when stopping canal:\n{}", ExceptionUtils.getFullStackTrace(e));
				} finally {
					logger.info("## canal client is down.");
				}
			}
		});
	}

	private void init(){
		setDestination(properties.getInstance());
		client=this;
	}
}
