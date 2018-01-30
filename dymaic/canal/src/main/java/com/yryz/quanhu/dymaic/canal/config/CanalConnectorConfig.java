package com.yryz.quanhu.dymaic.canal.config;

import java.net.InetSocketAddress;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;

@Configuration
@ConditionalOnClass({ CanalConnector.class, CanalConnectors.class })
@EnableConfigurationProperties(CanalProperties.class)
public class CanalConnectorConfig {
	private final CanalProperties properties;
	public CanalConnectorConfig(CanalProperties properties){
		this.properties=properties;
	}
	
	@Bean
	@ConditionalOnProperty(prefix = "canal", name = {"host","port","instance"}, matchIfMissing = false)
	public CanalConnector singleConnector(){
		CanalConnector connector = CanalConnectors
				.newSingleConnector(new InetSocketAddress(properties.getHost(), properties.getPort()), properties.getInstance(), "", "");
		return connector;
	}
	
	@Bean
	@ConditionalOnProperty(prefix = "canal", name = {"zookeeperHost","zookeeperPort","instance"}, matchIfMissing = false)
	public CanalConnector clusterConnector(){
		CanalConnector connector = CanalConnectors.newClusterConnector(properties.getZookeeperHost()+":"+properties.getZookeeperPort(), properties.getInstance(), "", "");
		return connector;
	}
}