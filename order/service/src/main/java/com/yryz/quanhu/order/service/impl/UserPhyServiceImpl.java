/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: UserPhyServiceImpl.java, 2018年1月18日 下午2:27:03 yehao
 */
package com.yryz.quanhu.order.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yryz.common.message.MessageConstant;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.order.common.QuanhuMessage;
import com.yryz.quanhu.order.dao.persistence.RrzOrderUserPhyDao;
import com.yryz.quanhu.order.dao.redis.RrzOrderUserPhyDaoRedis;
import com.yryz.quanhu.order.entity.RrzOrderUserAccount;
import com.yryz.quanhu.order.entity.RrzOrderUserPhy;
import com.yryz.quanhu.order.exception.CommonException;
import com.yryz.quanhu.order.service.UserAccountService;
import com.yryz.quanhu.order.service.UserPhyService;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 下午2:27:03
 * @Description 用户安全信息处理
 */
@Transactional
@Service
public class UserPhyServiceImpl implements UserPhyService{

	@Autowired
	private RrzOrderUserPhyDao rrzOrderUserPhyDao;
	
	@Autowired
	private RrzOrderUserPhyDaoRedis rrzOrderUserPhyRedis;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private QuanhuMessage quanhuMessage;
	
	private static final int ERROR_COUNT = 4;

	/**
	 * 检查是否已经超过密码输入错误次数
	 * @param custId
	 * @return
	 * @see com.yryz.service.order.modules.order.service.UserPhyService#banCheck(java.lang.String)
	 */
	@Override
	public boolean banCheck(String custId) {
		return rrzOrderUserPhyRedis.banCheck(custId);
	}

	/**
	 * 添加密码输入错误的次数判断
	 * @param custId
	 * @return
	 * @see com.yryz.service.order.modules.order.service.UserPhyService#increaseBan(java.lang.String)
	 */
	@Override
	public int increaseBan(String custId) {
		return rrzOrderUserPhyRedis.increaseBan(custId);
	}

	/**
	 * 在用户输入正确密码后，清理安全输入次数的错误次数
	 * @param custId
	 * @see com.yryz.service.order.modules.order.service.UserPhyService#clearBan(java.lang.String)
	 */
	@Override
	public void clearBan(String custId) {
		rrzOrderUserPhyRedis.clearBan(custId);
	}
	
	/**
	 * 保存用户安全信息
	 * @param rrzOrderUserPhy
	 */
	@Override
	public void saveUserPhy(RrzOrderUserPhy rrzOrderUserPhy){
		rrzOrderUserPhyDao.insert(rrzOrderUserPhy);
		rrzOrderUserPhyRedis.save(rrzOrderUserPhy);
	}
	
	/**
	 * 更新用户安全信息
	 * @param rrzOrderUserPhy
	 */
	@Override
	public void updateUserPhy(RrzOrderUserPhy rrzOrderUserPhy){
		rrzOrderUserPhyDao.update(rrzOrderUserPhy);
		updateUserPhyCache(rrzOrderUserPhy.getCustId());
	}
	
	/**
	 * 更新用户安全信息缓存
	 * @param custId
	 */
	@Override
	public void updateUserPhyCache(String custId){
		RrzOrderUserPhy rrzOrderUserPhy = rrzOrderUserPhyDao.get(custId);
		rrzOrderUserPhyRedis.save(rrzOrderUserPhy);
	}

	/**
	 * 处理用户安全信息
	 * @param rrzOrderUserPhy
	 * @param oldPassword
	 * @return
	 * @see com.yryz.service.order.modules.order.service.UserPhyService#dealUserPhy(com.yryz.service.order.modules.order.entity.RrzOrderUserPhy, java.lang.String)
	 */
	@Override
	public Response<?> dealUserPhy(RrzOrderUserPhy rrzOrderUserPhy, String oldPassword) {
		RrzOrderUserPhy userPhy = getUserPhy(rrzOrderUserPhy.getCustId());
		// 是否重置支付密码成功
		boolean reSetPayWord = false;
		// 如果是新建记录，添加即可
		if (userPhy == null) { 
			userPhy = rrzOrderUserPhy;
			userPhy.setCreateTime(new Date());
			saveUserPhy(userPhy);
		} else {
			if (rrzOrderUserPhy.getPayPassword() != null) {
				if (StringUtils.isEmpty(userPhy.getPayPassword())) {
					// 如果原密码为空，则直接修改
					userPhy.setPayPassword(rrzOrderUserPhy.getPayPassword());
				} else if ("".equals(rrzOrderUserPhy.getPayPassword())) {
					// 重置支付密码，关闭小额免密
					RrzOrderUserAccount userAccount = new RrzOrderUserAccount();
					userAccount.setCustId(rrzOrderUserPhy.getCustId());
					//TODO 新版本，默认小额免密
					userAccount.setSmallNopass(1);
					userAccount.setAccountState(1);
					userAccountService.executeRrzOrderUserAccount(userAccount);
					//TODO 新版本，默认小额免密
					userPhy.setSmallNopass(1);
					userPhy.setPayPassword(rrzOrderUserPhy.getPayPassword());
					reSetPayWord = true;
				} else {
					if (userPhy.getPayPassword().equals(oldPassword)) {
						// 验证旧密码
						userPhy.setPayPassword(rrzOrderUserPhy.getPayPassword());
					} else if (!StringUtils.isBlank(rrzOrderUserPhy.getCustIdcardNo())
							&& !StringUtils.isBlank(rrzOrderUserPhy.getPhyName())
							&& rrzOrderUserPhy.getCustIdcardNo().equals(userPhy.getCustIdcardNo())
							&& rrzOrderUserPhy.getPhyName().equals(userPhy.getPhyName())) {
						// 验证密保问题
						userPhy.setPayPassword(rrzOrderUserPhy.getPayPassword());
					} else {
						return ResponseUtils.returnException(new CommonException("验证失败"));
					}
					quanhuMessage.sendMessage(MessageConstant.EDIT_PAY_PASSWORD, userPhy.getCustId(), null);
				}
			} else {
				if (rrzOrderUserPhy.getCustIdcardType() != null) {
					userPhy.setCustIdcardType(rrzOrderUserPhy.getCustIdcardType());
				}
				if (rrzOrderUserPhy.getCustIdcardNo() != null) {
					userPhy.setCustIdcardNo(rrzOrderUserPhy.getCustIdcardNo());
				}
				if (rrzOrderUserPhy.getPhyName() != null) {
					userPhy.setPhyName(rrzOrderUserPhy.getPhyName());
				}
			}

			if (rrzOrderUserPhy.getSmallNopass() != null) {
				userPhy.setSmallNopass(rrzOrderUserPhy.getSmallNopass());
			}
			userPhy.setUpdateTime(new Date());
			updateUserPhy(userPhy);
		}
		// 找回密码成功后清除密码输出错误次数
		if (reSetPayWord){
			rrzOrderUserPhyRedis.clearBan(rrzOrderUserPhy.getCustId());
		}
		return ResponseUtils.returnSuccess();
	}

