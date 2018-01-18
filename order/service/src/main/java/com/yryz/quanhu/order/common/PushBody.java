package com.yryz.quanhu.order.common;

import java.io.Serializable;

/**
 * 推送实体
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class PushBody implements Serializable{
	/**
	 * 消息ID
	 */
	private String msgId;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 栏目类型
	 */
	private int colType;
	/**
	 * 消息类型
	 */
	private int msgType;
	/**
	 * 发送人id
	 */
	private String from;
	/**
	 * 接收人id
	 */
	private String to;
	/**
	 * 发送时间
	 */
	private String sendTime;
	/**
	 * 主体内容
	 */
	private Object data;
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getMsgType() {
		return msgType;
	}
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public int getColType() {
		return colType;
	}
	public void setColType(int colType) {
		this.colType = colType;
	}
	
}
