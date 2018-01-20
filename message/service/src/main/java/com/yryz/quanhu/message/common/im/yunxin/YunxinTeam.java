package com.yryz.quanhu.message.common.im.yunxin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.message.common.im.yunxin.vo.ImTeamSearchResponseVo;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 云信用户管理
 * 
 * @author Pxie
 *
 */
public class YunxinTeam {
	private static final Logger imLogger = LoggerFactory.getLogger("im.logger");
	private static YunxinTeam yunxinTeam = null;

	private YunxinTeam() {
	}

	public static YunxinTeam getInstance() {
		if (yunxinTeam == null) {
			yunxinTeam = new YunxinTeam();
		}

		return yunxinTeam;
	}

	/**
	 * 创建群
	 * 
	 * @throws Exception
	 */
	public String addTeam(String tName, String custId, String announcement, String intro, String msg, int magree,
			int joinMode, String icon, String members) {
		Map<String, Object> params = new HashMap<>(9);
		params.put("tname", tName);
		params.put("owner", custId);
		params.put("members", members);
		params.put("announcement", announcement);
		params.put("intro", intro);
		params.put("msg", msg);
		params.put("magree", magree);
		params.put("joinmode", joinMode);
		params.put("icon", icon);

		String result;
		try {
			result = YunxinHttpClient.getInstance().post(YunxinHttpClient.TEAM_ADD, params);
			imLogger.info("[im_addTeam]----->params:{},result:{}", params.toString(), result);
		} catch (Exception e) {
			imLogger.info("[im_addTeam]----->params:{},result:{}", params.toString(), e.getMessage());
			throw QuanhuException.busiError("", "yunxin invoke error");
		}
		JSONObject jsonObj = YunxinHttpClient.getInstance().checkCode(result);
		return jsonObj.getString("tid");
	}

	/**
	 * 更新群
	 *
	 * @param icon
	 * @throws Exception
	 */
	public void updateTeam(String tId, String tName, String custId, String announcement, String intro, String icon) {
		Map<String, String> params = new HashMap<String, String>(6);
		params.put("tid", tId);
		params.put("owner", custId);
		if (!StringUtils.isBlank(tName)) {
			params.put("tname", tName);
		}
		if (!StringUtils.isBlank(announcement)) {
			params.put("announcement", announcement);
		}
		if (!StringUtils.isBlank(intro)) {
			params.put("intro", intro);
		}
		if (!StringUtils.isBlank(icon)) {
			params.put("icon", icon);
		}

		String result = null;
		try {
			result = YunxinHttpClient.getInstance().post(YunxinHttpClient.TEAM_UPDATE, params);
			imLogger.info("[im_updateTeam]----->params:{},result:{}", params.toString(), result);
		} catch (Exception e) {
			imLogger.info("[im_updateTeam]----->params:{},result:{}", params.toString(), e.getMessage());
			throw QuanhuException.busiError("", "yunxin invoke error");
		}
		YunxinHttpClient.getInstance().checkCode(result);
	}

	/**
	 * 删除群
	 *
	 * @throws Exception
	 */
	public void deleteTeam(String tid, String custId) {
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("tid", tid);
		params.put("owner", custId);

		String result = null;
		try {
			result = YunxinHttpClient.getInstance().post(YunxinHttpClient.TEAM_DELETE, params);
			imLogger.info("[im_deleteTeam]----->params:{},result:{}", params.toString(), result);
		} catch (Exception e) {
			imLogger.info("[im_deleteTeam]----->params:{},result:{}", params.toString(), result);
			throw QuanhuException.busiError("yunxin invoke error");
		}
		YunxinHttpClient.getInstance().checkCode(result);
	}

	/**
	 * 群信息查询（不包含群成员信息）
	 *
	 * @param tids
	 * @return
	 * @throws Exception
	 */
	public ImTeamSearchResponseVo getTeamInfo(List<String> tids) {
		if (tids == null || tids.isEmpty()) {
			return null;
		}
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("tids", tids.toString());
		params.put("ope", "0");

		String result = null;
		try {
			result = YunxinHttpClient.getInstance().post(YunxinHttpClient.TEAM_QUERY, params);
			imLogger.info("[im_getTeamInfo]----->params:{},result:{}", params.toString(), result);
		} catch (Exception e) {
			imLogger.info("[im_getTeamInfo]----->params:{},result:{}", params.toString(), e.getMessage());
			throw QuanhuException.busiError("", "yunxin invoke error");
		}
		YunxinHttpClient.getInstance().checkCode(result);
		return JsonUtils.fromJson(result, new TypeReference<ImTeamSearchResponseVo>() {
		});
	}

	/**
	 * 加入群
	 * 
	 * @throws Exception
	 */
	public void joinTeam(String tid, String owner, String msg, int magree, String members) {
		Map<String, Object> params = new HashMap<>(5);
		params.put("tid", tid);
		params.put("owner", owner);
		params.put("members", members);
		params.put("msg", msg);
		params.put("magree", magree);

		String result;
		try {
			result = YunxinHttpClient.getInstance().post(YunxinHttpClient.TEAM_ADD_MEMBER, params);
			imLogger.info("[im_joinTeam]----->params:{},result:{}", params.toString(), result);
		} catch (Exception e) {
			imLogger.info("[im_joinTeam]----->params:{},result:{}", params.toString(), e.getMessage());
			throw  QuanhuException.busiError("", "yunxin invoke error");
		}
		YunxinHttpClient.getInstance().checkCode(result);
	}

