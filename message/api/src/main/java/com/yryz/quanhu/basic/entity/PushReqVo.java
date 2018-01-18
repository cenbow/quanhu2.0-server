/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年8月15日
 * Id: PushReqVo.java, 2017年8月15日 下午2:10:41 Administrator
 */
package com.yryz.quanhu.basic.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年8月15日 下午2:10:41
 * @Description 推送请求参数
 */
@SuppressWarnings("serial")
public class PushReqVo implements Serializable{
	
	private CommonPushType pushType;
	
	/**
	 * 要推送的用户ids
	 */
	private List<String> custIds;
	
	/**
	 * 要推送的tags
	 */
	private List<String> tags;
	
	/**
	 * 推送设备号ids
	 */
	private List<String> registrationIds;
	
	/**
	 * app通知栏显示的文字
	 */
	private String notification;
	
	/**
	 * 通知body文字
	 */
	private String nofiticationBody;
	
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
	
	public CommonPushType getPushType() {
		return pushType;
	}

	public void setPushType(CommonPushType pushType) {
		this.pushType = pushType;
	}

	public List<String> getCustIds() {
		return custIds;
	}

	public void setCustIds(List<String> custIds) {
		this.custIds = custIds;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
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
		this.notifyStatus = notifyStatus;
	}

	public Boolean getMsgStatus() {
		return msgStatus;
	}

	public void setMsgStatus(Boolean msgStatus) {
		this.msgStatus = msgStatus;
	}

	public String getNofiticationBody() {
		return nofiticationBody;
	}

	public void setNofiticationBody(String nofiticationBody) {
		this.nofiticationBody = nofiticationBody;
	}

	public List<String> getRegistrationIds() {
		return registrationIds;
	}

	public void setRegistrationIds(List<String> registrationIds) {
		this.registrationIds = registrationIds;
	}

	/**
	 * 
	 * @author danshiyu
	 * @version 1.0
	 * @date 2017年8月15日 下午4:41:15
	 * @Description 推送类型
	 */
	public static enum CommonPushType{
		/**
		 * 全部设备广播
		 */
		BY_ALL("all"),
		/***
		 * 单用户推送
		 */
		BY_ALIAS("byAlias"),
		/***
		 * 批量用户推送
		 */
		BY_ALIASS("byAliass"),
		/**
		 * 单tag推送
		 */
		BY_TAG("byTag"),
		/**
		 * 批量tag推送
		 */
		BY_TAGS("byTags"),
		/**
		 * 设备号推送
		 */
		BY_REGISTRATIONID("byRegistrationId"),
		/**
		 * 批量设备号推送
		 */
		BY_REGISTRATIONIDS("byRegistrationIds"),
		/**
		 * 用户和设备号都推送
		 */
		@Deprecated
		BY_ALIAS_REGISTRATIONID("byAliasRegistrationId");
		
		private String pushType;
		
		CommonPushType(String pushType) {
			this.pushType = pushType;
		}
		
		public String getPushType(){
			return this.pushType;
		}
	}
}
