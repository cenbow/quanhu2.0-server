/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: RrzOrderPayHistoryDao.java, 2018年1月18日 上午11:17:03 yehao
 */
package com.yryz.quanhu.order.dao.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yryz.quanhu.order.entity.RrzOrderPayHistory;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 上午11:17:03
 * @Description 资金操作数据处理接口
 */
@Mapper
public interface RrzOrderPayHistoryDao {
	
	/**
	 * 添加
	 * @param rrzOrderPayHistory
	 */
	public void insert(RrzOrderPayHistory rrzOrderPayHistory);
	
	/**
	 * 更新
	 * @param rrzOrderPayHistory
	 */
	public void update(RrzOrderPayHistory rrzOrderPayHistory);
	
	/**
	 * 获取列表
	 * @param rrzOrderPayHistory
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<RrzOrderPayHistory> getList(RrzOrderPayHistory rrzOrderPayHistory , long start ,long limit);
	
	/**
	 * 单一查询
	 * @param rrzOrderPayHistory
	 * @return
	 */
	public RrzOrderPayHistory get(RrzOrderPayHistory rrzOrderPayHistory);

}
