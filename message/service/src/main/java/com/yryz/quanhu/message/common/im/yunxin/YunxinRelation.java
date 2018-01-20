package com.yryz.quanhu.message.common.im.yunxin;

import com.yryz.common.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * im好友
 * 
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月28日 下午2:12:45
 */
public class YunxinRelation {
	private static final Logger imLogger = LoggerFactory.getLogger("im.logger");
	private static YunxinRelation yunxinRelation = null;

	private YunxinRelation() {
	}

	public static YunxinRelation getInstance() {
		if (yunxinRelation == null)
			yunxinRelation = new YunxinRelation();

		return yunxinRelation;
	}

	/**
	 * 添加好友
	 * 
	 * @param aCustId
	 * @param bCustId
	 * @throws Exception
	 */
	public void addFriend(String aCustId, String bCustId) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("accid", aCustId);
		params.put("faccid", bCustId);
		params.put("type", "1"); // 1直接加好友，2请求加好友，3同意加好友，4拒绝加好友
		params.put("msg", ""); // 加好友对应的请求消息，第三方组装，最长256字符

		String result = null;
		try {
			result = YunxinHttpClient.getInstance().post(YunxinHttpClient.FRIEND_ADD, params);
			imLogger.info("[im_addFriend]----->params:{},result:{}", params.toString(), result);
		} catch (Exception e) {
			imLogger.info("[im_addFriend]----->params:{},result:{}", params.toString(), e.getMessage());
			throw new Exception("yunxin invoke error");
		}

		YunxinHttpClient.getInstance().checkCode(result);
	}

	/**
	 * 更新好友信息
	 * 
	 * @param aCustId
	 * @param bCustId
	 * @param alias
	 * @throws Exception
	 */
	public void updateFriend(String aCustId, String bCustId, String alias) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("accid", aCustId);
		params.put("faccid", bCustId);
		params.put("alias", alias);
		//扩展设置备注名为空
		Map<String,String> exParam = new HashMap<>();
		exParam.put("aliasIsEmpty", "false");
		if(StringUtils.isBlank(alias)){
			exParam.put("aliasIsEmpty", "true");
		}
		params.put("ex", JsonUtils.toFastJson(exParam));
		String result = null;
		try {
			result = YunxinHttpClient.getInstance().post(YunxinHttpClient.FRIEND_UPDATE, params);
			imLogger.info("[im_updateFriend]----->aCustId:{},bCustId:{},alias:{},result:{}", aCustId, bCustId,alias, result);
		} catch (Exception e) {
			imLogger.info("[im_updateFriend]----->aCustId:{},bCustId:{},alias:{},result:{}", aCustId, bCustId,alias, e.getMessage());
			throw new Exception("yunxin invoke error");
		}
		YunxinHttpClient.getInstance().checkCode(result);
	}

	/**
	 * 删除好友
	 * 
	 * @param aCustId
	 * @param bCustId
	 * @throws Exception
	 */
	public void deleteFriend(String aCustId, String bCustId) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("accid", aCustId);
		params.put("faccid", bCustId);

		String result = null;
		try {
			result = YunxinHttpClient.getInstance().post(YunxinHttpClient.FRIEND_DELETE, params);
			imLogger.info("[im_deleteFriend]----->aCustId:{},bCustId:{},result:{}", aCustId, bCustId, result);
		} catch (Exception e) {
			imLogger.info("[im_deleteFriend]----->aCustId:{},bCustId:{},result:{}", aCustId, bCustId, e.getMessage());
			throw new Exception("yunxin invoke error");
		}
		YunxinHttpClient.getInstance().checkCode(result);
	}

	/**
	 * 查询好友
	 * 
	 * @param aCustId
	 * @param bCustId
	 * @throws Exception
	 */
	public void getFriends(String custId) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("accid", custId);
		params.put("createtime", "0");

		String result = null;
		try {
			result = YunxinHttpClient.getInstance().post(YunxinHttpClient.FRIEND_GET, params);
			System.out.println(result);
			imLogger.info("[im_getFriends]----->custId:{},result:{}", custId, result);
		} catch (Exception e) {
			imLogger.info("[im_getFriends]----->custId:{},result:{}", custId, result);
			throw new Exception("yunxin invoke error");
		}
		YunxinHttpClient.getInstance().checkCode(result);
	}

	public static void main(String args[]) {
		try {
			// YunxinRelation.getInstance().addFriend("6qcmqlen5r",
			// "1lfrxekrb6");
			// YunxinRelation.getInstance().deleteFriend("6qcmqlen5r",
			// "1lfrxekrb6");
			// YunxinRelation.getInstance().getFriends("6qcmqlen5r");
			YunxinRelation.getInstance().updateFriend("2ukx4mrr2k", "36b5ne0f3q", "");
			YunxinRelation.getInstance().getFriends("2ukx4mrr2k");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
