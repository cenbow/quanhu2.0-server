/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: UserAccountService.java, 2018年1月18日 上午11:09:02 yehao
 */
package com.yryz.quanhu.order.service;

import com.yryz.quanhu.order.entity.RrzOrderUserAccount;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 上午11:09:02
 * @Description 用户账户管理服务接口
 */
public interface UserAccountService {
	
	/**
	 * 处理用户账户信息
	 * @param userAccount
	 */
	public void executeRrzOrderUserAccount(RrzOrderUserAccount userAccount);
	
	/**
	 * 获取用户账户信息
	 * @param userAccount
	 * @return
	 */
	public RrzOrderUserAccount getUserAccount(RrzOrderUserAccount userAccount);
	
	/**
	 * 获取用户账户信息
	 * @param custId
	 * @return
	 */
	public RrzOrderUserAccount getUserAccount(String custId);
	
	/**
	 * 更新用户
	 * @param account
	 */
	public void updateUserAccount(RrzOrderUserAccount account);
	
	/**
	 * 更新用户缓存
	 * @param custId
	 */
	public void updateUserAccountCache(String custId);
	
	/**
	 * 保存账户
	 * @param account
	 */
	public void saveUserAccount(RrzOrderUserAccount account);
	
	/**
	 * 锁定账户
	 * @param custId
	 */
	public void lockUserAccount(String custId);
	
	/**
	 * 解锁账户
	 * @param custId
	 */
	public void unlockUserAccount(String custId);
	
	/**
	 * 操作账户余额
	 * 
	 * @param custId
	 * @param amount
	 * @param type 0，减余额；1，加余额
	 * @return 
	 */
	public RrzOrderUserAccount optAccountSource(String custId ,long amount , int type);
	
	/**
	 * 操作积分余额
	 * 
	 * @param custId
	 * @param amount
	 * @param type 0，减余额；1，加余额
	 * @return
	 */
	public RrzOrderUserAccount optIntegralSource(String custId ,long amount , int type);

}
