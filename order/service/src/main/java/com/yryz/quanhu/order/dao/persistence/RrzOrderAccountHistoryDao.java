/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: RrzOrderAccountHistoryDao.java, 2018年1月18日 上午11:14:34 yehao
 */
package com.yryz.quanhu.order.dao.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yryz.quanhu.order.entity.RrzOrderAccountHistory;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 上午11:14:34
 * @Description 消费订单流水数据库操作类
 */
@Mapper
public interface RrzOrderAccountHistoryDao {
	
	/**
	 * 保存消费流水
	 * @param rrzOrderAccountHistory
	 */
	public void insert(RrzOrderAccountHistory rrzOrderAccountHistory);
	
	/**
	 * 更新消费流水
	 * @param rrzOrderAccountHistory
	 */
	public void update(RrzOrderAccountHistory rrzOrderAccountHistory);
	
	/**
	 * 获取消费流水列表
	 * @param rrzOrderAccountHistory 查询实体
	 * @return
	 */
	public List<RrzOrderAccountHistory> getList(RrzOrderAccountHistory rrzOrderAccountHistory);
	
	/**
	 * 统计总额
	 * 
	 * @param rrzOrderAccountHistory 查询实体
	 * @return
	 */
	public Long sumCost(RrzOrderAccountHistory rrzOrderAccountHistory);
	
	/**
	 * 获取消费流水列表
	 * @param params 查询参数
	 * @return
	 */
	public List<RrzOrderAccountHistory> getListWeb(Map<String,Object> params);


}
