/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年8月10日
 * Id: SmsChannelService.java, 2017年8月10日 下午5:29:08 Administrator
 */
package com.yryz.quanhu.sms.service;
import java.util.List;

import com.yryz.quanhu.sms.entity.SmsChannel;
import com.yryz.quanhu.sms.entity.SmsSign;
/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年7月24日 下午7:12:44
 * @Description 短信通道管理
 */
public interface SmsChannelService {
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
	 * 短信通道获取
	 * @param id
	 * @return
	 */
	public SmsChannel get(Long channelId);
	/**
	 * 获取签名
	 * @param signId
	 * @return
	 */
	public SmsSign getSign(Long signId);
	
	/**
	 * 
	 * 短信通道查询
	 * @return
	 */
	public List<SmsChannel> listChannel();
}
