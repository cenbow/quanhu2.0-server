package com.yryz.common.constant;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2017/11/1 15:12
 * Created by lifan
 */
public class CommonConstants {
	/**
	 * redis用户数据源
	 */
	public static final String REDIS_SOURCE_USER="USER";
	/**
	 * redis认证数据源
	 */
	public static final String REDIS_SOURCE_AUTH="AUTH";
	
	/**
	 * 默认的列表长度
	 */
	public static int DEFAULT_SIZE = 20;
	
	/**
	 * 应用ID
	 */
	public static final String APP_ID="appId";
	/**
	 * 应用安全码
	 */
	public static final String APP_SECRET="appSecret";
	/**
	 *  设备ID
	 */
	public static final String DEVICE_ID="devId";
	
	/**
	 * 请求来源 1-IOS 2-Android 3-PC
	 */
	public static final String DEV_TYPE = "devType";
	
	/**
	 * token
	 */
	public static final String TOKEN = "token";
	
	/**
	 * custId
	 */
	public static final String CUST_ID = "custId";
	
	/**
	 * web 端dev类型
	 */
	public static final String DEV_TYPE_ANROID = "12";
	public static final String DEV_TYPE_IOS = "11";
	public static final String DEV_TYPE_PC = "13";

    /**
     * 上架
     */
    public static final Byte SHELVE_YES = 10;

    /**
     * 未删除
     */
    public static final Byte DELETE_NO = 10;

    /**
     * 下架
     */
    public static final Byte SHELVE_NO = 11;

    /**
     * 删除
     */
    public static final Byte DELETE_YES = 11;


	/**
	 * 推荐
	 */
	public static final Byte recommend_YES = 11;

	/**
	 * 非推荐
	 */
	public static final Byte recommend_NO = 10;


	/**  
	* @Fields : 服務名
	*/
	public static final String SPRING_APPLICATION_NAME = "spring.application.name";
}
