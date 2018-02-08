/*
 * AssertUtils.java
 * Copyright (c) 2011,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2011-3-9 Created
 */
package com.yryz.quanhu.support.id.bufferedid.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 系统上下文信息,公共属性
 * </p>
 * 
 * @author
 * @version 1.0 2011-3-8
 * @since 1.0
 */
public class Context {

	/******************* 以下为常用操作的方法变量 *************************/
	public final static String LANGUAGE_TYPE = "language_type";// 语言种类
	public final static String SUCESS_MESSAGE_VALUE = "_sucess_message";// 返回页面的错误消息
	public final static String ERROR_MESSAGE_VALUE = "_error_message";// 返回页面的成功消息
	public final static String TEMP_SUCESS_MESSAGE = "_temp_sucess_message";// 暂存的成功消息
	public final static String TEMP_ERROR_MESSAGE = "_temp_error_message";// 暂存的错误消息
	public final static String TEMP_PUBLISH_MESSAGE = "_temp_publish_message";// 暂存的错误消息

	public final static String SECURITY_ISJM = "_pango_jmlogin";// 是否登录
	public final static String MEMBER_LOGIN = "_member_login";// 登录会员对象
	public final static String MEMBER_LOGIN_JM = "_member_login_jm";// 登录会员对象
	public final static String SECURITY_ISLOGIN = "_pango_islogin";// 是否登录
	public final static String SECURITY_NOLOGIN = "_pango_nouser";// 用户是否存在
	public final static String SECURITY_EN_CANVASS = "_pango_en_canvass";// 英文站招商用户登录
	public final static String SECURITY_EMAIL_NOT_APPROVE = "_pango_email_not_approve";// 英文站招商用户登录

	public final static String SECURITY_USERID = "userId";// 登录用户ID
	public final static String SECURITY_USERCOUNT = "userEmail";// 登录EMAIL
	public final static String SECURITY_USERNAME = "userName";// 登录用户名
	public final static String SECURITY_USERTYPE = "userType";// 会员类别(资金或者项目方)
	public final static String SECURITY_ISPERSON = "isPerson";// 是否个人

	public final static String METHOD_LIST = "/list";// 列表查询
	public final static String METHOD_VIEM = "/show";// 单个显示
	public final static String METHOD_PRINT = "/print";// 项目打印
	public final static String METHOD_CREATE = "/create";// 新增
	public final static String METHOD_UPDATE = "/edit";// 编辑
	public final static String METHOD_SAVE = "/save";// 保存
	public final static String METHOD_DEL = "/delete";// 删除
	public final static String METHOD_SUC = "/success";// 成功
	// 频道聚合页URL
	public final static String METHOD_INDEX = "/index";// 聚合页
	// 以下为项目发布URL
	public final static String METHOD_PRO_CHANNEL = "/channel";// 基本信息
	public final static String METHOD_PRO_BASE = "/base";// 基本信息
	public final static String METHOD_PRO_DETAIL = "/detail";// 详细信息
	public final static String METHOD_PRO_CONTACT = "/contact";// 联系信息
	public final static String METHOD_PRO_EXTRA = "/extra";// 附加信息
	public final static String METHOD_PRO_NEXT = "/next";// 进入下一步
	public final static String METHOD_PRO_PREV = "/prev";// 上一步
	public final static String METHOD_PRO_PUBLISH = "/publish";// 进入下一步
	public final static String METHOD_PRO_DISPLAY = "/display";// 进入下一步
	public final static String METHOD_PRO_DRAFT = "/draft";// 草稿
	public final static String METHOD_PRO_PREVIEW = "/preview";// 预览

	public final static String METHOD_STOP = "/stop";// 停用
	public final static String METHOD_VERIFY = "/verify";// 审核
	public final static String METHOD_COMPLETE_ALERT = "/completeAlert";// 完整度提醒
	public final static String METHOD_SIMPLEVIEW = "/simpleView"; // 简版显示
	public final static String METHOD_EXCEL = "/excel";// 导出excel

