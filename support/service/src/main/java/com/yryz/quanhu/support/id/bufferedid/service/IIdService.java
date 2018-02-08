package com.yryz.quanhu.support.id.bufferedid.service;

public interface IIdService {
	
	/**
	 * 生成订单ID
	 * 
	 * @return
	 * @param type
	 */
	public String getOrderId(String type);

	Long getKid(String type);
	
	/**
	 * 生成用户ID
	 * 
	 * @return
	 */
	public String getUserId();

}
