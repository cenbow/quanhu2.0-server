/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: CommonInterceptor.java, 2018年1月18日 下午4:18:01 yehao
 */
package com.yryz.quanhu.openapi.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.alibaba.dubbo.rpc.RpcContext;
import com.yryz.common.constant.CommonConstants;
import com.yryz.common.context.Context;
import com.yryz.common.utils.StringUtils;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 下午4:18:01
 * @Description 通用拦截器
 */
public class CommonInterceptor extends HandlerInterceptorAdapter implements HandlerInterceptor {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Method", "POST, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "Origin, Content-Encoding ,Accept, X-Requested-With, "
				+ "Content-Type, X-Forwarded-For , Proxy-Client-IP , WL-Proxy-Client-IP , HTTP_CLIENT_IP , "
				+ "HTTP_X_FORWARDED_FOR , sign, token ,appVersion, v , devType ,devName , devId ,ip ,net ,custId,appId,appSecret,"
				+ "appversion,appid,devtype,devname,devid,custid");
		response.setContentType("text/json;charset=utf-8"); 
		System.out.println("hello CommonInterceptor ..." + request.getRequestURI());
		String httpMethod=request.getMethod();
		if(RequestMethod.OPTIONS.name().equals(httpMethod)){
//			Cat.logEvent("Method", RequestMethod.OPTIONS.name());
			return false;
		}
		//从request header中获取appid存入RpcContext,直接传递到dubbo服务
		String appId = request.getHeader(CommonConstants.APP_ID);
		if(StringUtils.isBlank(appId)){
			appId = Context.getProperty("appId.default");
		}
		request.setAttribute(CommonConstants.APP_ID, appId);
		RpcContext.getContext().setAttachment(CommonConstants.APP_ID, appId);
		//RpcContext.getContext().setAttachment(Constants.APP_SECRET, request.getHeader(Constants.APP_SECRET));
		RpcContext.getContext().setAttachment(CommonConstants.DEVICE_ID, request.getHeader(CommonConstants.DEVICE_ID));
		RpcContext.getContext().setAttachment(CommonConstants.DEV_TYPE, request.getHeader(CommonConstants.DEV_TYPE));
//		//Cat埋点
//		CatLocal.get(request.getRequestURI());

		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, 
			ModelAndView modelAndView) throws Exception {
		
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object handler, Exception ex) throws Exception {
//		CatLocal.complete();
	}

}
