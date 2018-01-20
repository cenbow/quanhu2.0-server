/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年8月10日
 * Id: PushService.java, 2017年8月10日 下午6:18:36 Administrator
 */
package com.yryz.quanhu.message.push.service;



import com.yryz.quanhu.message.push.entity.PushReceived;
import com.yryz.quanhu.message.push.entity.PushReqVo;

import java.util.List;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年8月10日 下午6:18:36
 * @Description 推送业务
 */
public interface PushService {
	/**
	 * 
	 * 根据用户id推送
	 * 
	 * @param userId
	 * @param notification
	 * @param msg
	 */
	@Deprecated
	public void sendByAlias(String userId, String notification, String msg);

	/**
	 * 
	 * tag推送
	 * 
	 * @param tag
	 * @param notification
	 * @param msg
	 */
	@Deprecated
	public void sendByTag(String tag, String notification, String msg);

	/**
	 * 
	 * 批量用户推送
	 * 
	 * @param userIds
	 * @param notification
	 * @param msg
	 */
	@Deprecated
	public void sendByAlias(List<String> userIds, String notification, String msg);

	/**
	 * 
	 * 批量tag推送
	 * 
	 * @param tags
	 * @param notification
	 * @param msg
	 */
	@Deprecated
	public void sendByTag(List<String> tags, String notification, String msg);

	/**
	 * 
	 * 通用jpush推送业务<br/>
	 * 支持custId别名、tag群推、registrationId设备号推送，custId别名设备号混合推送<br/>
	 * 选择设备号推送时，存在设备号就按设备号推送，否则不推送<br/>
	 * 只有选择设备号和别名同时推送时，优先按设备号推送，设备号不存在时，用别名推送
	 * @param reqVo
	 */
	public void commonSendAlias(PushReqVo reqVo);
	
	/**
	 * 获取jpush推送报告
	 * @author danshiyu
	 * @date 2017年9月19日
	 * @param msgIds
	 * @return
	 * @Description 建议定时拉取
	 */
	public List<PushReceived> getPushReceived(List<String> msgIds);
}
