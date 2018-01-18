/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: OrderAccountHistoryServiceImpl.java, 2018年1月18日 下午2:18:55 yehao
 */
package com.yryz.quanhu.order.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.IdGen;
import com.yryz.quanhu.order.dao.persistence.RrzOrderAccountHistoryDao;
import com.yryz.quanhu.order.entity.RrzOrderAccountHistory;
import com.yryz.quanhu.order.entity.RrzOrderIntegralHistory;
import com.yryz.quanhu.order.service.OrderAccountHistoryService;
import com.yryz.quanhu.order.utils.Page;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 下午2:18:55
 * @Description 用户消费流水数据统计
 */
public class OrderAccountHistoryServiceImpl implements OrderAccountHistoryService {
	
	@Autowired
	private RrzOrderAccountHistoryDao rrzOrderAccountHistoryDao;

	/**
	 * 添加消费流水
	 * @param rrzOrderAccountHistory
	 * @see com.yryz.service.order.modules.order.service.OrderAccountHistoryService#insert(com.yryz.service.order.modules.order.entity.RrzOrderAccountHistory)
	 */
	@Override
	public void insert(RrzOrderAccountHistory rrzOrderAccountHistory) {
		rrzOrderAccountHistory.setHistoryId(IdGen.uuid());
		rrzOrderAccountHistoryDao.insert(rrzOrderAccountHistory);
	}

	/**
	 * 获取用户消费流水列表（page）
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @see com.yryz.service.order.modules.order.service.OrderAccountHistoryService#getOrderAccountListWeb(java.util.Map, int, int)
	 */
	@Override
	public Page<RrzOrderAccountHistory> getOrderAccountListWeb(Map<String, Object> params, int pageNo, int pageSize) {
		Page<RrzOrderAccountHistory> page = new Page<RrzOrderAccountHistory>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrderBy("id");
		page.setOrder(Page.DESC);
		
		com.github.pagehelper.Page<RrzOrderIntegralHistory> pageHelp = PageHelper.startPage(page.getPageNo(), page.getPageSize());
		List<RrzOrderAccountHistory> list = rrzOrderAccountHistoryDao.getListWeb(params);
		page.setResult(list);
		page.setTotalCount(pageHelp.getTotal());
		return page;
	}

	/**
	 * 获取用户消费流水列表
	 * @param custId
	 * @param dateTime
	 * @param productType
	 * @param start
	 * @param limit
	 * @return
	 * @see com.yryz.service.order.modules.order.service.OrderAccountHistoryService#getOrderAccountList(java.lang.String, java.lang.String, java.lang.Integer, long, long)
	 */
	@Override
	public List<RrzOrderAccountHistory> getOrderAccountList(String custId, String dateTime, Integer productType,
			long start, long limit) {
		RrzOrderAccountHistory accountHistory = new RrzOrderAccountHistory();
		accountHistory.setCustId(custId);
		accountHistory.setCreateTime(DateUtils.parseDate(dateTime));
		accountHistory.setProductType(productType);
		accountHistory.setStart(start);
		accountHistory.setLimit(limit);
		return rrzOrderAccountHistoryDao.getList(accountHistory);
	}

}
