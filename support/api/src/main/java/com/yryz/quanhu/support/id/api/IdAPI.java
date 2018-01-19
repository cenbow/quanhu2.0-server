package com.yryz.quanhu.support.id.api;

/**
 * Dubbo Service(id) API
 * @author
 */
public interface IdAPI {
	
	/**
	 * 生成订单ID
	 * 
	 * @return
	 */
	String getId(String type);


	/**
	 * 生产分布式唯一id，id为数字类型，初始时6位随机
	 * 表中的kid字段统一调此接口，为了避免重复，建议type传表名，
	 * type统一维护到com.yryz.common.constant.IdConstants
	 * @param type
	 * @return
	 */
	Long getKid(String type);


	
	/**
	 * 生成用户ID，目前使用Twitter Snowflake雪花算法实现
	 * ID长度18位
	 * @return
	 */
	String getUserId();

	/**
	 * 基于Twitter的分布式自增ID算法Snowflake实现分布式有序
	 * @return 返回18位的自增ID
	 *
	 */
	Long getSnowflakeId();


}
