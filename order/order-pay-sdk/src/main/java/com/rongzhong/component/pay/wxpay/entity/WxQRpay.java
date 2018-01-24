package com.rongzhong.component.pay.wxpay.entity;

/**
 * 微信扫码支付相关信息
 * @author Administrator
 *
 */
public class WxQRpay {
	private String TradeType; // 交易类型
	private String prepayId;  // 预支付交易会话标识
	private String codeUrl;   // 二维码链接
	
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
	public String getCodeUrl() {
		return codeUrl;
	}
	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}
	
	
}
