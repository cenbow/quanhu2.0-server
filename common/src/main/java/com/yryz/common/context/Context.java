/*
 * AssertUtils.java
 * Copyright (c) 2011,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2011-3-9 Created
 */
package com.yryz.common.context;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * <p>
 * 系统上下文信息,公共属性
 * </p>
 *
 * @author kinjoYang
 * @version 1.0 2011-3-8
 * @since 1.0
 */
@Configuration
public class Context implements EnvironmentAware {

    private static Environment env;

    /**
     * UTF-8编码
     */
    public static final String UTF8 = "UTF-8";

    /**
     * 默认主键名称
     */
    public final static String KEY_NAME = "PID";

    /**
     * webRoot真实路径
     */
    public static String webRootRealPath;

    @SuppressWarnings("unused")
    private static volatile int invacodeCount = 0;
    /**
     * 时间格式
     */
    public static final String dateType = "yyyy-MM-dd";

    /**
     * 默认替代词
     */
    public static final String SENSIT_REPLACE = "*";

    /**
     * 初始化webRootRealPath
     */
    static {
        setWebRootRealPath(getWebRealPath());
    }

    public Context() {

    }

    /**
     * Construct
     * 自定义初始化： 当自定义path不为空时，加载path路径下的配置文件
     * 默认初始化：lazy初始化方式，默认读取相对路径下的component.properties
     *
     * @param path
     */
    public Context(String path) {
        setWebRootRealPath(getWebRealPath());
    }

    public static String getProperty(String propertyName) {
        return env.getProperty(propertyName) == null ? "" : env.getProperty(propertyName);
    }

    public static String getProperty(String propertyName, String defaultValue) {
        return env.getProperty(propertyName) == null ? defaultValue : env.getProperty(propertyName);
    }

    public static String getWebSite() {
        return env.getProperty("website");
    }

    public static String staticRoot() {
        return env.getProperty("static.root");
    }

    public static String getWebRootRealPath() {
//        if (Context.webRootRealPath == null) {
//            Context.webRootRealPath = getWebRealPath();
//        }
//        return webRootRealPath;
        return "";
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
        System.out.println("---------------------------------------------------");
        System.out.println("---------------------------------------------------");
        System.out.println("folderPath=" + folderPath);
        System.out.println("---------------------------------------------------");
        System.out.println("---------------------------------------------------");
        if (folderPath.indexOf("WEB-INF/lib") > 0) {
            path = folderPath.substring(0, folderPath.indexOf("/lib")) + "/classes/";
        }
        return path;
    }

    /**
     * @param env
     * @see org.springframework.context.EnvironmentAware#setEnvironment(org.springframework.core.env.Environment)
     */
    @Override
    public void setEnvironment(Environment env) {
        Context.env = env;
    }

}
