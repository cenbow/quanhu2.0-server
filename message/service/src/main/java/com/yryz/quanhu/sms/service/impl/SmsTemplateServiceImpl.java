/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年8月10日
 * Id: SmsTemplateServiceImpl.java, 2017年8月10日 下午5:33:11 Administrator
 */
package com.yryz.quanhu.sms.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yryz.common.exception.MysqlOptException;
import com.yryz.quanhu.sms.dao.SmsTemplateDao;
import com.yryz.quanhu.sms.entity.SmsTemplate;
import com.yryz.quanhu.sms.service.SmsTemplateService;
/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年7月24日 下午7:57:17
 * @Description 短信模板管理
 */
@Service
public class SmsTemplateServiceImpl implements SmsTemplateService {
	private static final Logger logger = LoggerFactory.getLogger(SmsTemplateServiceImpl.class);
	
	@Autowired
	private SmsTemplateDao dao;
	
	@Override
	public int save(SmsTemplate template) {
		try {
			template.setCreateTime(new Date());
			template.setUpdateTime(template.getCreateTime());
			return dao.save(template);
		} catch (Exception e) {
			logger.error("[SmsTemplateDao.save]",e);
			throw new MysqlOptException(e);
		}
	}
	@Override
	public int update(SmsTemplate template) {
		try {
			template.setUpdateTime(new Date());
			return dao.update(template);
		} catch (Exception e) {
			logger.error("[SmsTemplateDao.update]",e);
			throw new MysqlOptException(e);
		}
	}
	@Override
	public int delete(Integer id) {
		try {
			return dao.delete(id);
		} catch (Exception e) {
			logger.error("[SmsTemplateDao.delete]",e);
			throw new MysqlOptException(e);
		}
	}
	@Override
	public SmsTemplate get(Integer id) {
		try {
			return dao.get(id);
		} catch (Exception e) {
			logger.error("[SmsTemplateDao.get]",e);
			throw new MysqlOptException(e);
		}
	}
	@Override
	public SmsTemplate getByParams(String smsTemplateCode) {
		try {
			return dao.getByParams(smsTemplateCode);
		} catch (Exception e) {
			logger.error("[SmsTemplateDao.getByParams]",e);
			throw new MysqlOptException(e);
		}
	}
	@Override
	public List<SmsTemplate> listTemplate() {
		try {
			return dao.listTemplate();
		} catch (Exception e) {
			logger.error("[SmsTemplateDao.listTemplate]",e);
			throw new MysqlOptException(e);
		}
	}
}