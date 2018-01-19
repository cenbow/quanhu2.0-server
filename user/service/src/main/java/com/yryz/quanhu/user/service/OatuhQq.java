package com.yryz.quanhu.user.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.utils.JsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.user.contants.ThirdConstants;
import com.yryz.quanhu.user.utils.HTTPWeb;
import com.yryz.quanhu.user.vo.QqUser;
import com.yryz.quanhu.user.vo.ThirdUser;

/**
 * qq第三方登录校验
 *
 * @author Pxie
 * @version 2016-08-25
 */
public class OatuhQq {
	private static Logger logger = LoggerFactory.getLogger(OatuhQq.class);
	private static final Logger thirdLogger = LoggerFactory.getLogger("third.logger");

	/**
	 * 根据qq openid和token获取qq用户信息
	 * 
	 * @param openId
	 * @param token
	 * @return
	 * @throws Exception
	 * @Description 获取用户信息返回本地第三方统一对象
	 */
	public static ThirdUser getUser(String qqAppId,String openId, String token) throws Exception {
		ThirdUser user = new ThirdUser();

		String url = ThirdConstants.QQ_GET_USER_URL;

		Map<String, Object> map = new HashMap<>(4);
		map.put("access_token", token);
		map.put("oauth_consumer_key", qqAppId);
		map.put("openid", openId);
		map.put("appid", qqAppId);

		QqUser qqUser = null;
		try {
			String result = HTTPWeb.get(url, map);
			thirdLogger.info("[qq_getUser]-->params:{},result:{}", map.toString(), result);
			if (result != null) {
				qqUser = JsonUtils.fromJson(result, QqUser.class);
			}
			if (qqUser != null && qqUser.getRet() == 0) {
				String name = null;
				if (StringUtils.isBlank(qqUser.getNickname())) {
					name = openId;
				} else {
					name = qqUser.getNickname();
				}
				user.setGender(String.valueOf(qqUser.getGender()));
				user.setNickName(name);
				user.setHeadImg(qqUser.getFigureurl_qq_2());
				user.setLocation("");
				user.setThirdId(openId);
				user.setToken(token);
			}
		} catch (Exception e) {
			logger.error("[oauth qq]", e);
			thirdLogger.info("[qq_getUser]-->params:{},result:{}", map.toString(), e.getMessage());
			throw new QuanhuException(ExceptionEnum.BusiException.getCode(), ExceptionEnum.BusiException.getShowMsg(),
					"AuthQQ Error :" + e.getLocalizedMessage());
		}

		if (qqUser == null || qqUser.getRet() != 0) {
			throw new QuanhuException(ExceptionEnum.BusiException.getCode(), ExceptionEnum.BusiException.getShowMsg(),
					"AuthQQ Error :");
		}

		return user;
	}

}
