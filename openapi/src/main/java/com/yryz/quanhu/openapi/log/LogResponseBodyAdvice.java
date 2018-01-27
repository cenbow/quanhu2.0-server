/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年10月13日
 * Id: LogResponseBodyAdvice.java, 2017年10月13日 上午10:24:36 yehao
 */
package com.yryz.quanhu.openapi.log;


import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.support.requestlog.entity.RequestLog;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年10月13日 上午10:24:36
 * @Description 日志记录器
 */
@ControllerAdvice
public class LogResponseBodyAdvice implements ResponseBodyAdvice<Object> {

	/**
	 * @param returnType
	 * @param converterType
	 * @return
	 * @see org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice#supports(org.springframework.core.MethodParameter, Class)
	 */
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		//return true
		return true;
	}

	/**
	 * @param body
	 * @param returnType
	 * @param selectedContentType
	 * @param selectedConverterType
	 * @param request
	 * @param response
	 * @return
	 * @see org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice#beforeBodyWrite(Object, org.springframework.core.MethodParameter, org.springframework.http.MediaType, Class, org.springframework.http.server.ServerHttpRequest, org.springframework.http.server.ServerHttpResponse)
	 */
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
		RequestLog log = RequestContext.getRequestLog();
		log.setResponseBody(GsonUtils.parseJson(body));
		return body;
	}

}
