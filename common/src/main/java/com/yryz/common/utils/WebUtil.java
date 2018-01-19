package com.yryz.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yryz.common.constant.AppConstants;
import com.yryz.common.constant.DevType;
import com.yryz.common.context.Context;
import com.yryz.common.entity.RequestHeader;

/**
 * web信息获取工具
 * 
 * @author danshiyu
 *
 */
public class WebUtil {

	private final static Logger logger = LoggerFactory.getLogger(WebUtil.class);

	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip)) {
			return "";
		}
		// 多个路由时，取第一个非unknown的ip
		final String[] arr = ip.split(",");
		for (final String str : arr) {
			if (!"unknown".equalsIgnoreCase(str)) {
				ip = str;
				break;
			}
		}
		return ip;
	}

	public static String getLocalIp() {
		String ip = "";
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return ip;
	}

	/**
	 * 设置header
	 * 
	 * @param params
	 * @return
	 */
	public static RequestHeader getHeader(HttpServletRequest request) {
		RequestHeader header = new RequestHeader();
		Method[] methods = header.getClass().getMethods();
		try {
			for (Method m : methods) {
				String methodName = m.getName();
				if (!methodName.startsWith("set")) {
					continue;
				}
				methodName = methodName.substring(3);
				// 获取属性名称
				methodName = methodName.substring(0, 1).toLowerCase() + methodName.substring(1);

				if (methodName.equalsIgnoreCase("class")) {
					continue;
				}
				String value = request.getHeader(methodName);

				m.invoke(header, new Object[] { value });
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			header.setAppId(Context.getProperty(AppConstants.APP_ID));
			header.setDevType(DevType.IOS.getType());
		}
		return header;
	}
}
