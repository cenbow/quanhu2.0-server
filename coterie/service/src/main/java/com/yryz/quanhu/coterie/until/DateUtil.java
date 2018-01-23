package com.yryz.quanhu.coterie.until;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 日期转换工具
 * @author Pxie
 *
 */
public class DateUtil {
	
	/**
	 * Date to String
	 * @return  yyyy-MM-dd HH:mm:ss
	 */
	public static String nowToDetailString() {
		Date date = new Date();
		SimpleDateFormat sdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return sdate.format(date);
	}
	
	/**
	 * 
	 * 字符串时间转成 Date
	 * @param timeStr
	 * @return
	 */
//	public static Date parseDateTime(String timeStr){
//		if(StringUtils.isEmpty(timeStr)){
//			return null;
//		}
//		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
//		DateTime time = DateTime.parse(timeStr, format);
//		return time.toDate();
//	}
	
	/**
	 * 
	 * 获取截止到明天凌晨的秒数
	 * @return
	 */
	public static int getOneDaySecond(){
		long nowTimeStamp = System.currentTimeMillis();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return (int) ((calendar.getTimeInMillis() - nowTimeStamp)/1000);
	}
	
	/**
	 * 
	 * 获取截止到某天凌晨的秒数
	 * @return
	 */
	public static int getSecondByDay(int day){
		long nowTimeStamp = System.currentTimeMillis();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return (int) ((calendar.getTimeInMillis() - nowTimeStamp)/1000);
	}
}
