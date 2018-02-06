/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年6月23日
 * Id: WxWapPay.java, 2017年6月23日 上午8:59:59 yehao
 */
package com.rongzhong.component.pay.wxpay.entity;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年6月23日 上午8:59:59
 * @Description 微信支付
 */
public class WxWapPay {
	
	private String TradeType; // 交易类型
	
	private String prepayId;  // 预支付交易会话标识
	
	private String packageId; // 包ID
	
	private String mwebUrl;	  // 跳转地址
	
	public WxWapPay() {
		super();
	}

	public WxWapPay(String tradeType, String prepayId, String packageId, String mwebUrl) {
		super();
		TradeType = tradeType;
		this.prepayId = prepayId;
		this.packageId = packageId;
		this.mwebUrl = mwebUrl;
	}

	public String getTradeType() {
		return TradeType;
	}

	public void setTradeType(String tradeType) {
		TradeType = tradeType;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getMwebUrl() {
		return mwebUrl;
	}

	public void setMwebUrl(String mwebUrl) {
		this.mwebUrl = mwebUrl;
	}
	
	

}
