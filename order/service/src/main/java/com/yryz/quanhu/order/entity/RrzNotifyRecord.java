/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: RrzNotifyRecord.java, 2018年1月18日 上午9:56:41 yehao
 */
package com.yryz.quanhu.order.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 上午9:56:41
 * @Description 通知记录
 */
public class RrzNotifyRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3842188668677782560L;
	
	private String recordId;

    /** 通知次数 **/
    private Integer notifyTimes = 0;

    /** 限制通知次数 **/
    private Integer limitNotifyTimes = 5;

    /** 通知URL **/
    private String url;
    
    /** 回调参数 */
    private String msg;

    /** 商户订单号 **/
    private String orderId;
    
    /** 创建时间 */
	private Date createTime;

    /** 最后一次通知时间 **/
    private Date lastNotifyTime;

	/**
	 * 
	 * @exception 
	 */
	public RrzNotifyRecord() {
		super();
	}

	/**
	 * @param recordId
	 * @param notifyTimes
	 * @param limitNotifyTimes
	 * @param url
	 * @param msg
	 * @param orderId
	 * @param createTime
	 * @param lastNotifyTime
	 * @exception 
	 */
	public RrzNotifyRecord(String recordId, Integer notifyTimes, Integer limitNotifyTimes, String url, String msg,
			String orderId, Date createTime, Date lastNotifyTime) {
		super();
		this.recordId = recordId;
		this.notifyTimes = notifyTimes;
		this.limitNotifyTimes = limitNotifyTimes;
		this.url = url;
		this.msg = msg;
		this.orderId = orderId;
		this.createTime = createTime;
		this.lastNotifyTime = lastNotifyTime;
	}

	/**
	 * @return the recordId
	 */
	public String getRecordId() {
		return recordId;
	}

	/**
	 * @param recordId the recordId to set
	 */
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	/**
	 * @return the notifyTimes
	 */
	public Integer getNotifyTimes() {
		return notifyTimes;
	}

	/**
	 * @param notifyTimes the notifyTimes to set
	 */
	public void setNotifyTimes(Integer notifyTimes) {
		this.notifyTimes = notifyTimes;
	}

	/**
	 * @return the limitNotifyTimes
	 */
	public Integer getLimitNotifyTimes() {
		return limitNotifyTimes;
	}

	/**
	 * @param limitNotifyTimes the limitNotifyTimes to set
	 */
	public void setLimitNotifyTimes(Integer limitNotifyTimes) {
		this.limitNotifyTimes = limitNotifyTimes;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the lastNotifyTime
	 */
	public Date getLastNotifyTime() {
		return lastNotifyTime;
	}

	/**
	 * @param lastNotifyTime the lastNotifyTime to set
	 */
	public void setLastNotifyTime(Date lastNotifyTime) {
		this.lastNotifyTime = lastNotifyTime;
	}
	
}
