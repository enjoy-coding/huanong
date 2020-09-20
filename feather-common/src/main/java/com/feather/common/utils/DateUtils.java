package com.feather.common.utils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 时间工具类
 *
 * @author feather
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String MM_DD = "MM-dd";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss",
            "yyyy.MM.dd HH:mm", "yyyy.MM" };

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String getTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD_HH_MM_SS, date);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        String result = "";
        if (date != null) {
            result = new SimpleDateFormat(format).format(date);
        }
        return result;
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
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
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    public static int differentDays(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);

        int day1 = calendar1.get(Calendar.DAY_OF_YEAR);
        int day2 = calendar2.get(Calendar.DAY_OF_YEAR);
        int year1 = calendar1.get(Calendar.YEAR);
        int year2 = calendar2.get(Calendar.YEAR);

        if (year1 != year2) // 不同年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) { // 闰年
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    timeDistance += 366;
                } else { // 不是闰年
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
        } else {// 同年
            return day2 - day1;
        }
    }

    /**
     * 获取当前月
     *
     * @return
     */
    public static int getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        return month;
    }

    /**
     * 获取当前年
     *
     * @return
     */
    public static int getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        return year;
    }

    /**
     * 获取上一年
     * 
     * @return
     */
    public static int getPreYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        Date y = c.getTime();
        String year = sdf.format(y);
        return Integer.parseInt(year);
    }

    /**
     * 获取上一月
     * 
     * @return
     */
    public static int getPreMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String mon = sdf.format(m);
        return Integer.parseInt(mon);
    }

    /**
     * 获取上期时间
     * @param paramsTime 时间字符串
     * @return 上期时间字符串
     */
    public static String getPreTime(String paramsTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String time = "";
        try {
            Date  currdate = sdf.parse(paramsTime);

            Calendar c = Calendar.getInstance();
            c.setTime(currdate);
            c.add(Calendar.MONTH, -1);
            time = sdf.format(c.getTime());
        }catch (Exception e){
            e.printStackTrace();

        }
        return time;
    }

    /**
     * 获取时间字符串中的年份
     * @param paramsTime 时间字符串
     * @return 年份
     */
    public static int getYearByTimeStr(String paramsTime){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            return Integer.parseInt(sdf.format(sdf.parse(paramsTime)));
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;

    }

    /**
     * 获取时间字符串中的月份
     * @param paramsTime 时间字符串
     * @return 月份
     */
    public static int getMonthByTimeStr(String paramsTime){
        try {
            Date mon = new SimpleDateFormat("yyyy-MM").parse(paramsTime);
            SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
            return Integer.parseInt(sdf1.format(mon));
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;

    }

    public static int getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now.getTime());
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static String getLongToString(String longTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(Long.valueOf(longTime));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Long stringParseLong(String time) {
        Long timestamp = 0L;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(time);

            timestamp = date.getTime();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    /**
     * 判断当前时间是否在设置的dark mode时间段
     * @param date1: 开始时间（hh:mm）
     * @param date2: 结束时间（hh:mm）
     */
    public static boolean isBelongPeriodTime(String date1, String date2) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date currentTime = new Date(System.currentTimeMillis());
        Date startTimeDate;
        Date endTimeDate;
        Calendar date = Calendar.getInstance();
        Calendar begin = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        try {
            date.setTime(df.parse(df.format(currentTime)));
            startTimeDate = df.parse(date1);
            endTimeDate = df.parse(date2);
            begin.setTime(startTimeDate);
            end.setTime(endTimeDate);
            if (endTimeDate.getHours() < startTimeDate.getHours()) {
                return date.after(begin) || date.before(end);

            } else if (endTimeDate.getHours() == startTimeDate.getHours()) {
                if (endTimeDate.getMinutes() < startTimeDate.getMinutes()) {
                    return  date.after(begin) || date.before(end);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 这里是时间段的起止都在同一天的情况，只需要判断当前时间是否在这个时间段内即可
        return date.after(begin) && date.before(end);
    }
}
