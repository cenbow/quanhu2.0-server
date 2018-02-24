/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月10日
 * Id: CustThirdLoginServiceImpl.java, 2017年11月10日 下午1:36:27 Administrator
 */
package com.yryz.quanhu.user.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.IdConstants;
import com.yryz.common.exception.MysqlOptException;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.dao.UserAccountRedisDao;
import com.yryz.quanhu.user.dao.UserThirdLoginDao;
import com.yryz.quanhu.user.entity.UserThirdLogin;
import com.yryz.quanhu.user.service.UserThirdLoginService;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月10日 下午1:36:27
 * @Description 第三方账户业务
 */
@Service
public class UserThirdLoginServiceImpl implements UserThirdLoginService {
	private static final Logger logger = LoggerFactory.getLogger(UserThirdLoginServiceImpl.class);
	
	@Autowired
	private UserThirdLoginDao mysqlDao;
	@Reference(check=false)
	private IdAPI idApi;
	@Autowired
	private UserAccountRedisDao accountRedisDao;
	
	@Override
	@Transactional
	public int delete(Long userId,String thirdId) {
		try {
			int result = mysqlDao.delete(userId,thirdId,null);
			accountRedisDao.deleteLoginMethod(userId);
			return result;
		} catch (Exception e) {
			logger.error("[UserThirdLoginDao.delete]",e);
			throw new MysqlOptException(e);
		}
	}
	
	@Override
	@Transactional
	public int insert(UserThirdLogin record) {
		record.setCreateDate(new Date());
		record.setKid(ResponseUtils.getResponseData(idApi.getKid(IdConstants.QUANHU_THIRD_LOGIN)));
		record.setLastUpdateDate(record.getCreateDate());
		try {
			int result = mysqlDao.insert(record);
			accountRedisDao.deleteLoginMethod(record.getUserId());
			return result;
		} catch (Exception e) {
			logger.error("[UserThirdLoginDao.insert]",e);
			throw new MysqlOptException(e);
		}
	}

	@Override
	public List<UserThirdLogin> selectByUserId(Long userId) {
		List<UserThirdLogin> logins = accountRedisDao.getLoginMethod(userId);
		if(CollectionUtils.isNotEmpty(logins)){
			return logins;
		}
		try {
			logins = mysqlDao.selectByUserId(userId);
		} catch (Exception e) {
			logger.error("[UserThirdLoginDao.selectByUserId]",e);
			throw new MysqlOptException(e);
		}
		if(CollectionUtils.isNotEmpty(logins)){
			accountRedisDao.saveLoginMethod(userId, logins);
		}
		return logins;
	}

	@Override
	public UserThirdLogin selectByThirdId(String thirdId,String appId,Integer type) {
		UserThirdLogin login = accountRedisDao.getUserThird(thirdId, appId, type);
		if(login != null){
			return login;
		}
		try {
			login = mysqlDao.selectByThirdId(thirdId,appId);
		} catch (Exception e) {
			logger.error("[UserThirdLoginDao.selectByThirdId]",e);
			throw new MysqlOptException(e);
		}
		if(login != null){
			accountRedisDao.saveUserThird(login);
		}
		return login;
	}

}
