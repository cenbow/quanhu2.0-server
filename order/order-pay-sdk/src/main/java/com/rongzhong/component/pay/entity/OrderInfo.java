package com.rongzhong.component.pay.entity;

import java.math.BigDecimal;

/**
 * 订单信息
 * @author Administrator
 *
 */
public class OrderInfo {
	private String sn; // 交易流水号
	private Long orderAmount; // 订单金额（人民币单位为分）
	private String orderCurrency;  // 币种
	private String orderDatetime; // 订单提交时间
	private String productName; // 商品名称
	private String payerName; // 付款人名称
	private String productId;  // 商品id
	private String payerId;   // 付款人id
	
	public Long getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPayerName() {
		return payerName;
	}
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getPayerId() {
		return payerId;
	}
	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}
	
}
