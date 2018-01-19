package com.yryz.quanhu.message.push.service;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.PushPayload.Builder;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosAlert;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.report.ReceivedsResult;
import cn.jpush.api.report.ReceivedsResult.Received;
import cn.jpush.api.report.ReportClient;
import com.google.common.collect.Lists;
import com.yryz.common.utils.IdGen;
import com.yryz.quanhu.message.push.entity.PushParamsDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * JPush的发送工具类
 * 
 * @author Administrator
 *
 */
public class JPushService {

	private final static Logger LOGGER = LoggerFactory.getLogger(JPushService.class);

	// Modify: 2016年11月10日16:40:03 Pxie
	// (sgt) 3890e1ebabdb2056a18bbb04
	// chinaResource e6ac1994fca31c08a99d3ead
	// (mo)fd58769f98d761e327df6b25 (prod)b4d065627dec730e40b0e49a
	private final static String MASTER_SECRET = "e6ac1994fca31c08a99d3ead";// Context.getProperty("jpush_secret");
	// chinaResource df0712fb0b0e7e947881868a
	// (sgt)40869323ab400d45aa046896
	// (mo)ff209cfbd67a228f1f58b7dd (prod)829a414d80f3a73b73025247
	private final static String APP_KEY = "df0712fb0b0e7e947881868a";// Context.getProperty("jpush_key");


	/**
	 * 极光消息推送
	 * 
	 * @author danshiyu
	 * @date 2017年9月20日
	 * @param paramsDTO
	 * @return
	 */
	public static String sendPushMessage(PushParamsDTO paramsDTO, List<String> to) {
		JPushClient jPushClient = new JPushClient(paramsDTO.getAppSecret(), paramsDTO.getAppKey());
		Builder builder = PushPayload.newBuilder().setPlatform(Platform.all())
				.setAudience(getAudienceByPushType(paramsDTO.getPushType(), to));
		if (paramsDTO.getMsgStatus() && StringUtils.isNotBlank(paramsDTO.getMsg())) {
			builder.setMessage(Message.content(paramsDTO.getMsg()));
		}
		if (StringUtils.isNotEmpty(paramsDTO.getNotification()) && paramsDTO.getNotifyStatus()) {
			IosAlert alert = IosAlert.newBuilder()
					.setTitleAndBody(paramsDTO.getNotification(), null, paramsDTO.getNotificationBody()).build();
			AndroidNotification androidNotification = AndroidNotification.newBuilder()
					.addExtra("message", paramsDTO.getMsg()).setAlert(paramsDTO.getNotificationBody())
					.setTitle(paramsDTO.getNotification()).build();
			if (StringUtils.isEmpty(paramsDTO.getNotificationBody())) {
				androidNotification = AndroidNotification.newBuilder().addExtra("message", paramsDTO.getMsg())
						.setAlert(paramsDTO.getNotification()).build();
			}
			builder = builder
					.setNotification(
							Notification.newBuilder()
									.addPlatformNotification(IosNotification.newBuilder().setAlert(alert.toJSON())
											.setSound("happy").addExtra("message", paramsDTO.getMsg())
											.setContentAvailable(true).build())
									.addPlatformNotification(androidNotification).build())
					.setOptions(Options.newBuilder().setApnsProduction(paramsDTO.getPushEvn()).build());
		}
		PushPayload payload = builder.build();
		try {
			PushResult result = jPushClient.sendPush(payload);
			if (!result.isResultOK()) {
				LOGGER.warn("all推送任务执行失败  msg:{}", paramsDTO.getMsg());
				return null;
			}
			return String.valueOf(result.msg_id);
		} catch (APIConnectionException e) {
			LOGGER.warn("all推送任务执行失败  msg:{}", paramsDTO.getMsg());
			LOGGER.error("all推送任务执行失败  msg:{} erroMsg:{}", paramsDTO.getMsg(), e.getMessage());
			return null;
		} catch (APIRequestException e) {
			LOGGER.warn("all推送任务执行失败  msg:{}", paramsDTO.getMsg());
			LOGGER.error("all推送任务执行失败  msg:{} erroMsg:{}", paramsDTO.getMsg(), e.getMessage());
			return String.valueOf(e.getMsgId());
		} catch (Exception e) {
			LOGGER.warn("all推送任务执行失败  msg:{}", paramsDTO.getMsg());
			LOGGER.error("all推送任务执行失败  msg:{} erroMsg:{}", paramsDTO.getMsg(), e.getMessage());
			return null;
		}
	}

