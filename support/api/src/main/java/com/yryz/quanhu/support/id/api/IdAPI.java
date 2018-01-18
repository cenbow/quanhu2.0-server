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
	 * @param type
	 * @return
	 */
	Long getKid(String type);
	
	/**
	 * 生成用户ID
	 * 
	 * @return
	 */
	String getUserId();

}
