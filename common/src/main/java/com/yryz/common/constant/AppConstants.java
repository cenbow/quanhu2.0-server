package com.yryz.common.constant;

public interface AppConstants {
	/**
	 * 应用id
	 */
	public static final String APP_ID = "appId";
    /**
     * 平台用户ID
     */
    public static final String CUST_ID = "custId";
    /**
     * 赊购通用户ID
     */
    public static final String USER_ID = "userId";
    /**
     * 令牌
     */
    public static final String TOKEN = "token";
    
    /**
     * APP版本
     */
    public static final String APP_VERSION = "appVersion";
    
    /**
     * 用户使用设备类型
     */
    public static final String DEV_TYPE = "devType";
    /**
     * 用户使用设备号(极光registrationId)
     */
    public static final String DEV_ID = "devId";
    /**
     * 用户使用设备名称（机型）
     */
    public static final String DEV_NAME = "devName";
    /**
     * h5浏览器标识（区分PC和WAP访问）
     */
    public static final String USER_AGENT = "User-Agent";
    /**
     * APP下载渠道码
     */
    public static final String DITCH_CODE = "ditchCode";
    /**
     * 未登录
     */
    public final static Integer UNLOGIN = -1;
    /**
     * 成功
     */
    public final static Integer SUCCESS = 1;
    /**
     * 后端提醒
     */
    public final static Integer WARN = 2;
    /**
     * 系统错误
     */
    public final static Integer ERROR = 3;
    /**
     * 登录超时
     */
    public final static Integer TOKEN_OUT = 4;
    /**
     * 加密签名错误
     */
    public final static Integer SIGN_ERROR = 5;
    /**
     * 默认前缀昵称
     */
    public static final String NICK_NAME_PREFIX = "qh";
    /**
     * 邀请码错误
     */
    public static final Integer INVITE_CODE_ERROR = 6;
    /**
     * devType 1 IOS; 2 Android; 3 WEB
     */
    public static final Integer DEVTYPE_IOS = 1;

    public static final Integer DEVTYPE_ANDROID = 2;

    public static final Integer DEVTYPE_WEB = 3;
    
    /**
     * User-Agent是否WAP的标记
     */
    public static final String MOBILE = "Mobile";
    
    /**
     * IOS官方唯一安装渠道，（不考虑越狱）
     */
    public static final String INSTALL_CHANNEL = "App Store";

    /**
     * 语言类型:中文
     */
    public static final String CHINESE_LANGUAGE_TYPE = "zh";

    /**
     * 语言类型:英语
     */
    public static final String ENGLISH_LANGUAGE_TYPE = "en";
    
}
