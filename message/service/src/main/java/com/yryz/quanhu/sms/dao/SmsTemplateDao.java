/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年8月10日
 * Id: SmsTemplateDao.java, 2017年8月10日 下午5:17:58 Administrator
 */
package com.yryz.quanhu.sms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yryz.quanhu.sms.entity.SmsTemplate;
/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年7月24日 下午7:07:13
 * @Description 短信模板
 */
@Repository
public interface SmsTemplateDao {
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
	public SmsTemplate get(Integer id);
	
	/**
	 * 
	 * 获取短信模板
	 * @param smsTemplateCode
	 * @return
	 */
	public SmsTemplate getByParams(@Param("smsTemplateCode")String smsTemplateCode);
	
	/**
	 * 
	 * 查询短信模板
	 * @return
	 */
	public List<SmsTemplate> listTemplate();
}