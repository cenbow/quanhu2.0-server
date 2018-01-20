/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月19日
 * Id: PushReceived.java, 2017年9月19日 上午9:58:10 Administrator
 */
package com.yryz.quanhu.message.push.entity;

import java.io.Serializable;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年9月19日 上午9:58:10
 * @Description jpush推送状态报告
 */
@SuppressWarnings("serial")
public class PushReceived implements Serializable{
	/**
    * msgId
    */
    public String msg_id;
    /**
     * 安卓接收数量
     */
    public int android_received;
    /**
     * ios apns 发送数量
     */
    public int ios_apns_sent;
    /**
     * ios 接收数量
     */
    public int ios_msg_received;
    /**
     * wp mpns 发送数量
     */
    public int wp_mpns_sent;
	public String getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}
	public int getAndroid_received() {
		return android_received;
	}
	public void setAndroid_received(int android_received) {
		this.android_received = android_received;
	}
	public int getIos_apns_sent() {
		return ios_apns_sent;
	}
	public void setIos_apns_sent(int ios_apns_sent) {
		this.ios_apns_sent = ios_apns_sent;
	}
	public int getIos_msg_received() {
		return ios_msg_received;
	}
	public void setIos_msg_received(int ios_msg_received) {
		this.ios_msg_received = ios_msg_received;
	}
	public int getWp_mpns_sent() {
		return wp_mpns_sent;
	}
	public void setWp_mpns_sent(int wp_mpns_sent) {
		this.wp_mpns_sent = wp_mpns_sent;
	}
}
