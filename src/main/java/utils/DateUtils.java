package utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @className: DateUtils
 * @description: 日期处理工具类
 * @author: Aim
 * @date: 2023/4/3
 **/
public class DateUtils {

    // ====================== 定义常量 ======================
    /**
     * 完整时间 yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String DATE_LONG_STR = "yyyy-MM-dd HH:mm:ss.SSS";
    /**
     * 完整时间 yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm:ss";
    /**
     * 年月日(下划线) yyyy-MM-dd
     */
    public static final String DATE_SMALL_STR = "yyyy-MM-dd";
    /**
     * 完整时间 yyyy/MM/dd HH:mm:ss
     */
    public static final String STR_DATE_SLASH = "yyyy/MM/dd HH:mm:ss";
    /**
     * 年月日 yyyy/MM/dd
     */
    public static final String STR_DATE_SLASH_SMALL = "yyyy/MM/dd";
    /**
     * 完整时间 yyyy-MM-dd'T'HH:mm:ss
     */
    public static final String STR_DATE_FULL_T = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * 年月日时分秒(无横划线) yyyyMMddHHmmss
     */
    public static final String STR_DATE_All_KEY = "yyyyMMddHHmmss";
    /**
     * 年月日时分秒(无横划线) yyMMddHHmmss
     */
    public static final String STR_DATE_KEY = "yyMMddHHmmss";
    /**
     * 年月日(无横划线) yyyyMMdd
     */
    public static final String STR_DATE_SHORT = "yyyyMMdd";
    /**
     * 年月 yyyyMM
     */
    public static final String STR_YEAR_MONTH = "yyyyMM";
    /**
     * 年 yyyy
     */
    public static final String STR_YEAR = "yyyy";

    /**
     * 当前时间对象
     */
    public static Date nowadays() {
        return new Date();
    }

    /**
     * 将时间字符串转换成数据库中datetime可存储的类型
     */
    public static Timestamp sqlParse(String date) {
        return new Timestamp(dateParse(date).getTime());
    }

    /**
     * 获取当前timestamp
     */
    public static String getCurTimeStamp() {
        return System.currentTimeMillis() + "";
    }

    /**
     * 根据特定格式格式化日期
     *
     * @param date    被格式化的日期
     * @param pattern 时间格式
     * @return 格式化后的字符串
     */
    public static String dateFormatToString(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        return simpleDateFormat.format(calender.getTime());
    }

    /**
     * 获取系统当前时间(指定返回类型)
     *
     * @param pattern 时间格式
     * @return 时间字符串
     */
    public static String dateFormatToString(String pattern) {
        return dateFormatToString(nowadays(), pattern);
    }

    /**
     * 时间转换为yyyy-MM-dd HH:mm:ss格式的字符串
     */
    public static String dateToString(Date date) {
        DateFormat YYYY_MM_DD_MM_HH_SS = new SimpleDateFormat(DATE_FULL_STR);
        return YYYY_MM_DD_MM_HH_SS.format(date);
    }

    /**
     * 获取系统当前日期(精确到毫秒)，格式：yyyy-MM-dd HH:mm:ss
     */
    public static String getDateNow() {
        return dateFormatToString(DATE_FULL_STR);
    }

    /**
     * 获取系统当期年月日(精确到天)，格式：yyyyMMdd
     */
    public static String getDateShort() {
        return dateFormatToString(STR_DATE_SHORT);
    }

    /**
     * 返回系统当前时间(精确到毫秒),作为一个唯一的订单编号，格式：yyyyMMddHHmmss
     */
    public static String getDateOrderNum() {
        return dateFormatToString(STR_DATE_All_KEY);
    }

    /**
     * 两个时间比较
     */
    public static int compareDateWithNow(Date date) {
        return date.compareTo(nowadays());
    }

