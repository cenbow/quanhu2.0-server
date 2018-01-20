package com.yryz.quanhu.order.score.type;

/**
 * 事件队列名称枚举
 * 
 * @author lsn
 *
 */
public enum EventQueueEnum {

	QUEUE_EVENT("event.log", "事件日志收集队列，用于统计分析"), 
	QUEUE_EVENT_SCORE("event.score","积分事件队列"), 
	QUEUE_EVENT_HOTSPOT("event.hotspot", "热度计算事件队列"), 
	QUEUE_EVENT_GROW("event.grow", "成长度计算事件队列"),
	QUEUE_HOTSPOT_USER("hotspot.user", "用户热度通知"),
	QUEUE_HOTSPOT_CIRCLE("hotspot.circle", "圈子热度通知"),
	QUEUE_HOTSPOT_RESOURCE("hotspot.resource", "资源热度通知"),
	QUEUE_HOTSPOT_COTERIE("hotspot.coterie", "私圈热度通知");


	private String queueName;

	private String queueDesp;

	EventQueueEnum(String queueName, String queueDesp) {
		this.queueName = queueName;
		this.queueDesp = queueDesp;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getQueueDesp() {
		return queueDesp;
	}

	public void setQueueDesp(String queueDesp) {
		this.queueDesp = queueDesp;
	}

}
