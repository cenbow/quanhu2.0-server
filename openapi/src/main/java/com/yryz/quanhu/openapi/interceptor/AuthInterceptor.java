package com.yryz.quanhu.openapi.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.Annotation.Login;
import com.yryz.common.Annotation.NotLogin;
import com.yryz.common.constant.AppConstants;
import com.yryz.common.constant.DevType;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.entity.RequestHeader;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.utils.StringUtils;
import com.yryz.common.utils.WebUtil;
import com.yryz.quanhu.user.contants.TokenCheckEnum;
import com.yryz.quanhu.user.dto.AuthRefreshDTO;
import com.yryz.quanhu.user.dto.AuthTokenDTO;
import com.yryz.quanhu.user.service.AuthApi;

public class AuthInterceptor extends HandlerInterceptorAdapter implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
	
	@Reference
	private AuthApi authAPi;

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
			checkToken(request);
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
	
	/**
	 * 检查token
	 * @param request
	 */
	private void checkToken(HttpServletRequest request){
		String userId = request.getParameter(AppConstants.USER_ID);
		RequestHeader header = WebUtil.getHeader(request);
		String token = header.getToken();
		if(StringUtils.isEmpty(userId)){
			throw new QuanhuException(ExceptionEnum.NEEDTOKEN);
		}
		if(StringUtils.isEmpty(token)){
			throw new QuanhuException(ExceptionEnum.NEEDTOKEN);
		}
		DevType devType = DevType.getEnumByType(header.getDevType(), header.getUserAgent());
		Integer checkEnum = TokenCheckEnum.SUCCESS.getStatus();
		try {
			if (devType != DevType.ANDROID && devType != DevType.IOS) {
				AuthTokenDTO tokenDTO = new AuthTokenDTO(userId, devType, header.getAppId(),header.getToken());
				checkEnum = authAPi.checkToken(tokenDTO).getData();
			} else {
				AuthRefreshDTO refreshDTO = new AuthRefreshDTO(null, false);
				refreshDTO.setAppId(header.getAppId());
				refreshDTO.setUserId(userId);
				refreshDTO.setType(devType);
				refreshDTO.setToken(header.getToken());
				checkEnum = authAPi.checkToken(refreshDTO).getData();
			}
		} catch (Exception e) {
			logger.error("token校验异常",e);
		}
		
		if(checkEnum == TokenCheckEnum.SUCCESS.getStatus()){
		}else{
			if(checkEnum == TokenCheckEnum.INVALID.getStatus()){
				throw new QuanhuException(ExceptionEnum.TOKEN_INVALID);
			}
			if(checkEnum == TokenCheckEnum.EXPIRE.getStatus()){
				throw new QuanhuException(ExceptionEnum.TOKEN_EXPIRE);
			}
			if(checkEnum == TokenCheckEnum.NO_TOKEN.getStatus()){
				throw new QuanhuException(ExceptionEnum.NO_TOKEN);
			}
			if(checkEnum == TokenCheckEnum.ERROR.getStatus()){
				throw new QuanhuException(ExceptionEnum.NEEDTOKEN);
			}
		}
	}
}
