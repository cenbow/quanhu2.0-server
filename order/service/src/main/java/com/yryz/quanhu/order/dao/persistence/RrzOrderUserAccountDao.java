/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: RrzOrderUserAccountDao.java, 2018年1月18日 上午11:18:26 yehao
 */
package com.yryz.quanhu.order.dao.persistence;

import java.util.Set;

import org.apache.ibatis.annotations.Mapper;

import com.yryz.quanhu.order.entity.RrzOrderUserAccount;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 上午11:18:26
 * @Description 资金账户信息管理数据访问接口
 */
@Mapper
public interface RrzOrderUserAccountDao {
	
	/**
	 * 保存账户
	 * @param rrzOrderUserAccount
	 */
	public void insert(RrzOrderUserAccount rrzOrderUserAccount);
	
	/**
	 * 获取账户信息
	 * @param custId
	 * @return
	 */
	public RrzOrderUserAccount get(String custId);
	
	/**
	 * 更新账户信息
	 * @param rrzOrderUserAccount
	 */
	public void update(RrzOrderUserAccount rrzOrderUserAccount);
	
	/**
	 * 操作账户余额
	 * @param custId
	 * @param amount
	 * @param type 0，减余额；1，加余额
	 * @return 用户账户信息
	 */
	public RrzOrderUserAccount optAccountSource(String custId ,long amount , int type);
	
	/**
	 * 操作积分余额
	 * @param custId
	 * @param amount
	 * @param type 0，减余额；1，加余额
	 * @return 用户账户信息
	 */
	public RrzOrderUserAccount optIntegralSource(String custId ,long amount , int type);
	
	/**
	 * 刷新mysql数据到redis，重新做数据一致化
	 * 
	 * @param custIds
	 */
	public void refreshUserAccount(Set<String> custIds);

}
