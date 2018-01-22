package com.yryz.quanhu.message.common.im.yunxin;

import com.yryz.common.exception.QuanhuException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 云信用户管理
 * 
 * @author Pxie
 *
 */
public class YunxinUser {
	private static final Logger imLogger = LoggerFactory.getLogger("im.logger");
	private static YunxinUser yunxinUser = null;

	private YunxinUser() {
	}

	public static YunxinUser getInstance() {
		if (yunxinUser == null)
			yunxinUser = new YunxinUser();

		return yunxinUser;
	}

	/**
	 * 添加用户
	 * 
	 * @param userId
	 * @param nick
	 * @param iconUrl
	 * @throws Exception
	 */
	public void addUser(String userId, String nick, String iconUrl) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("accid", userId);
		params.put("name", nick);
		params.put("props", "");
		if (!StringUtils.isBlank(iconUrl))
			params.put("icon", iconUrl);
		params.put("token", userId);

		String result;
		try {
			result = YunxinHttpClient.getInstance().post(YunxinHttpClient.USER_CREATE, params);
			imLogger.info("[im_addUser]----->params:{},result:{}", params.toString(), result);
		} catch (Exception e) {
			imLogger.info("[im_addUser]----->params:{},result:{}", params.toString(), e.getMessage());
			throw new Exception("yunxin invoke error");
		}
		YunxinHttpClient.getInstance().checkCode(result);
	}

	/**
	 * 更新用户信息
	 * 
	 * @param userId
	 * @param nick
	 * @param iconUrl
	 * @throws Exception
	 */
	public void updateUser(String userId, String nick, String iconUrl) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("accid", userId);

		if (!StringUtils.isBlank(nick))
			params.put("name", nick);
		if (!StringUtils.isBlank(iconUrl))
			params.put("icon", iconUrl);

		String result = null;
		try {
			result = YunxinHttpClient.getInstance().post(YunxinHttpClient.USER_UPDATE, params);
			imLogger.info("[im_updateUser]----->params:{},result:{}", params.toString(), result);
		} catch (Exception e) {
			imLogger.info("[im_updateUser]----->params:{},result:{}", params.toString(), e.getMessage());
			throw new Exception("yunxin invoke error");
		}
		YunxinHttpClient.getInstance().checkCode(result);
	}

	/**
	 * 获取用户名片，可批量
	 * 
	 * @param userId
	 * @throws Exception
	 */
	public void getUser(String userId) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("accids", "[\"" + userId + "\"]");

		String result = null;
		try {
			result = YunxinHttpClient.getInstance().post(YunxinHttpClient.USER_GET, params);
			imLogger.info("[im_getUser]----->params:{},result:{}", params.toString(), result);
		} catch (Exception e) {
			imLogger.info("[im_getUser]----->params:{},result:{}", params.toString(), e.getMessage());
			throw QuanhuException.busiError("", "yunxin invoke error");
		}
		YunxinHttpClient.getInstance().checkCode(result);
	}

	/**
	 * 
	 * 用户封禁以及下线
	 * 
	 * @param custId
	 * @throws Exception
	 */
	public void block(String custId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("accid", custId);
		params.put("needkick", "true");
		String result = null;
		try {
			result = YunxinHttpClient.getInstance().post(YunxinHttpClient.USER_BLOCK, params);
			imLogger.info("[im_block]----->params:{},result:{}", params.toString(), result);
		} catch (Exception e) {
			imLogger.info("[im_block]----->params:{},result:{}", params.toString(), e.getMessage());
			throw QuanhuException.busiError("", "yunxin invoke error");
		}
		YunxinHttpClient.getInstance().checkCode(result);
	}

	/**
	 * 
	 * 用户解封
	 * 
	 * @param custId
	 * @throws Exception
	 */
	public void unblock(String custId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("accid", custId);

		String result = null;
		try {
			result = YunxinHttpClient.getInstance().post(YunxinHttpClient.USER_UNBLOCK, params);
			imLogger.info("[im_unblock]----->params:{},result:{}", params.toString(), result);
		} catch (Exception e) {
			imLogger.info("[im_unblock]----->params:{},result:{}", params.toString(), e.getMessage());
			throw QuanhuException.busiError("", "云信访问失败");
		}
		YunxinHttpClient.getInstance().checkCode(result);
	}

	public static void main(String args[]) {
		try {
			// YunxinUser.getInstance().updateUser("6qcmqlen5r", "不知道恶", "");
			/* dev环境可用im账户 */
			// 3kiugxmi5k
			// 36b5ne0f3q
			// 4fg6h1aeak
			// 4o5q5hmdad
			// 5nhpyjbmon
			// 5fna0use06
			// 503q38en0j
			// 55w0vt7v50
			// 6vifym8y3u
			// 6scq8kfh4m
			// 6gupdfbmt9
			// 2nyvttoews
			// 2u22qo97fi
			// 2u5x8uc3f8
			// 2jwmpqo1l3
			// 1r7xjobfe3
			// 1x33fj5cj5
			// 11y3giw7os
			// 1ytd0tvo1k
			// 1k5tsvvw8q
			// 109ggoo9gh
			// kwiddy3u
			// r3l7bbgi
			// guotlym3
			// 8kl87wgttq
			// 8m9qhkqu9v
			// 8gcpb0ia9t
			// 8unym37afo
			// 0letwdl58d
			// 0cf6q44uyl
			// 0ef00rekyt
			// 7bl4ewi9r5
			// 7wry37mjox
			/* dev环境可用im账户 */
			YunxinUser.getInstance().getUser("109ggoo9gh");
			// YunxinUser.getInstance().updateUser("guotlym3", "好好的荡荡悠悠药草香",
			// "http://yryz-upload.oss-cn-hangzhou.aliyuncs.com/headImage/2F20161111010059563-3623.jpg");
			// YunxinUser.getInstance().addUser("kwiddy3u", "苹果啦不鸟你牛肉粉公",
			// "http://yryz-upload.oss-cn-hangzhou.aliyuncs.com/headImage/2F20161103041043292-5297.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
