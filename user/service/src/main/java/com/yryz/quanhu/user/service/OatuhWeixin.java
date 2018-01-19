package com.yryz.quanhu.user.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.user.contants.ThirdConstants;
import com.yryz.quanhu.user.utils.HTTPWeb;
import com.yryz.quanhu.user.vo.ThirdUser;
import com.yryz.quanhu.user.vo.WxToken;
import com.yryz.quanhu.user.vo.WxUser;

/**
 * 微信第三方登录校验<br/>
 * 1.获取微信用户信息<br/>
 * 2.获取微信web第三方登录认证地址<br/>
 * 3.获取微信web第三方登录token
 * 
 * @author danshiyu
 * @version 2017-11-09
 */
public class OatuhWeixin {
	private static final Logger logger = LoggerFactory.getLogger(OatuhWeixin.class);
	private static final Logger thirdLogger = LoggerFactory.getLogger("third.logger");
	/**
	 * 获取微信用户信息
	 * 
	 * @param openId
	 * @param token
	 * @return
	 * @throws Exception
	 * @Description
	 */
	public static ThirdUser getUser(String openId, String token){
		ThirdUser user = new ThirdUser();
		String url = ThirdConstants.WX_GET_USER_URL;

		Map<String, Object> map = new HashMap<>(2);
		map.put("access_token", token);
		map.put("openid", openId);

		WxUser wxUser = null;
		try {
			String result = HTTPWeb.get(url, map);
			thirdLogger.info("[weixin_getUser]-->params:{},result:{}",map.toString(),result);

			if (result != null) {
				wxUser = JsonUtils.fromJson(result, WxUser.class);
			}
			if (wxUser != null && wxUser.getErrcode() == 0) {
				String name = null;
				if (StringUtils.isBlank(wxUser.getNickname())) {
					name = openId;
				} else {
					name = wxUser.getNickname();
				}

				user.setGender(String.valueOf(wxUser.getSex()));
				user.setNickName(name);
				user.setHeadImg(wxUser.getHeadimgurl());
				user.setLocation("");
				user.setThirdId(wxUser.getUnionid());
				user.setToken(token);
			}
		} catch (Exception e) {
			thirdLogger.info("[weixin_getUser]-->params:{},result:{}",map.toString(),e.getMessage());
			logger.error("[oauth weix]", e);
			throw new QuanhuException(ExceptionEnum.BusiException.getCode(), ExceptionEnum.BusiException.getShowMsg(), "AuthWeixin Error :" + e.getLocalizedMessage());
		}

		if (wxUser == null || wxUser.getErrcode() != 0) {
			throw new QuanhuException(ExceptionEnum.BusiException.getCode(), ExceptionEnum.BusiException.getShowMsg(), "AuthWeixin Error :");
		}
		return user;
	}

	/**
	 * 得到微信请求code url
	 * 
	 * @param returnUrl
	 *            第三方回调本地地址
	 * @param returnHost
	 *            己方web端host
	 * @return
	 * @Description
	 */
	public static String getQrUrl(String appKey,String returnUrl, String returnHost,String notifyUrl) {
		String url = ThirdConstants.WX_OPEN_URL;

		StringBuffer buffer = new StringBuffer();
		buffer.append(url);
		buffer.append("?appid=").append(appKey);
		buffer.append("&redirect_uri=").append(returnHost).append(notifyUrl);
		buffer.append("&response_type=code");
		buffer.append("&scope=snsapi_login");
		buffer.append("&state=").append("quanhu").append("_weixin");
		buffer.append("_").append(returnUrl);

		return buffer.toString();
	}

	/**
	 * 获取微信token
	 * 
	 * @param code
	 *            用户授权后得到code
	 * @return
	 * @throws Exception
	 */
	public static WxToken getToken(String appKey,String appSecret,String code) {
		String url = ThirdConstants.WX_GET_TOKEN_URL;

		Map<String, Object> map = new HashMap<>(4);
		map.put("appid", appKey);
		map.put("secret", appSecret);
		map.put("code", code);
		map.put("grant_type", "authorization_code");
		WxToken token = null;
		try {
			String result = HTTPWeb.get(url, map);
			thirdLogger.info("[weixin_getToken]-->params:{},result:{}",map.toString(),result);
			if (result != null) {
				token = JsonUtils.fromJson(result, WxToken.class);
			}
		} catch (Exception e) {
			thirdLogger.info("[weixin_getToken]-->params:{},result:{}",map.toString(),e.getMessage());
			logger.error("[oauth weix]", e);
			throw new QuanhuException(ExceptionEnum.BusiException.getCode(), ExceptionEnum.BusiException.getShowMsg(), "AuthWeixin Error :" + e.getLocalizedMessage());
		}

		if (token == null || token.getErrcode() != 0) {
			throw new QuanhuException(ExceptionEnum.BusiException.getCode(), ExceptionEnum.BusiException.getShowMsg(), "AuthWeixin Error :");
		}
		return token;

	}

}
