package com.yryz.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = simpleDateFormat.format(date);
        return s;
    }

    /**
     * 给时间加上几个小时
     *
     * @param date 当前时间
     * @param hour 需要加的时间
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

    private static String[] parsePatterns = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss",
            "yyyy.MM.dd HH:mm", "yyyy.MM", "yyyy-MM-dd HH:mm:ss.SSS"};

    // /**
    // * @param args
    // * @throws ParseException
    // */
    // public static void main(String[] args) throws ParseException {
    // String xx = subtract(new Date(), new Date());
    // incrMonthDate("2015年05月01 00:00:00",1);
    // System.out.println(xx);
    // // System.out.println(formatDate(parseDate("2010/3/6")));
    // // System.out.println(getDate("yyyy年MM月dd日 E"));
    // // long time = new Date().getTime()-parseDate("2012-11-19").getTime();
    // // System.out.println(time/(24*60*60*1000));
    // }

    /**
     * 求2个时间的间隔date1-date2。返回 HH:mm:ss格式
     *
     * @param date1
     * @param date2
     * @return
     */
    public static String subtract(Date date1, Date date2) {
        // Date d1 = df.parse("2004-03-26 13:31:40");
        // Date d2 = df.parse("2004-01-02 11:30:24");
        long diff = date1.getTime() - date2.getTime();// 这样得到的差值是微秒级别
        long days = diff / (1000 * 60 * 60 * 24);
        long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
        long second = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / (1000);
        // long second = new Date().getSeconds();
        hours += days * 24;
        String h = hours < 10 ? "0" + hours : "" + hours;
        String m = minutes < 10 ? "0" + minutes : "" + minutes;
        String s = second < 10 ? "0" + second : "" + second;

        if (days >= 2) {
            return days + "天";
        } else {
            return h + ":" + m + ":" + s;
        }
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 日期型字符串转化为日期 格式 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy.MM.dd",
     * "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取过去的天数
     *
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    /**
     * 获取过去的小时
     *
     * @param date
     * @return
     */
    public static long pastHour(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 60 * 1000);
    }

    /**
     * 获取过去的分钟
     *
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 1000);
    }

    /**
     * 转换为时间（天,时:分:秒.毫秒）
     *
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    /**
     * 回去昨天的日期格式 YYYY-MM-DD**
     *
     * @return
     */
    public static String getYesterDay() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        String yesDate = DateFormatUtils.format(calendar, "yyyy-MM-dd");
        return yesDate;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }

    /**
     * 前端显示时间差
     *
     * @param d1 服务器时间
     * @param d2 对比时间
     * @return
     */
    public static String webAfterDate(Date d1, Date d2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
            long totalHours = diff / (1000 * 60 * 60);
            long totalMinutes = diff / (1000 * 60);
            long totals = diff / (1000);
            long days = diff / (1000 * 60 * 60 * 24);
            // long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 *
            // 60);
            // long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours *
            // (1000 * 60 * 60)) / (1000 * 60);

            if (totals < 0) {
                return "刚刚";
            } else if (totals < 60) {
                return totals + "秒前";
            } else if (totalMinutes < 60) {
                return totalMinutes + "分钟前";
            } else if (totalHours < 24) {
                return totalHours + "小时前";
            } else if (days < 31) {
                return days + "天前";
            }

        } catch (Exception e) {
        }
        return df.format(d2);
    }

    /**
     * @param str   日期
     * @param month 加减月数
     * @return
     */
    public static Date incrMonthDate(String str, int month) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd 00:00:00");
        Date dt = null;
        try {
            dt = sdf.parse(str);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        // rightNow.add(Calendar.YEAR,-1);//日期减1年
        rightNow.add(Calendar.MONTH, month);// 日期加3个月
        // rightNow.add(Calendar.DAY_OF_YEAR,10);//日期加10天
        Date dt1 = rightNow.getTime();
        return dt1;
    }

    /**
     * 判断当前时间是否在系统既定的格式内
     *
     * @param src
     * @param start 格式：HH:mm
     * @param end   格式：HH:mm
     * @return
     */
    public static boolean checkBetween(Date src, String start, String end) {
        Date startDate = parseHour(start, "HH:mm");
        Date endDate = parseHour(end, "HH:mm");
        System.out.println("startDate:" + startDate.toLocaleString());
        System.out.println("endDate:" + endDate.toLocaleString());
        if (startDate == null || endDate == null) {
            throw new NullPointerException("时间参数不对，请检查参数格式。start:" + start + "-end:" + end);
        }
        long now = src.getTime();
        if (startDate.getTime() > endDate.getTime()) {
            if (now >= startDate.getTime() || now <= endDate.getTime()) {
                return true;
            } else {
                return false;
            }
        } else if (startDate.getTime() < endDate.getTime()) {
            if (now >= startDate.getTime() && now <= endDate.getTime()) {
                return true;
            } else {
                return false;
            }
        } else
            return false;
    }

    /**
     * 将小时格式化
     *
     * @param src
     * @param pattern
     * @return
     */
    public static Date parseHour(String src, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = dateFormat.parse(src);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            Calendar date1 = Calendar.getInstance();
            date1.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
            date1.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
            return date1.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 格式化日期
     * <p>
     * 格式 yyyyMMddHHmmss
     *
     * @param date
     * @return
     */
    public static String formatYMdHmsDateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(date);
    }

    /**
     * 判断日期是否在当前日期之前
     *
     * @param date
     * @return
     */
    public static boolean checkDateBeforeNow(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return date.before(calendar.getTime());
    }

    /**
     * 判断日期是否在当前日期之后
     *
     * @param date
     * @return
     */
    public static boolean checkDateAfterNow(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return date.after(calendar.getTime());
    }

    /**
     * 判断日期是否在当前日期之后
     *
     * @param date
     * @return
     */
    public static boolean checkDateEqualNow(Date date) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date);
        return calendar.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                && calendar.get(Calendar.DATE) == calendar2.get(Calendar.DATE);
    }


    /**
     * 获得当前时间的<code>java.util.Date</code>对象
     *
     * @return
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 获得当前日期时间
     * <p>
     * 日期时间格式yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String currentDatetime() {
        return datetimeFormat.format(now());
    }

    /**
     * 获取两个时间差的小时数
     *
     * @param dateA
     * @param dateB
     * @return
     */
    public static long getDistanceHours(Date dateA, Date dateB) {
        long diff = dateA.getTime() - dateB.getTime();
        return Math.abs(diff / 1000 / 60 / 60);
    }

    /**
     * 获取当前时间所在日的分钟数
     *
     * @return
     */
    public static long getCurMinutes() {
        Date date = new Date();
        long curMinutes = date.getTime();
        long beginMinutes = getBeginTimeOfDate(date).getTime();
        long minutes = (curMinutes - beginMinutes) / 1000 / 60;
        return minutes > 0 ? minutes : 0;
    }

    /**
     * 获取当前日开始时间
     *
     * @param date
     * @return
     */
    public static Date getBeginTimeOfDate(Date date) {
        String strDate = formatDate(date, new Object[]{"yyyy-MM-dd"}) + " 00:00:00";
        Date beginOfDate = null;

        try {
            beginOfDate = parseDate(strDate, new String[]{"yyyy-MM-dd HH:mm:ss"});
        } catch (ParseException var4) {
            var4.printStackTrace();
        }

        return beginOfDate;
    }
}
