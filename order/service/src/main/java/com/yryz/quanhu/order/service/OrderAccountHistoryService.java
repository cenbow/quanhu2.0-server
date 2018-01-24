/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: OrderAccountHistoryService.java, 2018年1月18日 上午11:05:06 yehao
 */
package com.yryz.quanhu.order.service;

import java.util.List;
import java.util.Map;

import com.yryz.quanhu.order.entity.RrzOrderAccountHistory;
import com.yryz.quanhu.order.utils.Page;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 上午11:05:06
 * @Description 订单消费流水管理
 */
public interface OrderAccountHistoryService {
	
	/**
	 * 保存消费流水
	 * @param rrzOrderAccountHistory
	 */
	public void insert(RrzOrderAccountHistory rrzOrderAccountHistory);
	
	/**
	 * 获取消费流水记录(page，web端调用)
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page<RrzOrderAccountHistory> getOrderAccountListWeb(Map<String, Object> params, int pageNo, int pageSize);
	
	/**
	 * 获取消费流水记录(start,limit，app调用)
	 * @param custId
	 * @param dateTime
	 * @param productType
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<RrzOrderAccountHistory> getOrderAccountList(String custId, String dateTime, Integer productType,
			long start, long limit);

}
