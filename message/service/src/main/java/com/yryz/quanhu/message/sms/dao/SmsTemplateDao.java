/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年8月10日
 * Id: SmsTemplateDao.java, 2017年8月10日 下午5:17:58 Administrator
 */
package com.yryz.quanhu.message.sms.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yryz.quanhu.message.sms.entity.SmsTemplate;
/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年7月24日 下午7:07:13
 * @Description 短信模板
 */
@Mapper
public interface SmsTemplateDao {
	int insert(SmsTemplate record);
	
	/**
	 * 
	 * 获取短信模板
	 * @param id
	 * @return
	 */
	public SmsTemplate selectOne(Long kid);

	/**
	 * 
	 * 短信模板更新
	 * @param template
	 * @return
	 */
	public int update(SmsTemplate template);
}