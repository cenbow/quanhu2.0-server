package com.yryz.quanhu.user.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.utils.HTTPWeb;
import com.yryz.common.utils.JsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.user.contants.ThirdConstants;
import com.yryz.quanhu.user.vo.ThirdUser;
import com.yryz.quanhu.user.vo.WeiboToken;
import com.yryz.quanhu.user.vo.WeiboUser;

/**
 * 微博第三方登录校验<br/>
 * 1.获取微博用户信息<br/>
 * 2.获取微博web第三方登录认证地址<br/>
 * 3.获取微博web第三方登录token
 * 
 * @author danshiyu
 * @version 2017-11-09
 */
public class OatuhWeibo {
	private static final Logger logger = LoggerFactory.getLogger(OatuhWeibo.class);
	private static final Logger thirdLogger = LoggerFactory.getLogger("third.logger");
	/**
	 * 获取微博用户信息
	 * 
	 * @param openId
	 * @param token
	 * @return
	 * @throws Exception
	 * @Description 获取微博用户信息返回统一的第三方对象
	 */
	public static ThirdUser getUser(String openId, String token){
		ThirdUser user = new ThirdUser();
		String url = ThirdConstants.WB_GET_USER_URL;

		Map<String, Object> map = new HashMap<>(2);
		map.put("access_token", token);
		map.put("uid", openId);

		WeiboUser wbUser = null;
		try {
			String result = HTTPWeb.get(url, map);
			thirdLogger.info("[weibo_getUser]-->params:{},result:{}",map.toString(),result);
			if (result != null) {
				wbUser = JsonUtils.fromJson(result, WeiboUser.class);
			}
			if (wbUser != null && wbUser.getError_code() == 0) {
				user.setGender(wbUser.getGender());
				String name = null;
				if (StringUtils.isBlank(wbUser.getScreen_name())) {
					name = openId;
				} else {
					name = wbUser.getScreen_name();
				}
				user.setNickName(name);
				user.setHeadImg(wbUser.getProfile_image_url());
				user.setLocation("");
				user.setThirdId(openId);
				user.setToken(token);
			}
		} catch (Exception e) {
			thirdLogger.info("[weibo_getUser]-->params:{},result:{}",map.toString(),e.getMessage());
			logger.error("[oauth weibo]", e);
			throw new QuanhuException(ExceptionEnum.BusiException.getCode(), ExceptionEnum.BusiException.getShowMsg(), "AuthWeibo Error :" + e.getLocalizedMessage());
		}

		if (wbUser == null || wbUser.getError_code() != 0) {
			throw new QuanhuException(ExceptionEnum.BusiException.getCode(), ExceptionEnum.BusiException.getShowMsg(), "AuthWeibo Error :");
		}

		return user;
	}

	/**
	 * 获取微博web登录授权页面url
	 * 
	 * @param returnUrl
	 *            第三方回调本地地址
	 * @param returnHost
	 *            己方web端host
	 * @return
	 * @Description
	 */
	public static String getAuthUrl(String appKey,String returnUrl, String returnHost,String notifyUrl) {
		String url = ThirdConstants.WB_OPEN_URL;

		StringBuffer buffer = new StringBuffer();
		buffer.append(url);
		buffer.append("?client_id=").append(appKey);
		buffer.append("&redirect_uri=").append(returnHost).append(notifyUrl);
		buffer.append("&scope=").append("all");
		buffer.append("&state=").append("test").append("_weibo");
		buffer.append("_").append(returnUrl);
		return buffer.toString();
	}

	/**
	 * 微博第三方登录获取token
	 * 
	 * @param code
	 * @param returnHost
	 *            己方web端host
	 * @return
	 * @throws Exception
	 * @Description
	 */
	public static WeiboToken getToken(String appKey,String appSecret,String code, String returnHost,String notifyUrl){
		String url = ThirdConstants.WB_GET_TOKEN_URL;
		Map<String, Object> params = new HashMap<>(5);

		params.put("client_id", appKey);
		params.put("client_secret", appSecret);
		params.put("grant_type", "authorization_code");
		params.put("code", code);
		params.put("redirect_uri", String.format("%s%s", returnHost, notifyUrl));
		WeiboToken token = null;

		try {
			String result = HTTPWeb.post(url, params);
			thirdLogger.info("[weibo_getToken]-->params:{},result:{}",params.toString(),result);
			if (result != null) {
				token = JsonUtils.fromJson(result, WeiboToken.class);
			}
		} catch (Exception e) {
			thirdLogger.info("[weibo_getToken]-->params:{},result:{}",params.toString(),e.getMessage());
			logger.error("[oauth weibo]", e);
			throw new QuanhuException(ExceptionEnum.BusiException.getCode(), ExceptionEnum.BusiException.getShowMsg(), "AuthWeibo Error :" + e.getLocalizedMessage());
		}

		if (token == null || token.getError_code() != 0) {
			throw new QuanhuException(ExceptionEnum.BusiException.getCode(), ExceptionEnum.BusiException.getShowMsg(), "AuthWeibo Error :");
		}

		return token;
	}

}