    /**
     * 两个时间比较(时间戳比较)
     */
    public static int compareDateWithNow(long date) {
        long now = dateToUnixTimestamp();
        if (date > now) {
            return 1;
        } else if (date < now) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * 时间字符串转换指定格式时间对象
     *
     * @param dateString 时间字符串
     * @param pattern    时间格式
     */
    public static Date dateParse(String dateString, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 时间转换为yyyy-MM-dd HH:mm:ss格式的date类型
     */
    public static Date dateParse(String dateString) {
        return dateParse(dateString, DATE_FULL_STR);
    }

    /**
     * 时间转换为yyyy-MM-dd格式的date类型
     */
    public static Date dateParseYYMMDDDate(String dateString) {
        return dateParse(dateString, DATE_SMALL_STR);
    }

    /**
     * 将指定的日期转换成Unix时间戳
     *
     * @param dateString 时间字符串
     * @param pattern    时间格式
     * @return long 时间戳
     */
    public static long dateToUnixTimestamp(String dateString, String pattern) {
        long timestamp = 0;
        try {
            timestamp = dateParse(dateString, pattern).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    /**
     * 将指定的日期转换成Unix时间戳，格式：yyyy-MM-dd HH:mm:ss
     */
    public static long dateToUnixTimestamp(String dateString) {
        return dateToUnixTimestamp(dateString, DATE_FULL_STR);
    }

    /**
     * 将当前日期零点转换成Unix时间戳
     *
     * @return long 时间戳
     */
    public static long dateZeroToUnixTimestamp() {
        String nowDay = dateFormatToString(DATE_SMALL_STR);
        nowDay += " 00:00:00";
        return dateToUnixTimestamp(nowDay);
    }

    /**
     * 将当前时间日期转换成Unix时间戳
     *
     * @return long 时间戳
     */
    public static long dateToUnixTimestamp() {
        return nowadays().getTime();
    }

    /**
     * 将Unix时间戳转换成日期
     *
     * @param timestamp 时间戳
     * @return String 日期字符串
     */
    public static String unixTimestampToDate(long timestamp) {
        SimpleDateFormat sd = new SimpleDateFormat(DATE_FULL_STR);
        sd.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sd.format(new Date(timestamp));
    }

    /**
     * 将Unix时间戳转换成日期
     *
     * @param timestamp 时间戳
     * @return String 日期字符串
     */
    public static String TimeStamp2Date(long timestamp, String pattern) {
        return dateFormatToString(new Date(timestamp), pattern);
    }

    /**
     * 将Unix时间戳转换成日期
     *
     * @param timestamp 时间戳
     * @return String 日期字符串
     */
    public static String TimeStamp2Date(long timestamp) {
        return TimeStamp2Date(timestamp, DATE_FULL_STR);
    }

    /**
     * 获取当月的第一天
     */
    public static String getMonthFirstDay() {
        Calendar cal = Calendar.getInstance();
        Calendar f = (Calendar) cal.clone();
        f.clear();
        f.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        f.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        String firstday = new SimpleDateFormat(DATE_SMALL_STR).format(f.getTime());
        firstday = firstday + " 00:00:00";
        return firstday;
    }

    /**
     * 获取当月的最后一天
     */
    public static String getMonthLastDay() {
        Calendar cal = Calendar.getInstance();
        Calendar l = (Calendar) cal.clone();
        l.clear();
        l.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        l.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        l.set(Calendar.MILLISECOND, -1);
        String lastday = new SimpleDateFormat(DATE_SMALL_STR).format(l.getTime());
        lastday = lastday + " 23:59:59";
        return lastday;
    }

    /**
     * 获取昨天日期
     */
    public static Date getYesterDay() {
        return getCustomDay(-1);
    }

    /**
     * 获取明天日期
     */
    public static Date getTomorrowDay() {
        return getCustomDay(1);
    }

    /**
     * 获取指定日期
     *
     * @param fate 指定天数(整数往后推,负数往前移动)
     */
    public static Date getCustomDay(int fate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowadays());
        calendar.add(Calendar.DATE, fate);// 把日期加减天.
        return calendar.getTime();
    }

    /**
     * 当前时间加上 年份 所得的 日期
     *
     * @param year 增加的年数
     */
    public static String getYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowadays());
        calendar.add(Calendar.YEAR, +year);
        String theday = new SimpleDateFormat(DATE_FULL_STR).format(calendar.getTime());
        return theday;
    }

    /**
     * 给指定的日期加上(减去)月份
     *
     * @param date    时间对象
     * @param pattern 时间格式
     * @param num     增减的月份数
     */
    public static String addMoth(Date date, String pattern, int num) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        calender.add(Calendar.MONTH, num);
        return simpleDateFormat.format(calender.getTime());
    }

