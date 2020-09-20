package com.feather.common.utils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class DateBigintConverter extends BaseTypeHandler<Date> {
    public static final String DATE_FORMAT = "yyyyMMddHHmmss";

    private static Map<String, Integer> TIMEZONE_ID = new HashMap<>();
    private static Map<Integer, String> TIMEZONE_INDEX = new HashMap<>();

    static {
        TIMEZONE_ID.put("Asia/Shanghai", 8);
        TIMEZONE_INDEX.put(8, "Asia/Shanghai");
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType)
            throws SQLException {
        long val = format(parameter);
        ps.setLong(i, val);
    }

    @Override
    public Date getNullableResult(ResultSet rs, String columnName) throws SQLException {
        long val = rs.getLong(columnName);
        return parse(val);
    }

    @Override
    public Date getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        long val = rs.getLong(columnIndex);
        return parse(val);
    }

    @Override
    public Date getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        long val = cs.getLong(columnIndex);
        return parse(val);
    }

    public static long format(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateFormat.setLenient(false);
        String str = dateFormat.format(date);
        long time = Long.parseLong(str, 10);
        return time;
    }

    public static long formatWithTimeZone(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateFormat.setLenient(false);
        String str = dateFormat.format(date);
        long time = Long.parseLong(str, 10);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int index = TIMEZONE_ID.get(cal.getTimeZone().getID());
        time = time * 100 + index;
        return time;
    }

    public static Date parse(long time) {
        String dtStr = String.valueOf(time);
        if (dtStr.length() < 12) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateFormat.setLenient(false);
        Date date = null;
        try {
            date = dateFormat.parse(dtStr);
        } catch (ParseException ex) {
        }
        return date;
    }

    public static Date parseTimeZone(long time) {
        long dt = time / 100;
        String dtStr = String.valueOf(dt);
        if (dtStr.length() < 14) {
            return null;
        }
        int index = (int) (time % 100);
        String timeZoneId = TIMEZONE_INDEX.get(index);
        if (timeZoneId == null) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateFormat.setLenient(false);
        Date date = null;
        try {
            date = dateFormat.parse(dtStr);
        } catch (ParseException ex) {
            return null;
        }
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.setTimeZone(timeZone);
        return cal.getTime();
    }

    public static Date parse(String str) {
        SimpleDateFormat parser = null;
        str = str.trim();
        int length = str.length();
        if (length == 0) {
            return null;
        }
        boolean hasSpace = str.indexOf(' ') > -1;
        if (str.indexOf('-') > -1) {
            if (str.indexOf(':') > -1) {
                if (str.split(":").length == 2)
                    parser = new SimpleDateFormat(hasSpace ? "yyyy-MM-dd HH:mm" : "yyyy-MM-ddHH:mm");
                else
                    parser = new SimpleDateFormat(hasSpace ? "yyyy-MM-dd HH:mm:ss" : "yyyy-MM-ddHH:mm:ss");
            } else {
                if (hasSpace) {
                    parser = new SimpleDateFormat("yyyy-MM-dd HH");
                } else {
                    int arrLen = str.split("-").length;
                    if (arrLen == 2)
                        parser = new SimpleDateFormat("yyyy-MM");
                    else
                        parser = new SimpleDateFormat("yyyy-MM-dd");
                }
            }
        } else if (str.indexOf('/') > -1) {
            if (str.indexOf(':') > -1) {
                if (str.split(":").length == 2)
                    parser = new SimpleDateFormat(hasSpace ? "yyyy/MM/dd HH:mm" : "yyyy/MM/ddHH:mm");
                else
                    parser = new SimpleDateFormat(hasSpace ? "yyyy/MM/dd HH:mm:ss" : "yyyy/MM/ddHH:mm:ss");
            } else {
                if (hasSpace) {
                    parser = new SimpleDateFormat("yyyy/MM/dd HH");
                } else {
                    int arrLen = str.split("/").length;
                    if (arrLen == 2)
                        parser = new SimpleDateFormat("yyyy/MM");
                    else
                        parser = new SimpleDateFormat("yyyy/MM/dd");
                }
            }
        } else {
            if (str.indexOf(':') > -1) {
                if (str.split(":").length == 2)
                    parser = new SimpleDateFormat(hasSpace ? "yyyyMMdd HH:mm" : "yyyyMMddHH:mm");
                else
                    parser = new SimpleDateFormat(hasSpace ? "yyyyMMdd HH:mm:ss" : "yyyyMMddHH:mm:ss");
            } else if (hasSpace) {
                if (length == 15) {
                    parser = new SimpleDateFormat("yyyyMMdd HHmmss");
                } else if (length == 13) {
                    parser = new SimpleDateFormat("yyyyMMdd HHmm");
                } else if (length == 11) {
                    parser = new SimpleDateFormat("yyyyMMdd HH");
                }
            } else {
                if (length == 14) {
                    parser = new SimpleDateFormat("yyyyMMddHHmmss");
                } else if (length == 12) {
                    parser = new SimpleDateFormat("yyyyMMddHHmm");
                } else if (length == 10) {
                    parser = new SimpleDateFormat("yyyyMMddHH");
                } else if (length == 8) {
                    parser = new SimpleDateFormat("yyyyMMdd");
                } else if (length == 6) {
                    parser = new SimpleDateFormat("yyyyMM");
                } else if (length == 4) {
                    parser = new SimpleDateFormat("yyyy");
                }
            }
        }
        parser.setLenient(false);
        try {
            return parser.parse(str);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    // public static void main(String[] args) {
    // System.out.println(getTime(new Date()));
    // }
}
