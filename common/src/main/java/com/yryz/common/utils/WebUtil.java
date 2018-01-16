package com.yryz.common.utils;

import java.net.InetAddress;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yryz.common.entity.RequestHeader;

/**
 * web信息获取工具
 * 
 * @author danshiyu
 *
 */
public class WebUtil {

	private final static Logger logger = LoggerFactory.getLogger(WebUtil.class);

	public static String getClientIP(Map<String, Object> params) {
		String ip = params.get("header-x-forwarded-for") == null ? null
				: params.get("header-x-forwarded-for").toString();
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = params.get("header-Proxy-Client-IP") == null ? null : params.get("header-Proxy-Client-IP").toString();
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = params.get("header-WL-Proxy-Client-IP") == null ? null
					: params.get("header-WL-Proxy-Client-IP").toString();
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
	 * 根据网关入参设置header
	 * 
	 * @param params
	 * @return
	 */
	public static RequestHeader getHeader(Map<String, Object> params) {
		RequestHeader header = new RequestHeader();
		if (MapUtils.isEmpty(params)) {
			return header;
		}
		header.setAppId(params.get("header-appId") == null ? null : params.get("header-appId").toString());
		header.setAppVersion(
				params.get("header-appVersion") == null ? null : params.get("header-appVersion").toString());
		header.setDevId(params.get("header-devId") == null ? null : params.get("header-devId").toString());
		header.setDevName(params.get("header-devName") == null ? null : params.get("header-devName").toString());
		header.setDevType(params.get("header-devType") == null ? null : params.get("header-devType").toString());
		header.setDitchCode(params.get("header-ditchCode") == null ? null : params.get("header-ditchCode").toString());
		header.setNet(params.get("header-net") == null ? null : params.get("header-net").toString());
		header.setSign(params.get("header-sign") == null ? null : params.get("header-sign").toString());
		header.setToken(params.get("header-token") == null ? null : params.get("header-token").toString());
		header.setRefreshToken(
				params.get("header-refreshToken") == null ? null : params.get("header-refreshToken").toString());
		header.setUserAgent(
				params.get("header-User-Agent") == null ? null : params.get("header-User-Agent").toString());
		header.setUserId(params.get("header-userId") == null ? null : params.get("header-userId").toString());
		return header;
	}
}
