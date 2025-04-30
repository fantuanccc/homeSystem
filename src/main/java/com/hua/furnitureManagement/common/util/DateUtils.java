package com.hua.furnitureManagement.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    // 定义一个默认的日期格式
    private static final String DEFAULT_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 获取当前时间
     * @return 返回当前时间的Date对象
     */
    public static Date getCurrentDate() {
        return new Date();
    }

    /**
     * 获取当前时间，并以指定的格式返回
     * @param pattern 日期格式
     * @return 返回格式化后的当前时间字符串
     */
    public static String getCurrentDateFormatted(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date());
    }

    /**
     * 获取当前时间，默认格式为"yyyy-MM-dd HH:mm:ss"
     * @return 返回格式化后的当前时间字符串
     */
    public static String getCurrentDateFormatted() {
        return getCurrentDateFormatted(DEFAULT_TIME_PATTERN);
    }

    /**
     * 将Date对象格式化为指定格式的字符串
     * @param date Date对象
     * @param pattern 指定的日期格式
     * @return 返回格式化后的日期字符串
     */
    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 将Date对象格式化为默认格式的字符串
     * @param date Date对象
     * @return 返回格式化后的日期字符串
     */
    public static String formatDate(Date date) {
        return formatDate(date, DEFAULT_TIME_PATTERN);
    }

    /**
     * 将字符串解析为Date对象
     * @param dateString 需要解析的日期字符串
     * @param pattern 指定的日期格式
     * @return 返回解析后的Date对象
     * @throws ParseException 如果解析失败，抛出此异常
     */
    public static Date parseDate(String dateString, String pattern) throws ParseException {
        if (dateString == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(dateString);
    }

    /**
     * 将字符串解析为默认格式的Date对象
     * @param dateString 需要解析的日期字符串
     * @return 返回解析后的Date对象
     * @throws ParseException 如果解析失败，抛出此异常
     */
    public static Date parseDate(String dateString) throws ParseException {
        return parseDate(dateString, DEFAULT_TIME_PATTERN);
    }

    /**
     * 获取当前日期时间的字符串表示，默认格式为"yyyy-MM-dd HH:mm:ss"
     * @return 返回格式化后的当前日期时间字符串
     */
    public static String getCurrentDateTime() {
        return getCurrentDateFormatted();
    }

    /**
     * 获取当前日期的字符串表示，默认格式为"yyyy-MM-dd"
     * @return 返回格式化后的当前日期字符串
     */
    public static String getCurrentDateOnly() {
        return getCurrentDateFormatted("yyyy-MM-dd");
    }

    /**
     * 获取当前时间的字符串表示，默认格式为"HH:mm:ss"
     * @return 返回格式化后的当前时间字符串
     */
    public static String getCurrentTimeOnly() {
        return getCurrentDateFormatted("HH:mm:ss");
    }

    /**
     * 获取指定日期时间的字符串表示，格式为"yyyy-MM-dd HH:mm:ss"
     * @param date 指定的日期时间
     * @return 返回格式化后的日期时间字符串
     */
    public static String formatDateTime(Date date) {
        return formatDate(date);
    }

    /**
     * 获取指定日期的字符串表示，格式为"yyyy-MM-dd"
     * @param date 指定的日期
     * @return 返回格式化后的日期字符串
     */
    public static String formatDateOnly(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    /**
     * 获取指定时间的字符串表示，格式为"HH:mm:ss"
     * @param date 指定的时间
     * @return 返回格式化后的时间字符串
     */
    public static String formatTimeOnly(Date date) {
        return formatDate(date, "HH:mm:ss");
    }
}
