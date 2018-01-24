package com.yryz.quanhu.order.grow.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yryz.quanhu.order.grow.type.GrowTypeEnum;

public abstract class GrowKeyUtil {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 状态操作数记录前缀
	 */
	private static final String GROW_RECORD_PREFIX = "GROW_RECORD";

	private static final String GROW_STATUS_PREFIX = "GROW_STATUS";

	/**
	 * 仅按次数循环的，才记录中间状态
	 * 
	 * @param userId
	 * @param eventCode
	 * @return
	 */
	public static String getGrowRecordKey(String userId, String eventCode) {
		String now = sdf.format(new Date());
		return GROW_RECORD_PREFIX + "_" + userId + "_" + now + "_" + eventCode;
	}

	/**
	 * 获取redis中的积分状态 成长的一次性触发跟资源有关，与积分的key生成方式不同
	 * 
	 * @param userId
	 * @param eventCode
	 * @return
	 */
	public static String getGrowStatusKey(String userId, String eventCode, String resourceId, GrowTypeEnum ste) {

		switch (ste.getTypeCode()) {
		// 一次性积分事件不记录日期，但要记录资源id
		case "ONCE":
			return GROW_STATUS_PREFIX + "_" + ste.getTypeCode() + "_" + userId + "_" + eventCode + "_" + resourceId;
		case "LOOP":
			String now = sdf.format(new Date());
			return GROW_STATUS_PREFIX + "_" + ste.getTypeCode() + "_" + userId + "_" + now + "_" + eventCode;
		// 每次触发的，无状态，直接记流水
		default:
			return "";
		}

	}

}