	public static final String FILTER_NOTAPPLIED = "_security_filterSecurityInterceptor_filterNotApplied";

	public static final String COOKIE_REFERER = "rzreferer";
	public static final String CAMPAIGN = "pk_campaign";
	public static final String PROJECT = "project_";
	public static final String CONTACT = "contact_";
	public static final String FORWARD_SLASH = "/";
	/******************* 常用操作的方法变量结束 *************************/
	
	
	/** 资讯缓存常量 */
	public static final String NEWS_REDIS_JRTT = "news_jrtt";	// 资讯 今日头条
	public static final String NEWS_REDIS_RMPH = "news_rmph";	// 资讯 热门排行 
	public static final String NEWS_REDIS_XGYD = "news_xgyd";   // 资讯 相关阅读
	public static final String NEWS_REDIS_RMTJ = "news_rmtj";	// 资讯 热门推荐
	public static final String NEWS_REDIS_LLCS = "news_llcs";	// 资讯 浏览次数
	public static final String TRANING_REDIS_JRTT = "traning_jrtt";		// 学院 今日头条
	public static final String TRANING_REDIS_RMPH = "traning_rmph";		// 学院 热门排行 
	public static final String TRANING_REDIS_XGYD = "traning_xgyd";   	// 学院 相关阅读
	public static final String TRANING_REDIS_RMTJ = "traning_rmtj";		// 学院 热门推荐
	public static final String TRANING_REDIS_LLCS = "traning_llcs";		// 学院 浏览次数
	public static final String WEALTH_REDIS_RANK="wealth_rank";//财富风云榜
	
	public static final String ADMIN_WEBSITE = "admin";
	public static final String HOME_WEBSITE = "home";

	/** 默认日志名称 */
	public final static String LOGGER_NAME = "SimpleLogger";
	// /** 默认主键名称 */
	// public final static String KEY_NAME = "PID";
	// /** 上下文路径 */
	// public static String realPath = "";

	static Logger logger = LoggerFactory.getLogger(Context.class);

	private static volatile boolean isRun = Boolean.FALSE;

	/**
	 * UTF-8编码
	 */
	public static final String UTF8 = "UTF-8";

	/** 默认主键名称 */
	public final static String KEY_NAME = "PID";

	/** 上下文路径 */
	public static String realPath = "";

	@SuppressWarnings("unused")
	private static volatile int invacodeCount = 0;
	/**
	 * 时间格式
	 */
	public static final String dateType = "yyyy-MM-dd";

	// 默认替代词
	public static final String SENSIT_REPLACE = "*";

	static Map<String, String> propertiesMap = new ConcurrentHashMap<String, String>();

	public static final String EXTEND_REG = "extend_reg";		//注册
	public static final String EXTEND_CAIFU = "extend_caifu";	// 财富页面
	public static final String EXTEND_DOWNLOAD = "extend_download"; //app下载
	public static final String EXTEND_HONGBAO = "extend_hongbao"; //app下载
	
	public static final int EXTEND_TYPE_REG = 1;
	public static final int EXTEND_TYPE_CAIFU = 2;
	public static final int EXTEND_TYPE_DOWNLOAD = 3;
	public static final int EXTEND_TYPE_HONGBAO = 4;
	
	
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
		if ("".equals(realPath))
			realPath = path;
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
					if (inputStream != null)
						inputStream.close();
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
		if (!isRun && propertiesMap.isEmpty())
			initProperties("conf/component.properties");
		return propertiesMap.get(propertyName) == null ? "" : propertiesMap.get(propertyName);
	}

	public static String getProperty(String propertyName, String defaultValue) {
		if (!isRun && propertiesMap.isEmpty())
			initProperties("component.properties");
		return propertiesMap.get(propertyName) == null ? defaultValue : propertiesMap.get(propertyName);
	}

	public static String getWebSite() {
		return getProperty("website");
	}
	
	public static String staticRoot(){
		return getProperty("static.root");
	}

}
