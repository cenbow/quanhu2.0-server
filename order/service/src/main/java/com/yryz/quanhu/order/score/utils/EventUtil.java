package com.yryz.quanhu.order.score.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.yryz.quanhu.order.score.type.ScoreTypeEnum;

public abstract class EventUtil {
	
    private static final Logger logger = LoggerFactory.getLogger(EventUtil.class);

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 状态操作数记录前缀
	 */
	private static final String SCORE_RECORD_PREFIX = "SCORE_RECORD";

	private static final String SCORE_STATUS_PREFIX = "SCORE_STATUS";

	private static final String SCORE_SIGN_PREFIX = "SCORE_SIGN";

	/**
	 * 仅按次数循环的，才记录中间状态
	 * 
	 * @param custId
	 * @param eventCode
	 * @return
	 */
	public static String getScoreRecordKey(String custId, String eventCode) {
		String now = sdf.format(new Date());
		return SCORE_RECORD_PREFIX + "_" + custId + "_" + now + "_" + eventCode;
	}

	/**
	 * 获取redis中的积分状态
	 * 
	 * @param custId
	 * @param eventCode
	 * @return
	 */
	public static String getScoreStatusKey(String custId, String eventCode, ScoreTypeEnum ste) {
		String now = sdf.format(new Date());
		switch (ste.getTypeCode()) {
		// 一次性积分事件不记录日期
		case "ONCE":
			return SCORE_STATUS_PREFIX + "_" + ste.getTypeCode() + "_" + custId + "_" + eventCode;
		case "LOOP":
			return SCORE_STATUS_PREFIX + "_" + ste.getTypeCode() + "_" + custId + "_" + now + "_" + eventCode;
		case "SIGN": //签到
			return SCORE_SIGN_PREFIX + "_" + custId + "_" + now + "_" + eventCode;
		// 每次触发的，无状态，直接记流水
		default:
			return "";
		}

	}

	public static int daysInterval(Date startDate, Date endDate){
		long oneDayMiliSecs = 1000 * 3600 * 24;
		long startMiliSecs = 0;
		long endMiliSecs = 0;
		try {
			startMiliSecs = sdf.parse(sdf.format(startDate)).getTime();
			endMiliSecs = sdf.parse(sdf.format(endDate)).getTime();
		} catch (ParseException e) {
			Calendar c = Calendar.getInstance();
			c.setTime(startDate);
			c.set(Calendar.HOUR, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			startMiliSecs = c.getTimeInMillis();
			
			c.setTime(endDate);
			c.set(Calendar.HOUR, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			endMiliSecs = c.getTimeInMillis();
		}
		BigDecimal timeStart = new BigDecimal(startMiliSecs);
		BigDecimal timeEnd = new BigDecimal(endMiliSecs);
		BigDecimal daysInterval = timeEnd.subtract(timeStart).divide(new BigDecimal(oneDayMiliSecs), RoundingMode.DOWN);
		return daysInterval.intValue();
	}
	
	
	/**
	 * @author admin
	 * @date 2015年3月26日
	 * @param body
	 * @return
	 * @Description 将字符串改为Map
	 */
	public static  Map<String, String> toMap(String body){
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		try {
			Map<String, String> map = gson.fromJson(body, new TypeToken<Map<String, String>>(){}.getType());
			return map;
		} catch (Exception e) {
			logger.warn("参数处理异常.." + body , e);
			return new HashMap<String, String>();
		}
	}
	
	
}
