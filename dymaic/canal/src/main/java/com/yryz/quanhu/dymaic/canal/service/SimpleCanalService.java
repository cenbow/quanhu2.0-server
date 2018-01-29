package com.yryz.quanhu.dymaic.canal.service;

import java.net.InetSocketAddress;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;

//@Component
public class SimpleCanalService extends AbstractCanalService {
	private SimpleCanalService client;
	@Value("${canal.host}")
	private String canalHost;
	@Value("${canal.port}")
	private Integer canalPort;
	@Value("${canal.instance}")
	private String canalInstance;
	
	// 根据ip，直接创建链接，无HA的功能
	public void startup() {
		if(StringUtils.isEmpty(canalHost) || canalPort==null){
			logger.warn("-------------canal 直连--------------------");
			logger.warn("-------canal host or port not found-------");
			logger.warn("------------------------------------------");
			return;
		}
		init();
		String destination = canalInstance;
		CanalConnector connector = CanalConnectors
				.newSingleConnector(new InetSocketAddress(canalHost, canalPort), destination, "", "");

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
		setDestination(canalInstance);
		client=this;
	}
}
