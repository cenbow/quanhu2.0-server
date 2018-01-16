/*
 * AssertUtils.java
 * Copyright (c) 2011,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2011-3-9 Created
 */
package com.yryz.common.context;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 系统上下文信息,公共属性
 * </p>
 * 
 * @author kinjoYang
 * @version 1.0 2011-3-8
 * @since 1.0
 */
public class Context {

	/** 
	 * 默认日志名称 
	 */
	public final static String LOGGER_NAME = "SimpleLogger";
	
	/**
	 * 默认配置文件
	 */
	public final static String DEFAULT_COMPONENT = "component.properties";

	static Logger logger = LoggerFactory.getLogger(Context.class);

	private static volatile boolean isRun = Boolean.FALSE;

	/**
	 * UTF-8编码
	 */
	public static final String UTF8 = "UTF-8";

	/** 
	 * 默认主键名称
	 */
	public final static String KEY_NAME = "PID";

	/** 
	 * 上下文路径
	 */
	public static String realPath = "";
	/**
	 * webRoot真实路径
	 */
	public static String webRootRealPath ;
	
	@SuppressWarnings("unused")
	private static volatile int invacodeCount = 0;
	/**
	 * 时间格式
	 */
	public static final String dateType = "yyyy-MM-dd";

	/** 默认替代词*/
	public static final String SENSIT_REPLACE = "*";

	static Map<String, String> propertiesMap = new ConcurrentHashMap<String, String>();

	
	/**
	 * Construct
	 * 自定义初始化： 当自定义path不为空时，加载path路径下的配置文件
	 * 默认初始化：lazy初始化方式，默认读取相对路径下的component.properties
	 * @param path
	 */
	public Context(String path) {
		if (path != null &&  !"".equals(path.trim())) {
			Context.initProperties(path);
		}
	}
	
	/** 设置系统上下文路径 */
	public static void setRealPath(String path) {
		if ("".equals(realPath)) {
			realPath = path;
		}
	}

	public static String getRealPath() {
		return realPath;
	}

	public static void reloadCount() {
		invacodeCount = 0;
	}

	/** 初始化配置 */
	private static void initProperties(String propertieFile) {
		try {
			isRun = !isRun;
			Properties p = new Properties();
			setWebRootRealPath(getWebRealPath());
			InputStream inputStream = null;
			try {
				inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertieFile);
				p.load(inputStream);
				Enumeration<Object> enums = p.keys();
				while (enums.hasMoreElements()) {
					String key = enums.nextElement().toString();
					propertiesMap.put(key, p.getProperty(key));
				}
			} catch (FileNotFoundException e) {
				logger.error("PangoContext initProperties FileNotFoundException:", e);
			} catch (IOException e) {
				logger.error("PangoContext initProperties IOException:", e);
			} finally {
				try {
					if (inputStream != null) {
						inputStream.close();
					}
				} catch (IOException e) {
					logger.error("PangoContext initProperties IOException:", e);
				}
			}
			logger.info("PangoContext initProperties from(" + propertieFile + ") completed!");
		} catch (NumberFormatException e) {
			logger.error("PangoContext initProperties Exception", e);
		}
	}

	/**
	 * 追加配置
	 */
	public static void addProperties(String propertieFile) {
		initProperties(propertieFile);
	}

	public static String getProperty(String propertyName) {
		if (!isRun && propertiesMap.isEmpty()) {
			initProperties(DEFAULT_COMPONENT);
		}
		return propertiesMap.get(propertyName) == null ? "" : propertiesMap.get(propertyName);
	}

	public static String getProperty(String propertyName, String defaultValue) {
		if (!isRun && propertiesMap.isEmpty()) {
			initProperties(DEFAULT_COMPONENT);
		}
		return propertiesMap.get(propertyName) == null ? defaultValue : propertiesMap.get(propertyName);
	}

	public static String getWebSite() {
		if (!isRun && propertiesMap.isEmpty()) {
			initProperties(DEFAULT_COMPONENT);
		}
		return getProperty("website");
	}
	
	public static String staticRoot(){
		if (!isRun && propertiesMap.isEmpty()) {
			initProperties(DEFAULT_COMPONENT);
		}
		return getProperty("static.root");
	}

	public static String getWebRootRealPath() {
		if (!isRun && propertiesMap.isEmpty()) {
			initProperties(DEFAULT_COMPONENT);
		}
		return webRootRealPath;
	}

	public static void setWebRootRealPath(String webRootRealPath) {
		Context.webRootRealPath = webRootRealPath;
	}
	
	/**
	 * 获取项目webapp下的绝对路径
	 * 
	 * @return
	 */
	public static String getWebRealPath() {
		String path = null;
		String folderPath = Context.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		if (folderPath.indexOf("WEB-INF/lib") > 0) {
			path = folderPath.substring(0, folderPath.indexOf("/lib"))+"/classes";
		}
		return path;
	}
	
}