	/**
	 * 任命管理员
	 * 
	 * @throws Exception
	 */
	public void addManager(String tid, String owner, String members) {
		Map<String, Object> params = new HashMap<>(3);
		params.put("tid", tid);
		params.put("owner", owner);
		params.put("members", members);

		String result;
		try {
			result = YunxinHttpClient.getInstance().post(YunxinHttpClient.TEAM_ADDMANAGER, params);
			imLogger.info("[im_addManager]----->params:{},result:{}", params.toString(), result);
		} catch (Exception e) {
			imLogger.info("[im_addManager]----->params:{},result:{}", params.toString(), e.getMessage());
			throw QuanhuException.busiError("", "yunxin invoke error");
		}
		YunxinHttpClient.getInstance().checkCode(result);
	}

	/**
	 * 移除管理员
	 * 
	 * @throws Exception
	 */
	public void removeManager(String tid, String owner, String members) {
		Map<String, Object> params = new HashMap<>(3);
		params.put("tid", tid);
		params.put("owner", owner);
		params.put("members", members);

		String result;
		try {
			result = YunxinHttpClient.getInstance().post(YunxinHttpClient.TEAM_REMOVEMANAGER, params);
			imLogger.info("[im_removeManager]----->params:{},result:{}", params.toString(), result);
		} catch (Exception e) {
			imLogger.info("[im_removeManager]----->params:{},result:{}", params.toString(), e.getMessage());
			throw QuanhuException.busiError("", "yunxin invoke error");
		}
		YunxinHttpClient.getInstance().checkCode(result);
	}

	/**
	 * 移交群主
	 *
	 * @param userId
	 * @throws Exception
	 */
	public void changeOwner(String tid, String userId, String newowner) {
		Map<String, String> params = new HashMap<String, String>(4);
		params.put("tid", tid);
		params.put("owner", userId);
		params.put("newowner", newowner);
		params.put("leave", "2");

		String result = null;
		try {
			result = YunxinHttpClient.getInstance().post(YunxinHttpClient.TEAM_CHANGEOWNER, params);
			imLogger.info("[im_changeOwner]----->params:{},result:{}", params.toString(), result);
		} catch (Exception e) {
			imLogger.info("[im_changeOwner]----->params:{},result:{}", params.toString(), e.getMessage());
			throw QuanhuException.busiError("", "yunxin invoke error");
		}
		YunxinHttpClient.getInstance().checkCode(result);
	}

	/**
	 * 禁言群成员
	 * 
	 * @param tid
	 * @param owner
	 * @param custId
	 * @param mute
	 *            1-禁言 0-解禁
	 * @Description
	 */
	public void muteTlist(String tid, String owner, String custId, int mute) {
		Map<String, String> params = new HashMap<>(4);
		params.put("tid", tid);
		params.put("owner", owner);
		params.put("accid", custId);
		params.put("mute", String.valueOf(mute));

		String result = null;
		try {
			result = YunxinHttpClient.getInstance().post(YunxinHttpClient.TEAM_MUTETLIST, params);
			imLogger.info("[im_muteTlist]----->params:{},result:{}", params.toString(), result);
		} catch (Exception e) {
			imLogger.info("[im_muteTlist]----->params:{},result:{}", params.toString(), e.getMessage());
			throw QuanhuException.busiError("", "yunxin invoke error");
		}
		YunxinHttpClient.getInstance().checkCode(result);
	}

	/**
	 * 将群主整体禁言
	 * 
	 * @param tid
	 * @param owner
	 * @param mute
	 *            true-禁言 false-解禁
	 * @Description
	 */
	public void muteTlistAll(String tid, String owner, boolean mute) {
		Map<String, String> params = new HashMap<>(3);
		params.put("tid", tid);
		params.put("owner", owner);
		params.put("mute", String.valueOf(mute));

		String result = null;
		try {
			result = YunxinHttpClient.getInstance().post(YunxinHttpClient.TEAM_MUTETLISTALL, params);
			imLogger.info("[im_muteTlistAll]----->params:{},result:{}", params.toString(), result);
		} catch (Exception e) {
			imLogger.info("[im_muteTlistAll]----->params:{},result:{}", params.toString(), e.getMessage());
			throw QuanhuException.busiError("", "yunxin invoke error");
		}
		YunxinHttpClient.getInstance().checkCode(result);
	}

	public static void main(String args[]) {
		try {
			List<String> members = Lists.newArrayList("3kiugxmi5k", "1ytd0tvo1k");
			System.out.println(members.toString());
			System.out.println(new JSONArray(members).toString());
			List<String> tids = Lists.newArrayList("36553473");
			// YunxinTeam.getInstance().addTeam("zhaganTeam4", "3kiugxmi5k",
			// "hehe", "hehe", "hehe", 1, 1, "#", buffer.toString());
			ImTeamSearchResponseVo responseVo = YunxinTeam.getInstance().getTeamInfo(tids);
			System.out.println(JsonUtils.toFastJson(responseVo.getTinfos()));
			// YunxinTeam.getInstance().joinTeam("102220963", "85gtutwr8x",
			// "你好", 0, members.toString());
			// YunxinTeam.getInstance().addManager("102220963", "85gtutwr8x",
			// members.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
