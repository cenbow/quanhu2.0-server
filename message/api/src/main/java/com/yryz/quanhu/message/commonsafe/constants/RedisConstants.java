package com.yryz.quanhu.message.commonsafe.constants;
/**
 * redis key前缀定义
 * @author danshiyu
 *
 */
public class RedisConstants {
	/**
	 * 限制ip验证发送次数统计	
	 */
	public static final String IP_LIMIT_COUNT = "IPLC";
	/**
	 * 每个ip业务执行时间
	 */
	public static final String IP_RUN_TIME = "IPRT";
	/**
	 * 短信
	 */
	public static final String SMS = "sms.";		
	public static final String SMS_LOCK = "lock.";

	/**
	 * 图像验证码key
	 */
	public static final String IMG_VIOLATE_CODE = "IMGVC";
	/**
	 * 图形验证码校验次数key（触发图像验证码）
	 */
	public static final String IMG_CHECK_COUNT = "IMGCC";
	/**
	 * 配置基本信息
	 */
	public final static String TABLESPACE_CONFIGINFO = "SYSCOFIN";
	/**
	 * 按配置类型组合配置信息队列
	 */
	public final static String TABLESPACE_CONFIGTYPESORTSET = "SYSCOFTPSS";
	/**
	 * 普通验证码
	 */
	public final static String VERIFY_CODE = "VERC";
	
	/**
	 * 普通验证码获取风控
	 */
	public final static String VERIFY_CODE_TIME = "VERCT";
	/**
	 * 普通验证码获取风控(当天获取总量)
	 */
	public static final String VERIFY_CODE_TOTAL = "total";
	/**
	 * 普通验证码获取风控(最后获取时间)
	 */
	public static final String VERIFY_CODE_LASTTIME = "lastTime";
	
	/**
	 * 图形验证码
	 */
	public final static String IMG_VERIFY_CODE = "IVERC";
	/**
	 * 触发图形验证码显示的累计次数
	 */
	public final static String IMG_VERIFY_VILATE_TIMES = "IVVT";
	/**
	 * ip限制
	 */
	public final static String IP_LIMIT = "IPLIM";
}
