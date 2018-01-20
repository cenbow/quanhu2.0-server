/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年8月10日
 * Id: SmsTemplateService.java, 2017年8月10日 下午5:30:01 Administrator
 */
package com.yryz.quanhu.sms.service;

import java.util.List;

import com.yryz.quanhu.sms.entity.SmsTemplate;
/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年7月24日 下午7:14:32
 * @Description 短信模板管理
 */
public interface SmsTemplateService {
	/**
	 * 
	 * 短信模板新增
	 * @param template
	 * @return
	 */
	public int save(SmsTemplate template);
	
	/**
	 * 
	 * 短信模板更新
	 * @param template
	 * @return
	 */
	public int update(SmsTemplate template);
	
	/**
	 * 
	 * 删除短信模板
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
	
	/**
	 * 
	 * 获取短信模板
	 * @param id
	 * @return
	 */
	public SmsTemplate get(Long kid);
	
	/**
	 * 
	 * 获取短信模板
	 * @param smsTemplateCode
	 * @return
	 */
	public SmsTemplate getByParams(String smsTemplateCode);
	
	/**
	 * 
	 * 查询短信模板
	 * @return
	 */
	public List<SmsTemplate> listTemplate();
}