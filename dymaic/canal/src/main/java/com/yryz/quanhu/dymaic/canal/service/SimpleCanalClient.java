package com.yryz.quanhu.dymaic.canal.service;

import java.net.InetSocketAddress;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;

@Component
public class SimpleCanalClient extends AbstractCanalClient {
	@Value("${canal.host}")
	private String canalHost;

	@Value("${canal.port}")
	private int canalPort;

	@Value("${canal.instance}")
	private String canalInstance;

	public SimpleCanalClient(String destination) {
		super(destination);
	}

	public void startup() {
		// 根据ip，直接创建链接，无HA的功能
		String destination = canalInstance;
		CanalConnector connector = CanalConnectors
				.newSingleConnector(new InetSocketAddress(canalHost, canalPort), destination, "", "");

		final SimpleCanalClient SimpleClient = new SimpleCanalClient(destination);
		SimpleClient.setConnector(connector);
		SimpleClient.start();
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					logger.info("## stop the canal client");
					SimpleClient.stop();
				} catch (Throwable e) {
					logger.warn("##something goes wrong when stopping canal:\n{}", ExceptionUtils.getFullStackTrace(e));
				} finally {
					logger.info("## canal client is down.");
				}
			}
		});
	}

}