	/**
	 * 获取jpush发送状态报告
	 * 
	 * @author danshiyu
	 * @date 2017年9月19日
	 * @param appKey
	 * @param masterSecret
	 * @param msgIds
	 * @return
	 * @Description
	 */
	public static List<Received> getReceivedStatus(String appKey, String masterSecret, final List<String> msgIds) {
		if (CollectionUtils.isEmpty(msgIds)) {
			return null;
		}
		ReportClient client = new ReportClient(masterSecret, appKey);
		try {
			String[] array = new String[msgIds.size()];
			ReceivedsResult result = client.getReceiveds(msgIds.toArray(array));
			return result.received_list;
		} catch (APIConnectionException | APIRequestException e) {
			LOGGER.warn("jpush获取状态报告失败,desc:{}", e.getMessage());
			LOGGER.error("jpush获取状态报告失败,desc:{}", e.getMessage());
			return null;
		} catch (Exception e) {
			LOGGER.warn("jpush获取状态报告失败,desc:{}", e.getMessage());
			LOGGER.error("jpush获取状态报告失败,desc:{}", e.getMessage());
			return null;
		}
	}

	/**
	 * 根据推送类型得到极光Audience
	 * 
	 * @param pushType
	 * @param to
	 * @return
	 */
	private static Audience getAudienceByPushType(String pushType, List<String> to) {
		Audience audience = Audience.alias(to);
		switch (pushType) {
		case "byAlias":
		case "byAliass":
			audience = Audience.alias(to);
			break;
		case "byTag":
		case "byTags":
			audience = Audience.tag(to);
		case "byRegistrationId":
		case "byRegistrationIds":
			audience = Audience.registrationId(to);
		case "all":
			audience = Audience.all();
		default:
			break;
		}
		return audience;
	}

	public static void main(String[] args) {
		// 100d85590972ab27fc9
		// 171976fa8ab52be80e3
		// 1517bfd3f7f2f7ae23c
		// 18171adc033884ed7a4
		// 5nhpyjbmon
		// 2nyvttoews
		String msg = "{\"msg_content\":{\"colType\":1,\"data\":{\"appChineseName\":\"赊购通\",\"appName\":\"sgt\",\"columnCode\":\"1008611\",\"content\":\"您提交的审核资料存在问题。请登录“悠然一指”APP重新提交相关审核资料。\",\"sendTime\":\"2017-08-14 14:31:57\",\"sid\":\"881\"},\"msgId\":\""
				+ IdGen.uuid()
				+ "\",\"msgType\":78580076,\"sendTime\":\"2017-08-14 14:31:57\",\"title\":\"审核结果消息推送\",\"to\":\"2nyvttoews\"}}";
		String msg_id = sendPushMessage(
				new PushParamsDTO(APP_KEY, MASTER_SECRET, null, null, "你好", msg, true, true, null, false),Lists.newArrayList("sc0kbxwo"));
		System.out.println(msg_id);
		// List<Received> receiveds = getReceivedStatus(APP_KEY, MASTER_SECRET,
		// Lists.newArrayList("sc0kbxwo"));
		// receiveds.get(0).ios_apns_sent = 1;
		// receiveds.get(0).android_received = 1;
		// receiveds.get(0).ios_msg_received = 1;
		// receiveds.get(0).wp_mpns_sent = 1;
		// List<PushReceived> pushReceiveds =
		// JsonUtils.fromJson(JsonUtils.toFastJson(receiveds),
		// new TypeReference<List<PushReceived>>() {
		// });

		// System.out.println(sendByRegistrationId(APP_KEY, MASTER_SECRET,
		// "1517bfd3f7f2f7ae23c", "你好", msg));
		// System.out.println(JsonUtils.toFastJson(pushReceiveds));
	}

}
