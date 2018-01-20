/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年8月10日
 * Id: SmsChannelDao.java, 2017年8月10日 下午5:16:52 Administrator
 */
package com.yryz.quanhu.message.sms.dao;

import org.apache.ibatis.annotations.Mapper;
import com.yryz.quanhu.message.sms.entity.SmsChannel;
/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年7月24日 下午7:01:19
 * @Description 短信通道
 */
@Mapper
public interface SmsChannelDao {
	
	int insert(SmsChannel record);
	
	SmsChannel selectOne(Long kid);
		
	/**
	 * 
	 * 短信通道更新
	 * @param channel
	 * @return
	 */
	public int update(SmsChannel channel);
	
}