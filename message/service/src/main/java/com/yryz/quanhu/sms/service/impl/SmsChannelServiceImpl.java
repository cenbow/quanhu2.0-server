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
import com.yryz.quanhu.sms.dao.SmsSignDao;
import com.yryz.quanhu.sms.entity.SmsChannel;
import com.yryz.quanhu.sms.entity.SmsSign;
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
	
	@Autowired
	private SmsSignDao signDao;
	
	@Override
	public int save(SmsChannel channel) {
		try {
			channel.setCreateDate(new Date());
			return dao.insert(channel);
		} catch (Exception e) {
			logger.error("[SmsChannelDao.save]",e);
			throw new MysqlOptException(e);
		}
	}
	@Override
	public int update(SmsChannel channel) {
		try {
			return dao.update(channel);
		} catch (Exception e) {
			logger.error("[SmsChannelDao.update]",e);
			throw new MysqlOptException(e);
		}
	}

	@Override
	public SmsChannel get(Long channelId) {
		try {
			return dao.selectOne(channelId);
		} catch (Exception e) {
			logger.error("[SmsChannelDao.get]",e);
			throw new MysqlOptException(e);
		}
	}
	@Override
	public SmsSign getSign(Long signId) {
		return signDao.selectOne(signId);
	}
	@Override
	public List<SmsChannel> listChannel() {
		// TODO Auto-generated method stub
		return null;
	}


}
