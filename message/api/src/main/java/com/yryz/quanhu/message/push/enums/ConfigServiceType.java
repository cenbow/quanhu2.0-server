/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年12月5日
 * Id: ConfigServiceType.java, 2017年12月5日 下午3:41:50 Administrator
 */
package com.yryz.quanhu.message.push.enums;

/**
 * 系统配置业务类型
 * @author danshiyu
 * @version 1.0
 * @date 2017年12月5日 下午3:41:50
 */
public enum ConfigServiceType {
	/**
	 * 启动页广告
	 */
	INDEXADVERT(0,"indexAdvert"),
	/**
	 * ios强制升级配置
	 */
	UPGRADE(1,"upgrade_ios"),
	/**
	 * 安卓强制升级配置
	 */
	UPGRADEANDROID(2,"upgrade_android"),
	/**
	 * 客服工作时间配置
	 */
	CUSTOM_WORK_TIME(3,"custom_work_time"),
	/**
	 * 验证码配置(按业务区分)
	 */
	VERIFY_CODE_CONFIG(4,"verify_code_config"),
	/**
	 * ip限制配置（按业务区分）
	 */
	IP_LIMIT_CONFIG(5,"ip_limit_config"),
	/**
	 * 邮件发送配置
	 */
	EMAIL_SEND(6,"email_send"),
	/**
	 * 短信发送配置
	 */
	SMS_SEND(7,"sms_send"),
	/**
	 * 推送配置
	 */
	PUSH_CONFIG(8,"push_config");
	
	private int type;
	private String name;

	ConfigServiceType(int type,String name) {
		this.type = type;
		this.name = name;
	}
	public int getType(){
		return this.type;
	}
	
	public String getName(){
		return this.name;
	}
}
