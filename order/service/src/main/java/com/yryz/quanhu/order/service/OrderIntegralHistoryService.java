/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: OrderIntegralHistoryService.java, 2018年1月18日 上午11:08:12 yehao
 */
package com.yryz.quanhu.order.service;

import java.util.List;
import java.util.Map;

import com.yryz.quanhu.order.entity.RrzOrderIntegralHistory;
import com.yryz.quanhu.order.utils.Page;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 上午11:08:12
 * @Description 订单积分记录管理
 */
public interface OrderIntegralHistoryService {
	
	/**
	 * 保存流水
	 * @param rrzOrderIntegralHistory
	 */
	public void insert(RrzOrderIntegralHistory rrzOrderIntegralHistory);
	
	/**
	 * 获取积分流水信息列表(start,limit;app端)
	 * @param custId
	 * @param dateTime
	 * @param productType
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<RrzOrderIntegralHistory> getOrderIntegralList(String custId, String dateTime, Integer productType,
			long start, long limit);
	
	/**
	 * 分类用户统计信息，现在主要统计用户提现总额和用户兑现金额总额
	 * @param custId
	 * @param productType
	 * @param type
	 * @return
	 */
	public Long sumCost(String custId, Integer productType, int type);
	
	/**
	 * 获取积分流水信息列表(page,web端)
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page<RrzOrderIntegralHistory> getOrderIntegralListWeb(Map<String, Object> params
			, int pageNo, int pageSize);
	
	/**
	 * 用户积分统计
	 */
	public void integralSum();
	
	/**
	 * 查询用户统计数据列表
	 * @param startTime
	 * @param endTime
	 * @param custId
	 * @return
	 */
	public List<Map<String, Object>> userSum(String startTime ,String endTime ,String custId);

	/**
	 * 单一用户统计
	 * @param custId
	 */
	public void integralSum(String custId);

	/**
	 * 获取用户统计数据
	 * @param custId
	 * @return
	 */
	public Map<String, String> getUserIntegralSum(String custId);
	
	/**
	 * 统计用户收益
	 * @param custId
	 * @param startTime
	 * @param endTime
	 * @param type
	 * @return
	 */
	public Long sumCost(String custId, Integer productType ,Integer orderType,String startTime ,String endTime);


}
