package com.max.framework.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.max.framework.util.string.StringUtil;

/**
 * 获得系统时间和进行时间转化的工具类
 * @author 马青松
 * @since 1.0
 */
public class SystemDateUtil {
    /**
     * 日志打印
     */
    private static final Logger logger = Logger.getLogger(SystemDateUtil.class);

    /**
     * 格式yyyy-MM-dd
     */
    public static final String DATE_FORMATE_YYYYMMDD = "yyyy-MM-dd";

    /**
     * 格式yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_FORMATE_YYYYMMDDHH = "yyyy-MM-dd HH:mm:ss";

    /**
     * 格式yyyyMMddHHmmss
     */
    public static final String DATE_FORMATE_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    /**
     * 格式yyyy-MM-dd HH:mm
     */
    public static final String DATE_FORMATE_YYYY_MMDDHH = "yyyy-MM-dd HH:mm";

    /**
     * 格式yyyyMMddHHmmSSS
     */
    public static final String DATE_FORMATE_YYYYMMDDHHMMSSS = "yyyyMMddHHmmSSS";

    /**
     * 获得当前时间(Date类型)
     * @return 当前时间(Date类型)
     */
    public static Date getSystemDate() {
        return new Date();
    }

    /**
     * 获得当前时间的yyyy-MM-dd格式
     * @return 当前时间的yyyy-MM-dd格式
     */
    public static String getSystemDateString() {
        return getStringFormDate(getSystemDate(), DATE_FORMATE_YYYYMMDD);
    }

    /**
     * 获得当前时间的yyyy-MM-dd HH:mm:ss格式
     * @return 当前时间的yyyy-MM-dd HH:mm:ss格式
     */
    public static String getSystemDateTimeString() {
        return getStringFormDate(getSystemDate(), DATE_FORMATE_YYYYMMDDHH);
    }

    /**
     * 获得当前时间的yyyyMMddHHmmSSS格式
     * @return 当前时间的yyyyMMddHHmmSSS格式
     */
    public static String getSystemDateTimeMillisecondString() {
        return getStringFormDate(getSystemDate(), DATE_FORMATE_YYYYMMDDHHMMSSS);
    }

    /**
     * 指定时间字符串进行时间转化(Date类型)
     * @param strDate 时间字符串 yyyy-MM-dd
     * @return 时间
     */
    public static Date getDateFormString(String strDate) {
        if (StringUtil.isNullOrEmpty(strDate)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMATE_YYYYMMDD);
        try {
            return sdf.parse(strDate);
        } catch (ParseException ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    /**
     * 将字符串转换为Date类型(yyyy-MM-dd HH:mm:ss)
     * @param strDate 时间字符串 yyyy-MM-dd HH:mm:ss
     * @return 时间
     */
    public static Date getTimeFormString(String strDate) {
        if (StringUtil.isNullOrEmpty(strDate)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMATE_YYYYMMDDHH);
        try {
            return sdf.parse(strDate);
        } catch (ParseException ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    /**
     * 将字符串转换为Date类型(yyyy-MM-dd HH:mm)
     * @param strDate 时间字符串 yyyy-MM-dd HH:mm
     * @return 时间
     */
    public static Date getDateTimeFormString(String strDate) {
        if (StringUtil.isNullOrEmpty(strDate)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMATE_YYYY_MMDDHH);
        try {
            return sdf.parse(strDate);
        } catch (ParseException ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    /**
     * 获得时间的字符串形式
     * @param date 时间
     * @param dateFormate 格式
     * @return 时间的字符串形式
     */
    public static String getStringFormDate(Date date, String dateFormate) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormate);
        return sdf.format(date);
    }

    /**
     * 将Object中的Date数据还原为Date
     * @param date Object中的Date数据
     * @return Date
     */
    public static Date convertObjectToDate(Object date) {
        if (date != null) {
            return (Date) date;
        } else {
            return null;
        }
    }

    /**
     * 为传入日期增加或减少整数分钟
     * @param date 日期
     * @param minNum 修改分钟数(正数为增加,负数为减少)
     * @return date
     */
    public static Date addOrSubtractMinForDate(Date date, int minNum) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minNum);
        return calendar.getTime();
    }

    /**
     * 为传入日期增加或减少整数天数
     * @param date 日期
     * @param dayNum 修改天数(正数为增加,负数为减少)
     * @return date
     */
    public static Date addOrSubtractDayForDate(Date date, int dayNum) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, dayNum);
        return calendar.getTime();
    }

    /**
     * 为传入日期增加或减少整数月份
     * @param date 日期
     * @param monthNum 修改月份数(整数为增加,负数为减少)
     * @return date
     */
    public static Date addOrSubtractMonthForDate(Date date, int monthNum) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, monthNum);
        return calendar.getTime();
    }

    /**
     * 字符串类型的日期和时间转为date类型
     * @param date 日期(yyyy-MM-dd)
     * @param time 时间(hh:mm)
     * @return date(yyyy-MM-dd HH:mm:ss)
     */
    public static Date appendToDateFormat(String date, String time) {
        StringBuffer dateFormat = new StringBuffer(date);
        dateFormat.append(" ");
        dateFormat.append(time);
        dateFormat.append(":00");
        String dateTime = dateFormat.toString();
        Date finalDate = SystemDateUtil.getTimeFormString(dateTime);
        return finalDate;
    }

    /**
     * 获取日期所在周中的星期一
     * @param date 日期
     * @return 星期一日期
     */
    public static Date getMondayByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        String dates = SystemDateUtil.getStringFormDate(date, DATE_FORMATE_YYYYMMDD);
        Date dateOnlyDate = SystemDateUtil.getDateFormString(dates);
        calendar.setTime(dateOnlyDate);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (1 == dayOfWeek) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, calendar.getFirstDayOfWeek() - day);
        return calendar.getTime();
    }

    /**
     * 获取日期所在周中的星期日
     * @param date 日期
     * @return 星期日日期
     */
    public static Date getSundayByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(SystemDateUtil.getMondayByDate(date));
        calendar.add(Calendar.DATE, 6);
        return calendar.getTime();
    }

    /**
     * 计算两个日期之间相差的天数(参数为date类型)
     * @param smdate 较小的时间
     * @param bdate 较大的时间
     * @return 相差天数
     */
    public static int daysBetween(Date smdate, Date bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMATE_YYYYMMDD);
        try {
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(betweenDays));
        } catch (ParseException ex) {
            logger.error(ex.getMessage());
            return 0;
        }
    }

    /**
     * 计算两个日期之间相差的天数(参数为字符串类型)
     * @param smdate 较小的时间
     * @param bdate 较大的时间
     * @return 相差天数
     */
    public static int daysBetween(String smdate, String bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMATE_YYYYMMDD);
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(smdate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            long time2 = cal.getTimeInMillis();
            long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(betweenDays));
        } catch (ParseException ex) {
            logger.error(ex.getMessage());
            return 0;
        }
    }
}
