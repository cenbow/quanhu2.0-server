/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年8月10日
 * Id: SmsChannelServiceImpl.java, 2017年8月10日 下午5:32:22 Administrator
 */
package com.yryz.quanhu.sms.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yryz.common.exception.MysqlOptException;
import com.yryz.quanhu.sms.dao.SmsChannelDao;
import com.yryz.quanhu.sms.entity.SmsChannel;
import com.yryz.quanhu.sms.service.SmsChannelService;
/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年7月24日 下午7:15:51
 * @Description 短信通道管理
 */
@Service
public class SmsChannelServiceImpl implements SmsChannelService {
	private static final Logger logger = LoggerFactory.getLogger(SmsChannelServiceImpl.class);
	
	@Autowired
	private SmsChannelDao dao;
	
	@Override
	public int save(SmsChannel channel) {
		try {
			channel.setCreateDate(new Date());
			channel.setUpdateDate(channel.getCreateDate());
			return dao.save(channel);
		} catch (Exception e) {
			logger.error("[SmsChannelDao.save]",e);
			throw new MysqlOptException(e);
		}
	}
	@Override
	public int update(SmsChannel channel) {
		try {
			channel.setUpdateDate(new Date());
			return dao.update(channel);
		} catch (Exception e) {
			logger.error("[SmsChannelDao.update]",e);
			throw new MysqlOptException(e);
		}
	}
	@Override
	public int delete(Integer id) {
		try {
			return dao.delete(id);
		} catch (Exception e) {
			logger.error("[SmsChannelDao.delete]",e);
			throw new MysqlOptException(e);
		}
	}
	@Override
	public SmsChannel get(Integer id) {
		try {
			return dao.get(id);
		} catch (Exception e) {
			logger.error("[SmsChannelDao.get]",e);
			throw new MysqlOptException(e);
		}
	}
	@Override
	public SmsChannel getByParams(String channel, String smsSign) {
		try {
			return dao.getByParams(channel, smsSign);
		} catch (Exception e) {
			logger.error("[SmsChannelDao.getByParams]",e);
			throw new MysqlOptException(e);
		}
	}
	@Override
	public List<SmsChannel> listChannel() {
		try {
			return dao.listChannel();
		} catch (Exception e) {
			logger.error("[SmsChannelDao.listChannel]",e);
			throw new MysqlOptException(e);
		}
	}
}
