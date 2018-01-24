/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: RrzOrderCust2bankDao.java, 2018年1月18日 上午11:15:27 yehao
 */
package com.yryz.quanhu.order.dao.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yryz.quanhu.order.entity.RrzOrderCust2bank;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 上午11:15:27
 * @Description 银行卡信息数据操作类
 */
@Mapper
public interface RrzOrderCust2bankDao {
	
	/**
	 * 保存我的银行卡信息
	 * @param rrzOrderCust2bank
	 */
	public void insert(RrzOrderCust2bank rrzOrderCust2bank);
	
	/**
	 * 更新我的银行卡信息
	 * @param rrzOrderCust2bank
	 */
	public void update(RrzOrderCust2bank rrzOrderCust2bank);
	
	/**
	 * 获取我的银行卡列表
	 * @param rrzOrderCust2bank
	 * @return
	 */
	public List<RrzOrderCust2bank> getList(RrzOrderCust2bank rrzOrderCust2bank);
	
	/**
	 * 获取银行卡记录信息
	 * @param cust2BankId 银行卡记录ID
	 * @return
	 */
	public RrzOrderCust2bank get(String cust2BankId);
	
	/**
	 * 根据银行卡号信息搜索卡信息
	 * 
	 * @param rrzOrderCust2bank
	 * @return
	 */
	public List<RrzOrderCust2bank> getBybankCardNo(RrzOrderCust2bank rrzOrderCust2bank);

}
