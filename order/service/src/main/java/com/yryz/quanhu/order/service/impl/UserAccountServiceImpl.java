/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: UserAccountServiceImpl.java, 2018年1月18日 下午2:24:31 yehao
 */
package com.yryz.quanhu.order.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.order.dao.persistence.RrzOrderUserAccountDao;
import com.yryz.quanhu.order.dao.redis.RrzOrderUserAccountRedis;
import com.yryz.quanhu.order.entity.RrzOrderUserAccount;
import com.yryz.quanhu.order.service.UserAccountService;
import com.yryz.quanhu.order.service.UserPhyService;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 下午2:24:31
 * @Description 账户账户信息管理服务实现
 */
@Transactional
@Service
public class UserAccountServiceImpl implements UserAccountService {
	
	@Autowired
	private RrzOrderUserAccountDao rrzOrderUserAccountDao;
	
	@Autowired
	private RrzOrderUserAccountRedis rrzOrderUserAccountRedis;
	
	@Autowired
	private UserPhyService userPhyService;
	
	/**
	 * 获取用户账户信息
	 * @param userAccount
	 * @see com.yryz.service.order.modules.order.service.UserAccountService#executeRrzOrderUserAccount(com.yryz.service.order.modules.order.entity.RrzOrderUserAccount)
	 */
	@Override
	public void executeRrzOrderUserAccount(RrzOrderUserAccount userAccount) {
		RrzOrderUserAccount account = getUserAccount(userAccount.getCustId());
		if (account != null) {
			if (userAccount.getAccountState() != null) {
				account.setAccountState(userAccount.getAccountState());
			}
			if (userAccount.getSmallNopass() != null) {
				account.setSmallNopass(userAccount.getSmallNopass());
			}
			account.setUpdateTime(new Date());
			updateUserAccount(account);
		} else {
			account = new RrzOrderUserAccount();
			account.setCustId(userAccount.getCustId());
			account.setAccountSum(0L);
			account.setCostSum(0L);
			account.setIntegralSum(0L);
			if (userAccount.getAccountState() != null) {
				account.setAccountState(userAccount.getAccountState());
			} else {
				account.setAccountState(1);
			}
//			if (userAccount.getSmallNopass() != null) {
//				account.setSmallNopass(userAccount.getSmallNopass());
//			} else {
			//小额免密默认开启
			account.setSmallNopass(1);
//			}
			account.setCreateTime(new Date());
			saveUserAccount(account);
		}
	}
	
	/**
	 * 获取用户账户信息
	 * @param userAccount
	 * @return
	 * @see com.yryz.service.order.modules.order.service.UserAccountService#getUserAccount(com.yryz.service.order.modules.order.entity.RrzOrderUserAccount)
	 */
	@Override
	public RrzOrderUserAccount getUserAccount(RrzOrderUserAccount userAccount) {
		return getUserAccount(userAccount.getCustId());
	}
	
	/**
	 * 获取用户账户信息
	 * @param custId
	 * @return
	 */
	@Override
	public RrzOrderUserAccount getUserAccount(String custId) {
		RrzOrderUserAccount account = rrzOrderUserAccountRedis.getUserAccount(custId);
		if(account == null){
			account = rrzOrderUserAccountDao.get(custId);
			if(account != null){
				rrzOrderUserAccountRedis.saveUserAccount(account);
			}
		}
		if (StringUtils.isNotEmpty(custId) && account == null) {
			account = new RrzOrderUserAccount();
			account.setCustId(custId);
			account.setAccountSum(0L);
			account.setCostSum(0L);
			account.setIntegralSum(0L);
			account.setAccountState(1);
			account.setSmallNopass(1);
			account.setCreateTime(new Date());
			saveUserAccount(account);
		}
		boolean flag = userPhyService.banCheck(custId);
		// 如果密码输入超过次数，返回参数也设为0
		if (!flag) { 
			account.setAccountState(0);
		}
		return account;
	}
	
	/**
	 * 更新用户
	 * @param account
	 */
	@Override
	public void updateUserAccount(RrzOrderUserAccount account){
		rrzOrderUserAccountDao.update(account);
		updateUserAccountCache(account.getCustId());
	}
	
	/**
	 * 更新用户缓存
	 * @param custId
	 */
	@Override
	public void updateUserAccountCache(String custId){
		RrzOrderUserAccount redisAccount = rrzOrderUserAccountDao.get(custId);
		rrzOrderUserAccountRedis.update(redisAccount);
	}
	
	/**
	 * 保存账户
	 * @param account
	 */
	@Override
	public void saveUserAccount(RrzOrderUserAccount account){
		rrzOrderUserAccountDao.insert(account);
		rrzOrderUserAccountRedis.saveUserAccount(account);
	}

	/**
	 * 账户锁定
	 * @param custId
	 * @see com.yryz.service.order.modules.order.service.UserAccountService#lockUserAccount(java.lang.String)
	 */
	@Override
	public void lockUserAccount(String custId) {
		RrzOrderUserAccount account = getUserAccount(custId);
		if (account != null) {
			account.setAccountState(0);
			account.setUpdateTime(new Date());
			updateUserAccount(account);
		}		
	}

	/**
	 * 解锁账户
	 * @param custId
	 * @see com.yryz.service.order.modules.order.service.UserAccountService#unlockUserAccount(java.lang.String)
	 */
	@Override
	public void unlockUserAccount(String custId) {
		RrzOrderUserAccount account = getUserAccount(custId);
		if (account != null) {
			account.setAccountState(1);
			account.setUpdateTime(new Date());
			updateUserAccount(account);
		}
		userPhyService.clearBan(custId);
	}

	/**
	 * 更新用户消费余额
	 * @param custId
	 * @param amount
	 * @param type
	 * @return
	 * @see com.yryz.service.order.modules.order.service.UserAccountService#optAccountSource(java.lang.String, long, int)
	 */
	@Override
	public RrzOrderUserAccount optAccountSource(String custId, long amount, int type) {
		RrzOrderUserAccount rrzOrderUserAccount = rrzOrderUserAccountRedis.optAccountSource(custId, amount, type);
		rrzOrderUserAccountDao.update(rrzOrderUserAccount);
		return rrzOrderUserAccount;
	}

	/**
	 * 更新用户积分余额
	 * @param custId
	 * @param amount
	 * @param type
	 * @return
	 * @see com.yryz.service.order.modules.order.service.UserAccountService#optIntegralSource(java.lang.String, long, int)
	 */
	@Override
	public RrzOrderUserAccount optIntegralSource(String custId, long amount, int type) {
		RrzOrderUserAccount rrzOrderUserAccount = rrzOrderUserAccountRedis.optIntegralSource(custId, amount, type);
		rrzOrderUserAccountDao.update(rrzOrderUserAccount);
		return rrzOrderUserAccount;
	}

}
