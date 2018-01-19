/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年5月25日
 * Id: SmsDao.java, 2017年5月25日 下午1:48:00 wangzuzheng
 */
package com.yryz.quanhu.sms.dao;

import com.yryz.quanhu.sms.entity.SmsConfigModel;
import com.yryz.quanhu.sms.entity.SmsInfoModel;
import com.yryz.quanhu.sms.entity.SmsLogsModel;

/**
 * 
 * @author wangzuzheng
 * @version 1.0
 * @date 2017年5月25日 下午1:48:07
 * @Description 相关短信dao
 */
public interface SmsDao {
//	/**
//	 * 
//	 * @author wangzuzheng
//	 * @date 2017年5月25日
//	 * @param phone
//	 *            手机号
//	 * @param code
//	 * @param verifyCode
//	 * @Description 保存短信记录
//	 */
//	public void save(String phone, String code, String verifyCode);
	
	/**
	 * 
	 * @author wangzuzheng
	 * @date 2017年5月25日
	 * @param smsInfoModel
	 * @Description 保存短信
	 */
	public void save(SmsInfoModel smsInfoModel);

	/**
	 * 
	 * @author wangzuzheng
	 * @date 2017年5月25日
	 * @return
	 * @Description 查询短信配置
	 */
	public SmsConfigModel getConfig();

	/**
	 * 
	 * @author wangzuzheng
	 * @date 2017年5月25日
	 * @param phone
	 * @return
	 * @Description 按日期获取用户短信发送概况
	 */
	public SmsLogsModel getDayLogs(String phone);

	/**
	 * 
	 * @author wangzuzheng
	 * @date 2017年5月25日
	 * @param phone
	 * @param code
	 * @return
	 * @Description 查询短信类型
	 */
	public String getCodeType(String phone, String code);

	/**
	 * 
	 * @author wangzuzheng
	 * @date 2017年5月25日
	 * @param phone
	 * @param code
	 * @param type
	 * @param expire
	 * @Description 写入短信码、并更新日志
	 */
	public void setCodeExpire(String phone, String code, String type, int expire);

	/**
	 * 
	 * @author wangzuzheng
	 * @date 2017年5月25日
	 * @param phone
	 * @param code
	 * @return
	 * @Description 验证成功、并删除
	 */
	public boolean checkForDelete(String phone, String code);

}
