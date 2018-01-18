/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: RrzOrderIntegralHistoryDao.java, 2018年1月18日 上午11:16:33 yehao
 */
package com.yryz.quanhu.order.dao.persistence;

import java.util.List;
import java.util.Map;

import com.yryz.quanhu.order.entity.RrzOrderIntegralHistory;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 上午11:16:33
 * @Description TODO (这里用一句话描述这个方法的作用)
 */
public interface RrzOrderIntegralHistoryDao {
	
	/**
	 * 保存积分流水
	 * @param rrzOrderIntegralHistory
	 */
	public void insert(RrzOrderIntegralHistory rrzOrderIntegralHistory);
	
	/**
	 * 更新积分流水
	 * @param rrzOrderIntegralHistory
	 */
	public void update(RrzOrderIntegralHistory rrzOrderIntegralHistory);
	
	/**
	 * 获取积分流水列表
	 * @param rrzOrderIntegralHistory
	 * @return
	 */
	public List<RrzOrderIntegralHistory> getList(RrzOrderIntegralHistory rrzOrderIntegralHistory);	
	
	/**
	 * 统计总额
	 * @param rrzOrderIntegralHistory
	 * @return
	 */
	public Long sumCost(RrzOrderIntegralHistory rrzOrderIntegralHistory);
	
	/**
	 * 获取积分流水列表(web)
	 * @param params
	 * @return
	 */
	public List<RrzOrderIntegralHistory> getListWeb(Map<String,Object> params);
	
	/**
	 * 查询用户统计列表
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> userSum(Map<String, String> map);
	
	/**
	 * 查询用户统计列表(根据时间间隔统计)
	 * @param map
	 * @return
	 */
	public Long sumCostByDate(Map<String, Object> map);

}
