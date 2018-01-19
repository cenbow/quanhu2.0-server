/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: OrderIntegralHistoryServiceImpl.java, 2018年1月18日 下午2:22:53 yehao
 */
package com.yryz.quanhu.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.IdGen;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.order.dao.persistence.RrzOrderIntegralHistoryDao;
import com.yryz.quanhu.order.dao.redis.RrzOrderIntegralHistoryRedis;
import com.yryz.quanhu.order.entity.RrzOrderIntegralHistory;
import com.yryz.quanhu.order.service.OrderIntegralHistoryService;
import com.yryz.quanhu.order.utils.Page;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 下午2:22:53
 * @Description 积分流水信息统计表
 */
@Transactional
@Service
public class OrderIntegralHistoryServiceImpl implements OrderIntegralHistoryService{

	@Autowired
	private RrzOrderIntegralHistoryDao rrzOrderIntegralHistoryDao;
	
	@Autowired
	RrzOrderIntegralHistoryRedis rrzOrderIntegralHistoryRedis;

	/**
	 * 添加积分流水记录
	 * @param rrzOrderIntegralHistory
	 * @see com.yryz.service.order.modules.order.service.OrderIntegralHistoryService#insert(com.yryz.service.order.modules.order.entity.RrzOrderIntegralHistory)
	 */
	@Override
	public void insert(RrzOrderIntegralHistory rrzOrderIntegralHistory) {
		rrzOrderIntegralHistory.setHistoryId(IdGen.uuid());
		rrzOrderIntegralHistoryDao.insert(rrzOrderIntegralHistory);
	}

	/**
	 * 获取积分流水记录
	 * @param custId
	 * @param dateTime
	 * @param productType
	 * @param start
	 * @param limit
	 * @return
	 * @see com.yryz.service.order.modules.order.service.OrderIntegralHistoryService#getOrderIntegralList(java.lang.String, java.lang.String, java.lang.Integer, long, long)
	 */
	@Override
	public List<RrzOrderIntegralHistory> getOrderIntegralList(String custId, String dateTime, Integer productType,
			long start, long limit) {
		RrzOrderIntegralHistory integralHistory = new RrzOrderIntegralHistory();
		integralHistory.setCustId(custId);
		integralHistory.setCreateTime(DateUtils.parseDate(dateTime));
		integralHistory.setProductType(productType);
		integralHistory.setStart(start);
		integralHistory.setLimit(limit);
		return rrzOrderIntegralHistoryDao.getList(integralHistory);
	}

	/**
	 * 用户积分流水统计，通过数据库方式实时统计
	 * @param custId
	 * @param productType
	 * @param type
	 * @return
	 * @see com.yryz.service.order.modules.order.service.OrderIntegralHistoryService#sumCost(java.lang.String, java.lang.Integer, int)
	 */
	@Override
	public Long sumCost(String custId, Integer productType, int type) {
		if (type == 1) {
			RrzOrderIntegralHistory rrzOrderIntegralHistory = new RrzOrderIntegralHistory();
			rrzOrderIntegralHistory.setCustId(custId);
			if (productType != null) {
				rrzOrderIntegralHistory.setProductType(productType);
			}
			return rrzOrderIntegralHistoryDao.sumCost(rrzOrderIntegralHistory);
		}
		return null;
	}

	/**
	 * 获取积分流水记录(page方式)
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @see com.yryz.service.order.modules.order.service.OrderIntegralHistoryService#getOrderIntegralListWeb(java.util.Map, int, int)
	 */
	@Override
	public Page<RrzOrderIntegralHistory> getOrderIntegralListWeb(Map<String, Object> params, int pageNo, int pageSize) {
		Page<RrzOrderIntegralHistory> page = new Page<RrzOrderIntegralHistory>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrderBy("id");
		page.setOrder(Page.DESC);

		com.github.pagehelper.Page<RrzOrderIntegralHistory> pageHelp = PageHelper.startPage(page.getPageNo(), page.getPageSize());
		List<RrzOrderIntegralHistory> list = rrzOrderIntegralHistoryDao.getListWeb(params);
		page.setResult(list);
		page.setTotalCount(pageHelp.getTotal());
		return page;
	}

	/**
	 * 积分统计信息
	 * @see com.yryz.service.order.modules.order.service.OrderIntegralHistoryService#integralSum()
	 */
	@Override
	public void integralSum() {
		String nowTime = DateUtils.getDateTime();
		String startTime = rrzOrderIntegralHistoryRedis.getStartTime();
		List<Map<String, Object>> list = userSum(startTime, nowTime, null);
		rrzOrderIntegralHistoryRedis.integralSum(list);
		rrzOrderIntegralHistoryRedis.setStartTime(nowTime);
		
	}

	/**
	 * 获取用户积分统计总表(根据时间)
	 * @param startTime
	 * @param endTime
	 * @param custId
	 * @return
	 * @see com.yryz.service.order.modules.order.service.OrderIntegralHistoryService#userSum(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> userSum(String startTime, String endTime, String custId) {
		Map<String, String> map = new HashMap<>(1);
		if(StringUtils.isNotEmpty(startTime)){
			map.put("startTime", startTime);
		}
		if(StringUtils.isNotEmpty(endTime)){
			map.put("endTime", endTime);
		}
		if(StringUtils.isNotEmpty(custId)){
			map.put("custId", custId);
		}
		return rrzOrderIntegralHistoryDao.userSum(map);
	}

	/**
	 * 更新单一用户的积分统计信息
	 * @param custId
	 * @see com.yryz.service.order.modules.order.service.OrderIntegralHistoryService#integralSum(java.lang.String)
	 */
	@Override
	public void integralSum(String custId) {
		String nowTime = DateUtils.getDateTime();
		rrzOrderIntegralHistoryRedis.cleanUserSum(custId);
		List<Map<String, Object>> list = userSum(null, nowTime, custId);
		rrzOrderIntegralHistoryRedis.integralSum(list);
		
	}

	/**
	 * 获取用户积分流水统计信息
	 * @param custId
	 * @return
	 * @see com.yryz.service.order.modules.order.service.OrderIntegralHistoryService#getUserIntegralSum(java.lang.String)
	 */
	@Override
	public Map<String, String> getUserIntegralSum(String custId) {
		return rrzOrderIntegralHistoryRedis.getUserIntegralSum(custId);
	}

	/**
	 * 用户统计
	 * @param custId
	 * @param productType
	 * @param startTime
	 * @param endTime
	 * @return
	 * @see com.yryz.service.order.modules.order.service.OrderIntegralHistoryService#sumCost(java.lang.String, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public Long sumCost(String custId, Integer productType,Integer orderType, String startDate, String endDate) {
		Map<String, Object> map = new HashMap<>(4);
		if(StringUtils.isNotEmpty(custId)){
			map.put("custId", custId);
		}
		if(productType != null){
			map.put("productType", productType);
		}
		if(orderType != null){
			map.put("orderType", orderType);
		}
		if(StringUtils.isNotEmpty(startDate)){
			map.put("startDate", startDate);
		}
		if(StringUtils.isNotEmpty(endDate)){
			map.put("endDate", endDate);
		}
		return rrzOrderIntegralHistoryDao.sumCostByDate(map);
	}
}