    /**
     * 给制定的时间加上(减去)天
     *
     * @param date    时间对象
     * @param pattern 时间格式
     * @param num     增减的天数
     */
    public static String addDay(Date date, String pattern, int num) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        calender.add(Calendar.DATE, num);
        return simpleDateFormat.format(calender.getTime());
    }

    /**
     * 获取昨天零点的时间戳
     */
    public static long getYesterDayZeroUnixTimestamp() {
        String yesterDay = dateFormatToString(getYesterDay(), DATE_SMALL_STR);
        yesterDay += " 00:00:00";
        return dateToUnixTimestamp(yesterDay);
    }

    /**
     * 获取昨天23:59:59的时间戳
     */
    public static long getYesterDayUnixTimestamp() {
        String yesterDay = dateFormatToString(getYesterDay(), DATE_SMALL_STR);
        yesterDay += " 23:59:59";
        return dateToUnixTimestamp(yesterDay);
    }

    /**
     * 获取昨天零点的时间戳
     */
    public static long getNowZeroDate() {
        String now = dateFormatToString(nowadays(), DATE_SMALL_STR);
        now += " 00:00:00";
        return dateToUnixTimestamp(now);
    }

    /**
     * 获取昨天23:59:59的时间戳
     */
    public static long getNowDate() {
        String now = new SimpleDateFormat(DATE_SMALL_STR).format(nowadays());
        now += " 23:59:59";
        return dateToUnixTimestamp(now);
    }

    /**
     * 获取指定日期的零点的时间戳
     */
    public static long getEnactZeroDate(Date date) {
        String now = new SimpleDateFormat(DATE_SMALL_STR).format(date);
        now += " 00:00:00";
        return dateToUnixTimestamp(now);
    }

    /**
     * 获取指定日期的23:59:59的时间戳
     */
    public static long getEnactDate(Date date) {
        String now = new SimpleDateFormat(DATE_SMALL_STR).format(date);
        now += " 23:59:59";
        return dateToUnixTimestamp(now);
    }

    /**
     * 字符串转换成时间对象
     *
     * @param date    时间字符串
     * @param pattern 转换格式
     * @param isLong  是否通过字符串转时间戳转换成时间对象
     */
    public static Date parse(String date, String pattern, boolean isLong) {
        Date time = null;
        String[] formats = {DATE_LONG_STR, DATE_FULL_STR, DATE_SMALL_STR, STR_DATE_SLASH, STR_DATE_SLASH_SMALL, DATE_FULL_STR};
        try {
            if (isLong) {
                long times = Long.parseLong(date);
                time = new Date(times);
            } else {
                if (pattern != null) {
                    SimpleDateFormat df = new SimpleDateFormat(pattern);
                    time = df.parse(date);
                } else {
                    for (String format : formats) {
                        try {
                            SimpleDateFormat df = new SimpleDateFormat(format);
                            time = df.parse(date);
                            if (time != null) {
                                break;
                            }
                        } catch (ParseException pe) {
                            continue;
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return time;
    }

    /**
     * 字符串转换成时间对象
     *
     * @param date    时间字符串
     * @param pattern 转换格式
     */
    public static Date parse(String date, String pattern) {
        return parse(date, pattern, false);
    }

    /**
     * @param date    时间字符串
     * @param pattern 转换格式
     * @return 转换指定格式字符串
     */
    public static String format(String date, String pattern) {
        return dateFormatToString(parse(date, null), pattern);
    }

    /**
     * @param date 时间字符串
     * @return 转换为yyyyMMddHHmmss格式的字符串
     */
    public static String format(String date) {
        return format(date, DATE_FULL_STR);
    }
}
