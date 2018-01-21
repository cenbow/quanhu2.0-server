package com.yryz.quanhu.openapi.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yryz.common.Annotation.Login;
import com.yryz.common.Annotation.NotLogin;
import com.yryz.common.exception.QuanhuException;
import com.yryz.quanhu.openapi.service.AuthService;

public class AuthInterceptor extends HandlerInterceptorAdapter implements HandlerInterceptor {
	
	@Autowired
	private AuthService authService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Method", "POST, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers",
				"Origin, Content-Encoding ,Accept, X-Requested-With, "
						+ "Content-Type, X-Forwarded-For , Proxy-Client-IP , WL-Proxy-Client-IP , HTTP_CLIENT_IP , "
						+ "HTTP_X_FORWARDED_FOR , sign, token ,appVersion, v , devType ,devName , devId ,ip ,net ,userId,appId,appSecret,ditchCode,"
						+ "appversion,appid,devtype,devname,devid,custid");
		response.setContentType("text/json;charset=utf-8");
		String httpMethod = request.getMethod();
		if (RequestMethod.OPTIONS.name().equals(httpMethod)) {
			return false;
		}
		try {
			if(handler instanceof HandlerMethod){
				Login login = ((HandlerMethod)handler).getMethodAnnotation(Login.class);
				if(login != null){ //如果自带notLogin方法，就不检查登录状态
				} else {
					NotLogin notLogin = ((HandlerMethod)handler).getMethodAnnotation(NotLogin.class);
					if(notLogin != null){ //如果自带notLogin方法，就不检查登录状态
						return false;
					}
				}
			}
			authService.checkToken(request);
		} catch (QuanhuException e) {
			PrintWriter out = response.getWriter();
			out.append(String.format("{\"code\":\"%s\",\"msg\":\"%s\",\"errorMsg\":\"%s\"}", e.getCode(),e.getMsg(),e.getErrorMsg()));
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// CatLocal.complete();
	}
}
