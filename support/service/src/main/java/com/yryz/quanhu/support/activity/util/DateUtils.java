package com.yryz.quanhu.support.activity.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	/**
	 * 比较2个日期大小
	 */
	public static long getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		long leek = (afterTime - beforeTime);
		return leek;
	}

	public static int getDatePoorHour(Date endDate, Date nowDate) {
		long diff = endDate.getTime() - nowDate.getTime();
		return (int) diff / (3600 * 1000);
	}

	public static int daysOfTwo(Date fDate, Date oDate) {
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(fDate);
		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
		aCalendar.setTime(oDate);
		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
		return day2 - day1;
	}

}
