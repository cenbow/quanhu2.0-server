/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月15日
 * Id: Constants.java, 2018年1月15日 下午12:52:17 Administrator
 */
package com.yryz.quanhu.user.contants;

/**
 * @author Administrator
 * @version 1.0
 * @date 2018年1月15日 下午12:52:17
 * @Description TODO (这里用一句话描述这个方法的作用)
 */
public class Constants {
	public static final String DISABLE_MSG = "";
	
	/**
	 * 昵称最大长度
	 */
	public static final int NICK_NAME_MAX_LENGTH = 10;
	/**
	 * 强制绑定手机号标识
	 */
	public static final String BIND_PHONE = "BIND_PHONE";
	/**
	 * 管理后台代理注册渠道
	 */
	public static final String ADMIN_REG_CHANNEL = "admin_agent";
	/**
	 * 管理后台马甲代理注册渠道
	 */
	public static final String ADMIN_REG_VEST_CHANNEL = "admin_vest_agent";
	/**
	 * 第三方应用appkey
	 */
	public static final String APP_KEY = "appKey";
	/**
	 * 禁言时间/小时
	 */
	public static final int NO_TALK_HOUR = 8;
	/**
	 * 警告禁言次数
	 */
	public static final int WARN_TIMES = 3;
	
	/**
	 * 第三方登录信息配置
	 */
	public static final String THIRD_LOGIN_CONFIG_NAME = "thirdLoginConfig";
	/**
	 * 登录认证配置
	 */
	public static final String AUTH_CONFIG_NAME = "userAuthConfig";
	
	/**
	 * 邀请注册配置
	 */
	public static final String INVITER_CONFIG_NAME = "inviterConfig";
	
    /**
	 * 头像审核状态
	 *
	 */
	public enum ImgAuditStatus{
		/** 待审核 */
		NO_AUDIT(10),
		/** 审核成功 */
		SUCCESS(11),
		/** 审核失败 */
		FAIL(12);
		
		private int status;
		
		ImgAuditStatus(int status) {
			this.status = status;
		}
		public int getStatus(){
			return status;
		}
	}
}
