/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月9日
 * Id: DateUtils.java, 2017年11月9日 上午10:42:52 Administrator
 */
package com.yryz.quanhu.user.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Administrator
 * @version 1.0
 * @date 2017年11月9日 上午10:42:52
 * @Description TODO (这里用一句话描述这个方法的作用)
 */
public class DateUtils {
	private static Calendar c = new GregorianCalendar();
	
	
	/**
	 * 在当前时间上增加小时数
	 * 
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date addHour(int hour) {
		c.add(Calendar.HOUR_OF_DAY, hour);
		return c.getTime();
	}
	
	/**
	 * 在指定时间上增加小时数
	 * 
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date addHour(Date date, int hour) {
		c.setTime(date);
		c.add(Calendar.HOUR_OF_DAY, hour);
		return c.getTime();
	}
	
	/**
	 * 获得当前时间的<code>java.util.Date</code>对象
	 * 
	 * @return
	 */
	public static Date now() {
		return new Date();
	}
}
