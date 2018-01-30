package com.yryz.quanhu.dymaic.canal.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "canal")
public class CanalProperties {
	private String host;
	private Integer port;
	private String zookeeperHost;
	private Integer zookeeperPort;
	private String instance;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public String getZookeeperHost() {
		return zookeeperHost;
	}

	public void setZookeeperHost(String zookeeperHost) {
		this.zookeeperHost = zookeeperHost;
	}

	public Integer getZookeeperPort() {
		return zookeeperPort;
	}

	public void setZookeeperPort(Integer zookeeperPort) {
		this.zookeeperPort = zookeeperPort;
	}

}