	@Override
	public RrzOrderUserPhy getUserPhy(String custId) {
		RrzOrderUserPhy userPhy = rrzOrderUserPhyRedis.get(custId);
		if(userPhy == null) {
			userPhy = rrzOrderUserPhyDao.get(custId);
			if(userPhy != null){
				rrzOrderUserPhyRedis.save(userPhy);
			}
		}
		if (userPhy == null) {
			userPhy = new RrzOrderUserPhy();
			userPhy.setCreateTime(new Date());
			userPhy.setCustId(custId);
			//TODO 新版本，默认小额免密
			userPhy.setSmallNopass(1);
			saveUserPhy(userPhy);
		}
		return userPhy;
	}

	/**
	 * 验证用户安全信息
	 * @param rrzOrderUserPhy
	 * @return
	 * @see com.yryz.service.order.modules.order.service.UserPhyService#checkSecurityProblem(com.yryz.service.order.modules.order.entity.RrzOrderUserPhy)
	 */
	@Override
	public Response<?> checkSecurityProblem(RrzOrderUserPhy rrzOrderUserPhy) {
		RrzOrderUserPhy userPhy = getUserPhy(rrzOrderUserPhy.getCustId());
		// 如果是新建记录，添加即可
		if (userPhy == null) { 
			return ResponseUtils.returnException(new CommonException("未设置安全验证信息"));
		} else {
			if (StringUtils.isBlank(rrzOrderUserPhy.getCustIdcardNo())
					|| StringUtils.isBlank(rrzOrderUserPhy.getPhyName())) {
				return ResponseUtils.returnException(new CommonException("未设置安全验证信息"));
			}

			if (rrzOrderUserPhy.getCustIdcardNo().equals(userPhy.getCustIdcardNo())
					&& rrzOrderUserPhy.getPhyName().equals(userPhy.getPhyName())) {
				return ResponseUtils.returnSuccess();
			} else {
				return ResponseUtils.returnException(new CommonException("安全验证信息错误"));
			}
		}
	}

	/**
	 * 检查支付密码
	 * @param custId
	 * @param payPassword
	 * @return
	 * @see com.yryz.service.order.modules.order.service.UserPhyService#checkPayPassword(java.lang.String, java.lang.String)
	 */
	@Override
	public Response<?> checkPayPassword(String custId, String payPassword) {
		if (StringUtils.isNotEmpty(custId) && StringUtils.isNotEmpty(payPassword)) {
			if (!banCheck(custId)){
				return ResponseUtils.returnException(new CommonException("密码输入错误已达5次，支付密码被锁定，明天凌晨自动解锁，请明天凌晨后再使用或使用忘记支付密码找回！。"));
			}
			RrzOrderUserPhy rrzOrderUserPhy = getUserPhy(custId);
			// 只有用户安全数据存在，并且密码不正常，才返回密码错误
			if (rrzOrderUserPhy != null && StringUtils.isNotEmpty(rrzOrderUserPhy.getPayPassword())
					&& !payPassword.equals(rrzOrderUserPhy.getPayPassword())) {
				int count = increaseBan(custId);
				if (count < ERROR_COUNT){
					return ResponseUtils.returnException(new CommonException("密码输入错误，请重新输入！"));
				}
				if (count == ERROR_COUNT){
					return ResponseUtils.returnException(new CommonException("密码输入错误已达4次，您还有1次机会，5次输入错误，支付密码将被锁定到第二天凌晨自动解锁，您也可以使用忘记支付密码立即找回！"));
				}
				if (count > ERROR_COUNT){
					return ResponseUtils.returnException(new CommonException("密码输入错误已达5次，支付密码被锁定，明天凌晨自动解锁，请明天凌晨后再使用或使用忘记支付密码找回！"));
				}
			} else {
				clearBan(custId);
			}
		}
		return ResponseUtils.returnSuccess();
	}

}
