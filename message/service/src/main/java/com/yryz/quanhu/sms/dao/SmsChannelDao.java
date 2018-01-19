/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年8月10日
 * Id: SmsChannelDao.java, 2017年8月10日 下午5:16:52 Administrator
 */
package com.yryz.quanhu.sms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yryz.quanhu.sms.entity.SmsChannel;
/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年7月24日 下午7:01:19
 * @Description 短信通道
 */
@Repository
public interface SmsChannelDao {
	
	/**
	 * 短信通道保存
	 * @param channel
	 */
	public int save(SmsChannel channel);
	
	/**
	 * 
	 * 短信通道更新
	 * @param channel
	 * @return
	 */
	public int update(SmsChannel channel);
	
	/**
	 * 
	 * 删除短信通道
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
	
	/**
	 * 
	 * 短信通道获取
	 * @param id
	 * @return
	 */
	public SmsChannel get(Integer id);
	
	/**
	 * 
	 * 获取短信通道
	 * @param channel
	 * @param smsSign
	 * @return
	 */
	public SmsChannel getByParams(@Param("channel")String channel,@Param("smsSign")String smsSign);
	
	/**
	 * 
	 * 短信通道查询
	 * @return
	 */
	public List<SmsChannel> listChannel();
}