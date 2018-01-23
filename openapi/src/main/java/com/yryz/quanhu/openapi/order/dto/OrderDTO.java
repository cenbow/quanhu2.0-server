package com.yryz.quanhu.openapi.order.dto;

import java.io.Serializable;

/**
 * 非现金订单信息
 * 
 * @author yehao
 *
 */
@SuppressWarnings("serial")
public class OrderDTO implements Serializable {
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 订单金额
	 */
	private Long orderAmount;
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 币种 156：人民币
	 * 
	 * 840：美元
	 * 
	 * 344：港币
	 * 
	 * 392：日元
	 * 
	 * 978：欧元
	 * 
	 * 458：马来西亚吉特
	 * 
	 * 032：澳元
	 * 
	 */
	private String orderCurrency;
	/**
	 * 订单提交时间
	 */
	private String orderDatetime;
	/**
	 * 扩展参数
	 */
	private Object ext;

	public OrderDTO() {
		super();
	}

	public OrderDTO(String orderId, Long orderAmount, String productName, String orderCurrency, String orderDatetime,
			Object ext) {
		super();
		this.orderId = orderId;
		this.orderAmount = orderAmount;
		this.productName = productName;
		this.orderCurrency = orderCurrency;
		this.orderDatetime = orderDatetime;
		this.ext = ext;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getOrderCurrency() {
		return orderCurrency;
	}

	public void setOrderCurrency(String orderCurrency) {
		this.orderCurrency = orderCurrency;
	}

	public String getOrderDatetime() {
		return orderDatetime;
	}

	public void setOrderDatetime(String orderDatetime) {
		this.orderDatetime = orderDatetime;
	}

	public Object getExt() {
		return ext;
	}

	public void setExt(Object ext) {
		this.ext = ext;
	}

	@Override
	public String toString() {
		return "OrderVO [orderId=" + orderId + ", orderAmount=" + orderAmount + ", productName=" + productName
				+ ", orderCurrency=" + orderCurrency + ", orderDatetime=" + orderDatetime + ", ext=" + ext + "]";
	}

}
