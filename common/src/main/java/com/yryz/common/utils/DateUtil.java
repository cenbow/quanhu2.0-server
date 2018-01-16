package com.yryz.common.utils;

import com.yryz.common.exception.QuanhuException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static String getString(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = simpleDateFormat.format(date);
		return s;
	}

	public static Date getDate(String date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date parse = null;
		try {
			parse = simpleDateFormat.parse(date);
		} catch (ParseException e) {
			throw QuanhuException.busiError( "日期转换异常！");
		}
		return parse;
	}

	/**
	 * 给时间加上几个小时
	 * 
	 * @param date
	 *            当前时间
	 * @param hour
	 *            需要加的时间
	 * @return Date
	 */
	public static Date addDateMinut(Date date, int hour) {
		// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd
		// HH:mm:ss");
		if (date == null)
			return null;
		// System.out.println("front:" + format.format(date)); //显示输入的日期
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, hour);// 24小时制
		date = cal.getTime();
		// System.out.println("after:" + format.format(date)); //显示更新后的日期
		cal = null;
		// return format.format(date);
		return date;
	}

	/*
	 * 获取当前时间之前或之后几分钟 minute
	 */
	public static String getTimeByMinute(int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, minute);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
	}
	
	public static void main(String[] args) throws ParseException {
		String a = getTimeByMinute(-30);
		System.out.println(a);
	}


}
