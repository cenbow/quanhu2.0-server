/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月20日
 * Id: PushParamsDTO.java, 2017年9月20日 上午11:44:11 Administrator
 */
package com.yryz.quanhu.message.push.entity;


import java.io.Serializable;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年9月20日 上午11:44:11
 * @Description 推送开关
 */
@SuppressWarnings("serial")
public class PushParamsDTO implements Serializable {
	
	private String appKey;
	
	private String appSecret;
	
	/**
	 * 推送类型 @see {@link CommonPushType}
	 */
	private String pushType;
	
	/**
	 * 要推送的用户ids
	 */
	private String to;
	
	/**
	 * app通知栏消息
	 */
	private String notification;
	
	/**
	 * 通知body文字
	 */
	private String notificationBody;
	
	/**
	 * app端接收的自定义消息
	 */
	private String msg;
	
	/**
	 * 通知栏开关 默认开启
	 */
	private Boolean notifyStatus = true;
	
	/**
	 * 透传消息开关 默认开启
	 */
	private Boolean msgStatus = true;
	
	/**
	 * 推送环境 true:生产 false:测试 
	 */
	private Boolean pushEvn = true;
	
	/**
	 * 
	 * @exception 
	 */
	public PushParamsDTO() {
		super();
	}

	/**
	 * @param appKey
	 * @param appSecret
	 * @param pushType
	 * @param to
	 * @param notification
	 * @param msg
	 * @param notifyStatus
	 * @param msgStatus
	 * @exception 
	 */
	public PushParamsDTO(String appKey, String appSecret, String pushType, String to, String notification, String msg,
			Boolean notifyStatus, Boolean msgStatus,String notificationBody,boolean pushEvn) {
		super();
		this.appKey = appKey;
		this.appSecret = appSecret;
		this.pushType = pushType;
		this.to = to;
		this.notification = notification;
		this.msg = msg;
		this.notifyStatus = notifyStatus;
		this.msgStatus = msgStatus;
		this.notificationBody = notificationBody;
		this.pushEvn = pushEvn;
	}

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getPushType() {
		return pushType;
	}

	public void setPushType(String pushType) {
		this.pushType = pushType;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Boolean getNotifyStatus() {
		return notifyStatus;
	}

	public void setNotifyStatus(Boolean notifyStatus) {
		this.notifyStatus = notifyStatus == null ? false : notifyStatus;
	}

	public Boolean getMsgStatus() {
		return msgStatus;
	}

	public void setMsgStatus(Boolean msgStatus) {
		this.msgStatus = msgStatus == null ? true : msgStatus;
	}

	public String getNotificationBody() {
		return notificationBody;
	}

	public void setNotificationBody(String notificationBody) {
		this.notificationBody = notificationBody;
	}

	public Boolean getPushEvn() {
		return pushEvn;
	}

	public void setPushEvn(Boolean pushEvn) {
		this.pushEvn = pushEvn;
	}
	
}
