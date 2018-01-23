/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年6月16日
 * Id: YryzPaySDK.java, 2017年6月16日 下午3:48:24 yehao
 */
package com.rongzhong.component.pay.api;

import java.io.IOException;
import java.util.SortedMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rongzhong.component.pay.alipay.Alipay;
import com.rongzhong.component.pay.entity.OrderInfo;
import com.rongzhong.component.pay.entity.PayOrder;
import com.rongzhong.component.pay.entity.PayResponse;
import com.rongzhong.component.pay.wxpay.Wxpay;
import com.rongzhong.component.pay.wxpay.entity.WxQRpay;
import com.rongzhong.component.pay.wxpay.entity.WxWapPay;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年6月16日 下午3:48:24
 * @Description 支付SDK
 */
public class YryzPaySDK {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(YryzPaySDK.class);
	
	/**
	 * 支付宝APP支付，构建服务参数，返回给APP端完成后续操作
	 * @param payOrder
	 * @return
	 * @throws IOException
	 */
	public static String buildAppAliPayOrder(OrderInfo orderInfo) throws IOException {
		String linkStr = "";
		try {
			linkStr = Alipay.buildAppAliPayOrder(orderInfo);
		} catch (IOException e) {
			LOGGER.warn("[buildAppAliPayOrder] Exception " , e );
		}
		return linkStr;
	}
	
	/**
	 * 微信APP支付，构建服务参数，返回给APP端完成后续操作
	 * @param payOrder
	 * @param ip
	 * @return
	 * @throws IOException
	 */
	public static SortedMap<String, String> buildAppWxPayOrder(OrderInfo orderInfo , String ip) throws IOException{
		try {
			SortedMap<String, String> wxReqMap = Wxpay.getAppPayReqMap(orderInfo, ip);
			return wxReqMap;
		} catch (Exception e) {
			LOGGER.warn("[buildAppWxPayOrder] Exception " , e );
		}
		return null;
	}
	
	/**
	 * 构建支付宝wap支付跳转临时页面，让前端执行页面跳转
	 * @param payOrder
	 * @param returnUrl 选填，有则可以执行业务跳转
	 * @return
	 * @throws IOException
	 */
	public static String buildWapAliPayHtml(OrderInfo orderInfo , String returnUrl) throws IOException {
		String requestHtml = "";
		try {
			requestHtml = Alipay.buildPayH5Request(orderInfo , returnUrl );
		} catch (IOException e) {
			LOGGER.warn("[buildWebAliPayHtml] Exception " , e );
		}
		return requestHtml;
	}
	
	/**
	 * 构建支付宝web支付跳转临时页面，让前端执行页面跳转
	 * @param payOrder
	 * @param returnUrl 选填，有则可以执行业务跳转
	 * @return
	 * @throws IOException
	 */
	public static String buildWebAliPayHtml(OrderInfo orderInfo , String returnUrl) throws IOException {
		String requestHtml = "";
		try {
			requestHtml = Alipay.buildPayPageRequest(orderInfo , returnUrl );
		} catch (IOException e) {
			LOGGER.warn("[buildWebAliPayHtml] Exception " , e );
		}
		return requestHtml;
	}
	
	/**
	 * 构建微信支付的跳转地址，让前端可以生成二维码
	 * @param payOrder
	 * @return
	 * @throws IOException
	 */
	public static String buildWebWxPayUrl(OrderInfo orderInfo , String ipAddress) throws IOException {
		try {
			WxQRpay wxQRpay = Wxpay.getWxQRpay(orderInfo, ipAddress);
			return wxQRpay.getCodeUrl();
		} catch (Exception e) {
			LOGGER.warn("[buildWebWxPayUrl] Exception " , e );
		}
		return null;
	}
	
	/**
	 * 构建wap页的微信支付
	 * @param orderInfo
	 * @param ipAddress
	 * @return
	 * @throws IOException
	 */
	public static WxWapPay buildWapWxPayOrder(OrderInfo orderInfo , String ipAddress) throws IOException {
		try {
			WxWapPay wxWapPay = Wxpay.getWxWapPay(orderInfo, ipAddress);
			return wxWapPay;
		} catch (Exception e) {
			LOGGER.warn("[buildWebWxPayUrl] Exception " , e );
		}
		return null;
	}
	
	public static PayResponse parseAliPayResult(HttpServletRequest request){
		try {
			PayResponse payResponse = Alipay.parsePayResult(request);
			return payResponse;
		} catch (IOException e) {
			LOGGER.warn("[parseAliPayResult] Exception " , e );
		}
		return null;
	}
	
	public static PayResponse parseWxPayResult(HttpServletRequest request){
		try {
			PayResponse payResponse = Wxpay.parsePayResult(request);
			return payResponse;
		} catch (Exception e) {
			LOGGER.warn("[parseWxPayResult] Exception " , e );
		}
		return null;
	}
	
	
	public static OrderInfo fromOrder(PayOrder payOrder) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setSn(payOrder.getOrderId());
		orderInfo.setOrderAmount(payOrder.getOrderAmount());
		orderInfo.setOrderCurrency(payOrder.getOrderCurrency());
		orderInfo.setOrderDatetime(payOrder.getOrderDatetime());
		orderInfo.setProductName(payOrder.getProductName());
		return orderInfo;
	}

}
