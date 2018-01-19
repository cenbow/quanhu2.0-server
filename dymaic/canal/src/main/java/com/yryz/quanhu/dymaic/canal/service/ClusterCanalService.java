package com.yryz.quanhu.dymaic.canal.service;

import org.springframework.beans.factory.annotation.Value;
import org.apache.commons.lang.exception.ExceptionUtils;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;

//@Component
public class ClusterCanalService extends AbstractCanalService {
	private ClusterCanalService client;
	
	//canal.host=canal Zookeeper host
	@Value("${canal.host}")
	private String canalHost;
	
	//canal.host=canal Zookeeper port
	@Value("${canal.port}")
	private int canalPort;
	
	//canal服务端实例
	@Value("${canal.instance}")
	private String canalInstance;
	
	public void startup() {
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
