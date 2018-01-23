package com.yryz.quanhu.dymaic.canal.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;

//@Component
public class ClusterCanalService extends AbstractCanalService {
	protected final Logger logger = LoggerFactory.getLogger(ClusterCanalService.class);
	private ClusterCanalService client;
	
	@Value("${canal.zookeeper.host}")
	private String canalHost;
	
	@Value("${canal.zookeeper.port}")
	private Integer canalPort;
	
	//canal服务端实例
	@Value("${canal.instance}")
	private String canalInstance;
	
	public void startup() {
		if(StringUtils.isEmpty(canalHost) || canalPort==null){
			logger.warn("-----------canal zookeeper Cluster------------");
			logger.warn("--canal zookeeper host or port not found--");
			logger.warn("------------------------------------------");
			return;
		}
		init();
		String destination = canalInstance;
		// 基于zookeeper动态获取canal server的地址，建立链接，其中一台server发生crash，可以支持failover
		CanalConnector connector = CanalConnectors.newClusterConnector(canalHost+":"+canalPort, destination, "", "");

		client.setConnector(connector);
		client.start();

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
		setDestination(canalInstance);
		client=this;
	}
}
