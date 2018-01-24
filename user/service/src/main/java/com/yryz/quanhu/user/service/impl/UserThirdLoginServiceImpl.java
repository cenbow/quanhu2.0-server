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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.IdConstants;
import com.yryz.common.exception.MysqlOptException;
import com.yryz.quanhu.support.id.api.IdAPI;
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
	@Override
	public int delete(Long userId,String thirdId) {
		try {
			return mysqlDao.delete(userId,thirdId);
		} catch (Exception e) {
			logger.error("[CustThirdLoginDao.delete]",e);
			throw new MysqlOptException(e);
		}
	}
	
	@Override
	public int insert(UserThirdLogin record) {
		record.setCreateDate(new Date());
		record.setKid(idApi.getKid(IdConstants.QUANHU_THIRD_LOGIN).getData());
		record.setLastUpdateDate(record.getCreateDate());
		try {
			return mysqlDao.insert(record);
		} catch (Exception e) {
			logger.error("[CustThirdLoginDao.insert]",e);
			throw new MysqlOptException(e);
		}
	}

	@Override
	public List<UserThirdLogin> selectByUserId(Long userId) {
		try {
			return mysqlDao.selectByUserId(userId);
		} catch (Exception e) {
			logger.error("[CustThirdLoginDao.selectByUserId]",e);
			throw new MysqlOptException(e);
		}
	}

	@Override
	public UserThirdLogin selectByThirdId(String thirdId,String appId) {
		try {
			return mysqlDao.selectByThirdId(thirdId,appId);
		} catch (Exception e) {
			logger.error("[CustThirdLoginDao.selectByThirdId]",e);
			throw new MysqlOptException(e);
		}
	}

}
