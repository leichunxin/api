package com.hdu.newlife.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hdu.newlife.exception.NLException;

/**
 * 日期工具类
 * 
 * <br>createDate：2013-11-07
 * <br>updateDate：2013-11-22
 * @version 1.2.2
 * @author newlife
 *
 */
public class DateUtils {

    private static final Log logger = LogFactory.getLog(DateUtils.class);

    // -------------------------------------------------------------------------
    // 字符串转换为日期对象：
    // -------------------------------------------------------------------------

    /**
     * 字符串转换为Date对象
     * @param source			字符串
     * @param format			日期格式
     * @return					为空，或转换失败时皆返回null
     */
    public static Date parse(String source, String format) {
        if (StringUtils.isBlank(source) || StringUtils.isBlank(format)) {
            return null;
        }

        try {
            return new SimpleDateFormat(format).parse(source);
        } catch (Exception e) {
            logger.error("字符串转换为Date对象时异常", e);
            return null;
        }
    }

    /**
     * 字符串转换为Date对象(字符串格式："yyyy-MM-dd")
     * @param source			字符串
     * @return					source为空，或转换失败时皆返回null
     */
    public static Date parseDate(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }

        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(source);
        } catch (ParseException e) {
            logger.error("字符串转换为Date对象时异常", e);
            return null;
        }
    }

    /**
     * 字符串转换为Date对象(字符串格式："yyyy-MM-dd HH:mm:ss")
     * @param source			字符串
     * @return					source为空，或转换失败时皆返回null
     */
    public static Date parseDateTime(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }

        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(source);
        } catch (ParseException e) {
            logger.error("字符串转换为Date对象时异常", e);
            return null;
        }
    }

    /**
     * 字符串转换为Date对象(字符串格式："yyyy-MM-dd HH:mm:ss SSS")
     * @param source			字符串
     * @return					source为空，或转换失败时皆返回null
     */
    public static Date parseDateMiTime(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }

        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").parse(source);
        } catch (ParseException e) {
            logger.error("字符串转换为Date对象时异常", e);
            return null;
        }
    }

    // -------------------------------------------------------------------------
    // 日期对象转换为字符串：
    // -------------------------------------------------------------------------

    /**
     * Date对象转换为字符串
     * @param date				Date
     * @param format			日期格式
     * @return					为空，或转换失败时皆返回null
     */
    public static String format(Date date, String format) {
        if (null == date || StringUtils.isBlank(format)) {
            return null;
        }

        try {
            return new SimpleDateFormat(format).format(date);
        } catch (Exception e) {
            logger.error("Date对象转换为字符串时异常", e);
            return null;
        }
    }

    /**
     * Date对象转换为字符串(格式："yyyy-MM-dd")
     * @param date				Date
     * @return					date为空时皆返回""
     */
    public static String formatDate(Date date) {
        if (null == date) {
            return "";
        }

        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
    
    /**
     * Date对象转换为字符串(格式："MM-dd")
     * @param date				Date
     * @return					date为空时皆返回""
     */
    public static String formatDate2(Date date) {
        if (null == date) {
            return "";
        }

        return new SimpleDateFormat("MM-dd").format(date);
    }

    /**
     * Date对象转换为字符串(格式："yyyy-MM-dd HH:mm:ss")
     * @param date				Date
     * @return					date为空时皆返回""
     */
    public static String formatDateTime(Date date) {
        if (null == date) {
            return "";
        }

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    /**
     * Date对象转换为字符串(格式："yyyy-MM-dd HH:mm:ss SSS")
     * @param date				Date
     * @return					date为空时皆返回""
     */
    public static String formatDateTimeMi(Date date) {
        if (null == date) {
            return "";
        }

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(date);
    }

    // -------------------------------------------------------------------------
    // 天 的开始时间和结束时间：
    // -------------------------------------------------------------------------

    /**
     * 天的开始时间
     * @param date				Date
     * @return					date为空时皆返回null
     */
    public static Date dayBegin(Date date) {
        if (null == date) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        dayBegin(calendar);

        return calendar.getTime();
    }
    
    /**
     * 天的开始时间
     * @param calendar			Calendar
     * @return					calendar为空时皆返回null
     */
    private static Date dayBegin(Calendar calendar) {
        if (null == calendar) {
            return null;
        }

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    /**
     * 天的结束时间
     * @param date				Date
     * @return					date为空时皆返回null
     */
    public static Date dayEnd(Date date) {
        if (null == date) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        dayEnd(calendar);

        return calendar.getTime();
    }

    /**
     * 天的结束时间
     * @param calendar			Calendar
     * @return					calendar为空时皆返回null
     */
    private static Date dayEnd(Calendar calendar) {
        if (null == calendar) {
            return null;
        }

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        return calendar.getTime();
    }
    
    /**
     * 获取当前日期所在月份的第一天的日期
     * @return
     */
    public static Date firstDayOfMonth(Date date) {
    	if (null == date) {
            return null;
        }
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		//设置从当前月份一号
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);		
		
        return calendar.getTime();
    }
    
    /**
     * 获取某日期所在月份的最后一天的日期
     * @param date
     * @return
     */
    public static Date lastDayOfMonth(Date date) {
    	if (null == date) {
            return null;
        }
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		String dateStr = sdf.format(calendar.getTime());
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			return null;
		} 
    }
    
    /**
     * 获取某日期所在月份的最后一天
     * @param date
     * @return
     */
    public static int lastDayOfMonthDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 获取到期时间
     * @param date
     * @param months
     * @return
     */
    public static Date getDate(Date date, int months) {
    	if (null == date) {
            return null;
        }
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int lastDayOfMonth = lastDayOfMonthDay(date);
    	calendar.add(Calendar.MONTH, months);
		int lastDayOfMonthEnd = lastDayOfMonthDay(calendar.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(calendar.getTime());
    	if(dayOfMonth <= 28) {
    		try {
				return sdf.parse(dateStr);
			} catch (ParseException e) {
				return null;
			}
    	} else {
    		if(dayOfMonth == lastDayOfMonth) {
    			return lastDayOfMonth(calendar.getTime());
    		} else {
    			if(lastDayOfMonth > lastDayOfMonthEnd) {
    				return lastDayOfMonth(calendar.getTime());
    			} else {
    				try {
    					return sdf.parse(dateStr);
    				} catch (ParseException e) {
    					return null;
    				}
    			}
    		}
    	}
    }
    
    public static void main(String[] args) {
    	Date date = new Date(System.currentTimeMillis());
    	System.out.println(formatDate(date));
    	System.out.println(formatDate(getDate(date,1)));
    }
    
    /**
     * 获取某个时间的上周同日时间
     * @param date Date
     * @return date为空时皆返回null
     */
    public static Date theDayLastWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (null != date) {
        	calendar.setTime(date);
        }
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        return calendar.getTime();
    }

    // -------------------------------------------------------------------------
    // 月/年 的开始时间和结束时间：
    // -------------------------------------------------------------------------

    /**
     * 月的开始时间
     * @param date				Date
     * @return					date为空时皆返回null
     */
    public static Date monthBegin(Date date) {
        if (null == date) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
        dayBegin(calendar);

        return calendar.getTime();
    }

    /**
     * 月的结束时间
     * @param date				Date
     * @return					date为空时皆返回null
     */
    public static Date monthEnd(Date date) {
        if (null == date) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        dayEnd(calendar);

        return calendar.getTime();
    }

    /**
     * 年的开始时间
     * @param date				Date
     * @return					date为空时皆返回null
     */
    public static Date yearBegin(Date date) {
        if (null == date) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
        dayBegin(calendar);

        return calendar.getTime();
    }

    /**
     * 年的结束时间
     * @param date				Date
     * @return					date为空时皆返回null
     */
    public static Date yearEnd(Date date) {
        if (null == date) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        dayEnd(calendar);

        return calendar.getTime();
    }

    // -------------------------------------------------------------------------
    // 根据参数组成一个日期对象：
    // -------------------------------------------------------------------------

    /**
     * 根据参数组成一个日期对象，参数："年/月/日/时/分/秒/毫秒"
     * <br> 说明：<font color="red">如值为-1则使用当前的</font>
     * @param year				年
     * @param month				月
     * @param day				日
     * @param hour				时
     * @param minute			分
     * @param second			秒
     * @param milliSecond		毫秒
     * @return
     */
    public static Date set(int year, int month, int day, int hour, int minute, int second, int milliSecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        if (year != -1)
            calendar.set(Calendar.YEAR, year);
        if (month != -1)
            calendar.set(Calendar.MONTH, month);
        if (day != -1)
            calendar.set(Calendar.DATE, day);
        if (hour != -1)
            calendar.set(Calendar.HOUR_OF_DAY, hour);
        if (minute != -1)
            calendar.set(Calendar.MINUTE, minute);
        if (second != -1)
            calendar.set(Calendar.SECOND, second);
        if (milliSecond != -1)
            calendar.set(Calendar.MILLISECOND, milliSecond);

        return calendar.getTime();
    }

    /**
     * 根据参数组成一个日期对象，参数："年/月/日"
     * <br> 说明：<font color="red">如值为-1则使用当前的</font>
     * @param year				年
     * @param month				月
     * @param day				日
     * @return
     */
    public static Date setDate(int year, int month, int day) {
        return set(year, month, day, -1, -1, -1, -1);
    }

    /**
     * 根据参数组成一个日期对象，参数："时/分/秒"
     * <br> 说明：<font color="red">如值为-1则使用当前的</font>
     * @param hour				时
     * @param minute			分
     * @param second			秒
     * @return
     */
    public static Date setTime(int hour, int minute, int second) {
        return set(-1, -1, -1, hour, minute, second, -1);
    }

    /**
     * 根据参数组成一个日期对象，参数："时/分/秒/毫秒"
     * <br> 说明：<font color="red">如值为-1则使用当前的</font>
     * @param hour				时
     * @param minute			分
     * @param second			秒
     * @param milliSecond		毫秒
     * @return
     */
    public static Date setTime(int hour, int minute, int second, int milliSecond) {
        return set(-1, -1, -1, hour, minute, second, milliSecond);
    }

    // -------------------------------------------------------------------------
    // 修改指定时间的值：
    // -------------------------------------------------------------------------

    /**
     * 更改指定时间的"年/月/日/时/分/秒/毫秒"
     * <br> 说明：<font color="red">如值为-1则不进行修改</font>
     * @param date				原始时间
     * @param year				年
     * @param month				月
     * @param day				日
     * @param hour				时
     * @param minute			分
     * @param second			秒
     * @param milliSecond		毫秒
     * @return
     */
    public static Date update(Date date, int year, int month, int day, int hour, int minute, int second, int milliSecond) {
        if (null == date) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (year != -1)
            calendar.set(Calendar.YEAR, year);
        if (month != -1)
            calendar.set(Calendar.MONTH, month);
        if (day != -1)
            calendar.set(Calendar.DATE, day);
        if (hour != -1)
            calendar.set(Calendar.HOUR_OF_DAY, hour);
        if (minute != -1)
            calendar.set(Calendar.MINUTE, minute);
        if (second != -1)
            calendar.set(Calendar.SECOND, second);
        if (milliSecond != -1)
            calendar.set(Calendar.MILLISECOND, milliSecond);

        return calendar.getTime();
    }

    /**
     * 更改指定时间的"年/月/日"
     * <br> 说明：<font color="red">如值为-1则不进行修改</font>
     * @param date				原始时间
     * @param year				年
     * @param month				月
     * @param day				日
     * @return
     */
    public static Date updateDate(Date date, int year, int month, int day) {
        return update(date, year, month, day, -1, -1, -1, -1);
    }

    /**
     * 更改指定时间的"时/分/秒"
     * <br> 说明：<font color="red">如值为-1则不进行修改</font>
     * @param date				原始时间
     * @param hour				时
     * @param minute			分
     * @param second			秒
     * @return
     */
    public static Date updateTime(Date date, int hour, int minute, int second) {
        return update(date, -1, -1, -1, hour, minute, second, -1);
    }

    /**
     * 更改指定时间的"时/分/秒/毫秒"
     * <br> 说明：<font color="red">如值为-1则不进行修改</font>
     * @param date				原始时间
     * @param hour				时
     * @param minute			分
     * @param second			秒
     * @param milliSecond		毫秒
     * @return
     */
    public static Date updateTime(Date date, int hour, int minute, int second, int milliSecond) {
        return update(date, -1, -1, -1, hour, minute, second, milliSecond);
    }

    // -------------------------------------------------------------------------
    // 指定时间的加、减：
    // -------------------------------------------------------------------------

    /**
     * 在指定时间的基础上增/减天数（说明：正数为增、负数为减）
     * @param oldDate			原始时间
     * @param day				天数
     * @return
     */
    public static Date addDay(Date oldDate, int day) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(oldDate);

        calendar.add(Calendar.DATE, day);

        return calendar.getTime();
    }

    /**
     * 在指定时间的基础上增/减"年/月/日"（说明：正数为增、负数为减）
     * @param oldDate			原始时间
     * @param year				年
     * @param month				月
     * @param day				日
     * @return
     */
    public static Date addDate(Date oldDate, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(oldDate);

        calendar.add(Calendar.YEAR, year);
        calendar.add(Calendar.MONTH, month);
        calendar.add(Calendar.DATE, day);

        return calendar.getTime();
    }

    /**
     * 在指定时间的基础上增/减"年/月/日/时/分/秒"（说明：正数为增、负数为减）
     * @param oldDate			原始时间
     * @param year				年
     * @param month				月
     * @param day				日
     * @param hour				时
     * @param minute			分
     * @param second			秒
     * @return
     */
    public static Date addDate(Date oldDate, int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(oldDate);

        calendar.add(Calendar.YEAR, year);
        calendar.add(Calendar.MONTH, month);
        calendar.add(Calendar.DATE, day);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        calendar.add(Calendar.MINUTE, minute);
        calendar.add(Calendar.SECOND, second);

        return calendar.getTime();
    }

    // -------------------------------------------------------------------------
    // 根据指定值得到时间对象：
    // -------------------------------------------------------------------------

    /**
     * 得到指定"年/月/日"的时间对象(时/分/秒为当前的)
     * @param year				年
     * @param month				月
     * @param day				日
     * @return
     */
    public static Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        return calendar.getTime();
    }

    /**
     * 得到指定"年/月/日/时/分/秒"的时间对象
     * @param year				年
     * @param month				月
     * @param day				日
     * @param hour				时
     * @param minute			分
     * @param second			秒
     * @return
     */
    public static Date getDateTime(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, hour, minute, second);
        return calendar.getTime();
    }

    // -------------------------------------------------------------------------
    // 获取指定时间的年/月/日：
    // -------------------------------------------------------------------------

    /**
     * 获取指定时间的年
     * @param date
     * @return					date为空时皆返回0
     */
    public static int year(Date date) {
        if (null == date) {
            return 0;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取指定时间的月
     * @param date
     * @return					date为空时皆返回0
     */
    public static int month(Date date) {
        if (null == date) {
            return 0;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取指定时间的日
     * @param date
     * @return					date为空时皆返回0
     */
    public static int day(Date date) {
        if (null == date) {
            return 0;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    // -------------------------------------------------------------------------
    // 日期比较：
    // -------------------------------------------------------------------------

    /**
     * 日期比较，得出其天数
     * <br> 注意：这里不检查两值为空情况
     * @param beginDate			开始时间
     * @param endDate			结束时间
     * @return					可能会返回负值，两值相差不足一天时返回0
     */
    public static int compareDate(Date beginDate, Date endDate) {
        return (int) ((endDate.getTime() - beginDate.getTime()) / (1000 * 60 * 60 * 24));
    }

    // -------------------------------------------------------------------------
    // 其它：
    // -------------------------------------------------------------------------

    /**
     * 检测日期格式字符串是否正确
     * @param dateStr			日期格式字符串(可为"yyyyMMdd"或"yyyy-MM-dd")
     * @return					正确返回true；格式不正确、长度不对等皆返回false
     */
    public static boolean checkDateStr(String dateStr) {
        // 是否为空、长度、是否全为数字的检测：
        if (isBlank(dateStr)) {
            return false;
        } else {
            dateStr = dateStr.replaceAll("-", "");
            if (dateStr.length() != 8 || !dateStr.matches("\\d{8}")) {
                return false;
            }
        }

        int year = Integer.parseInt(dateStr.substring(0, 4));
        int month = Integer.parseInt(dateStr.substring(4, 6));
        int day = Integer.parseInt(dateStr.substring(6, 8));

        // 二月份最大天数：
        int febMaxDay = isLeapYear(year) ? 29 : 28;
        // 每月最大天数集：
        int[] days = new int[] { 31, febMaxDay, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

        for (int i = 0; i < 12; i++) {
            if (month == i + 1) {
                if (1 <= day && day <= days[i]) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    /**
     * 判断是否是闰年
     * <br> 规则：四年一闰，百年不闰，四百年再闰
     * <br> 说明：仅考虑3200年以前(其实3200等不是闰年)
     * @param year				指定年
     * @return					返回true:是、false:不是
     */
    public static boolean isLeapYear(int year) {
        if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
            return true;
        }
        return false;

    }

    /**
     * 判断字符串是否为空
     * <br> 说明：为了使本工具类不与字符串工具类有关联，对于字符串为空的情况简单的做下处理
     * @param str				目标字符串
     * @return					为空时将返回true
     */
    private static boolean isBlank(String str) {
        if (null == str || str.trim().length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 描述:	 获取指定时间所在季度的第一个月的第一天
     * <br> 说明：非按自然年获取时间，自定义每年的起始时间和结束时间分别为12月1日和11月30日</br>
     *
     * @param date 指定时间
     * @return
     */
    public static Date getSeasonFirstMonth(Date date) {
        int month = month(date);
        int year = year(date);
        if (month >= 9 && month <= 11) {
            return getDate(year, 9, 1);
        } else if (month >= 6 && month <= 8) {
            return getDate(year, 6, 1);
        } else if (month >= 3 && month <= 5) {
            return getDate(year, 3, 1);
        }
        if (month == 12) {
            return getDate(year, 12, 1);
        } else {
            return getDate(year - 1, 12, 1);
        }
    }

    /**
     * 描述:	 获取指定时间所在季度的最后一个月的第一天
     *
     * @param date 指定时间
     * @return
     */
    public static Date getSeasonLastMonth(Date date) {
        int month = month(date);
        int year = year(date);
        if (month >= 9 && month <= 11) {
            return getDate(year, 11, 1);
        } else if (month >= 6 && month <= 8) {
            return getDate(year, 8, 1);
        } else if (month >= 3 && month <= 5) {
            return getDate(year, 5, 1);
        }
        return getDate(year, 2, 1);
    }

    /**
     * 描述:	 获取当前时间所在的非自然年的年份
     * <br> 说明：非按自然年获取时间，自定义每年的起始时间和结束时间分别为12月1日和11月30日</br>
     *
     * @param date
     * @return
     */
    public static int getCurrentSeasonYear(Date date) {
        int year = DateUtils.year(date);
        if (12 == DateUtils.month(date)) {
            year = year + 1;
        }
        return year;
    }

    /** 
     * @Title:getSeasonLastMonthDay
     * @Description: 获取目标季度的最后一天
     * @param endDate
     * @return
     * @author jiantinggu
     * @date 2013-11-27
     */
    public static Date getSeasonLastDay(Date date) {
        int month = month(date);
        int year = year(date);
        if (month >= 9 && month <= 11) {
            return getDate(year, 11, 30);
        } else if (month >= 6 && month <= 8) {
            return getDate(year, 8, 31);
        } else if (month >= 3 && month <= 5) {
            return getDate(year, 5, 31);
        }
        if (month == 12) {
            return getDate(year + 1, 2, 28);
        } else {
            return getDate(year, 2, 28);
        }
    }

    /**
     * 描述:	 获取时间段中的月份列表
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<Integer> getMonthsBetweenDate(Date startDate, Date endDate) {
        if (null == startDate || null == endDate) {
            return null;
        }
        List<Integer> monthList = new ArrayList<Integer>();
        Calendar calendar = Calendar.getInstance();
        startDate = setDate(year(startDate), month(startDate) - 1, day(startDate));
        calendar.setTime(startDate);
        while (compareDate(calendar.getTime(), endDate) >= 0) {
            monthList.add(month(calendar.getTime()));
            calendar.add(Calendar.MONTH, 1);
        }
        return monthList;
    }
    
	/**
	 * 判断两个日期间的关系,精确到天（date1>date2返回1相等返回0，小于返回-1否则返回-2）
	 * @param date1
	 * @param date2
	 * @return
     * @author chunxinlei
	 */
	public static int compareTheDate(Date date1,Date date2) {
		if(DateUtils.year(date1) == DateUtils.year(date2)) {
			if(DateUtils.month(date1) == DateUtils.month(date2)) {
				if(DateUtils.day(date1) > DateUtils.day(date2)) {
					return 1;
				} else if(DateUtils.day(date1) == DateUtils.day(date2)) {
					return 0;
				} else {
					return -1;
				}
			} else if(DateUtils.month(date1) > DateUtils.month(date2)) {
				return 1;
			} else {
				return -1;
			}
		} else if(DateUtils.year(date1) > DateUtils.year(date2)) {
			return 1;
		}else {
			return -1;
		}
	}
	
	/**
     * 
     * 描述: 获取农夫山泉的财年数据。
     *       <p>农夫山泉的财年从12月算起，即2013年12月的财年应该为2014</p>
     * @author Qiancheng Yao
     * @return int
     */
    public static int getFiscalYear() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        return month < 12 ? year : year + 1;
    }

    public static int getLastFiscalYear() {
        return getFiscalYear() - 1;
    }

    /**
     * 
     * 描述:   获取上一个财务年的开始日期。
     * @author Qiancheng Yao
     * @return Date
     */
    public static Date getBeginDateOfLastFiscalYear() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String lastBeginDate = (calendar.get(Calendar.YEAR) - (month < 12 ? 2 : 1)) + "-12-01";
        try {
            return dateFormat.parse(lastBeginDate);
        } catch (ParseException e) {
            throw new NLException("");
        }
    }

    /**
     * 
     * 描述:   获取上一个财务年的结束日期(查询条件中应小于该日期，而不是小于等于)。
     * @author Qiancheng Yao
     * @return Date
     */
    public static Date getEndDateOfLastFiscalYear() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String lastBeginDate = (calendar.get(Calendar.YEAR) - (month < 12 ? 1 : 0)) + "-12-01";
        try {
            return dateFormat.parse(lastBeginDate);
        } catch (ParseException e) {
            throw new NLException("");
        }
    }

    /**
     * 
     * 描述:   获取这一个财务年的开始日期。
     * @author Qiancheng Yao
     * @return Date
     */
    public static Date getBeginDateOfThisFiscalYear() {
        return getEndDateOfLastFiscalYear();
    }
}
