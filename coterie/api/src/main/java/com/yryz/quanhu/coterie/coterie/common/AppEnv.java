package com.yryz.quanhu.coterie.coterie.common;

import java.io.Serializable;

/**
 * 应用环境配置信息
 * 
 * @author lsn
 *
 */
public class AppEnv implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6172929865310347199L;
	
	/**
	 * 运行环境
	 */
	private boolean dev = false;
	
	/**
	 * 应用名称 区别于模块名
	 */
	private String appName;
	
	/**
	 * 应用中文名称
	 */
	private String appChineseName;
	
	/**
	 * 数据库名称
	 */
	private String dbName;
	
	/**
	 * 表前缀
	 */
	private String tablePrefix;
	
	private String cirleKey;
	
	private String cirleToken;

	public boolean isDev() {
		return dev;
	}

	public void setDev(boolean dev) {
		this.dev = dev;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getTablePrefix() {
		return tablePrefix;
	}

	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}

	public String getCirleKey() {
		return cirleKey;
	}

	public void setCirleKey(String cirleKey) {
		this.cirleKey = cirleKey;
	}

	public String getCirleToken() {
		return cirleToken;
	}

	public void setCirleToken(String cirleToken) {
		this.cirleToken = cirleToken;
	}

	public String getAppChineseName() {
		return appChineseName;
	}

	public void setAppChineseName(String appChineseName) {
		this.appChineseName = appChineseName;
	}
	
}
