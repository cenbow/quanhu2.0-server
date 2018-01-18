package com.yryz.quanhu.dymaic.canal.service;

import java.net.InetSocketAddress;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yryz.quanhu.dymaic.canal.entity.CanalMsgContent;

@Component
public class SimpleCanalService extends AbstractCanalService {
	private SimpleCanalService client;
	@Value("${canal.host}")
	private String canalHost;
	@Value("${canal.port}")
	private int canalPort;
	@Value("${canal.instance}")
	private String canalInstance;
	
	@Resource
	private CanalMsgHandler canalMsgHandler;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	public SimpleCanalService(){
		
	}
	
	public void startup() {
		// 根据ip，直接创建链接，无HA的功能
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

	@Override
	protected void processEntry(List<Entry> entrys,long batchId) {
		List<CanalMsgContent> msgList=canalMsgHandler.convert(entrys);
		try {
			if(logger.isInfoEnabled()){
				logger.info(MAPPER.writeValueAsString(msgList));
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		if(!CollectionUtils.isEmpty(msgList)){
			for (int i = 0; i < msgList.size(); i++) {
				CanalMsgContent msg=msgList.get(i);
				canalMsgHandler.sendMq(msg);
			}
		}
	}

	private void init(){
		setDestination(canalInstance);
		client=this;
	}
}
