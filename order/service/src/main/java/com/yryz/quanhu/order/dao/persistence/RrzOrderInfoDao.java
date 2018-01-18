/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: RrzOrderInfoDao.java, 2018年1月18日 上午11:15:55 yehao
 */
package com.yryz.quanhu.order.dao.persistence;

import com.yryz.quanhu.order.entity.RrzOrderInfo;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 上午11:15:55
 * @Description 订单信息操作类
 */
public interface RrzOrderInfoDao {
	
	/**
	 * 保存订单信息
	 * @param rrzOrderInfo
	 */
	public void insert(RrzOrderInfo rrzOrderInfo);
	
	/**
	 * 更新订单信息
	 * @param rrzOrderInfo
	 */
	public void update(RrzOrderInfo rrzOrderInfo);
	
	/**
	 * 获取订单信息
	 * @param orderId 订单ID
	 * @return
	 */
	public RrzOrderInfo get(String orderId);
	
	/**
	 * 获取订单信息(锁表)
	 * @param orderId 订单ID
	 * @return
	 */
	public RrzOrderInfo getForUpdate(String orderId);

}
