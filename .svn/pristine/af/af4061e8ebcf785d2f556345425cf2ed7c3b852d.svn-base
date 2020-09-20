package com.feather.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class NumberUtils {
    private static final Double MILLION = 10000.0;
    private static final Double MILLIONS = 1000000.0;
    private static final Double BILLION = 100000000.0;
    private static final String MILLION_UNIT = "万";
    private static final String BILLION_UNIT = "亿";

    /**
     * 将数字转换成以万为单位或者以亿为单位，因为在前端数字太大显示有问题
     *
     * @author
     * @version 1.00.00
     *
     * @date 2018年1月18日
     * @param amount
     *            报销金额
     * @return
     */
    public static String amountConversion(double amount) {
        // 最终返回的结果值
        String result = String.valueOf(amount);
        // 四舍五入后的值
        double value = 0;
        // 转换后的值
        double tempValue = 0;
        // 余数
        double remainder = 0;

        // 金额大于1百万小于1亿
        if (amount > MILLIONS && amount < BILLION) {
            tempValue = amount / MILLION;
            remainder = amount % MILLION;

            // 余数小于5000则不进行四舍五入
            if (remainder < (MILLION / 2)) {
                value = formatNumber(tempValue, 2, false);
            } else {
                value = formatNumber(tempValue, 2, true);
            }
            // 如果值刚好是10000万，则要变成1亿
            if (value == MILLION) {
                result = zeroFill(value / MILLION) + BILLION_UNIT;
            } else {
                result = zeroFill(value) + MILLION_UNIT;
            }
        }
        // 金额大于1亿
        else if (amount > BILLION) {
            tempValue = amount / BILLION;
            remainder = amount % BILLION;

            // 余数小于50000000则不进行四舍五入
            if (remainder < (BILLION / 2)) {
                value = formatNumber(tempValue, 2, false);
            } else {
                value = formatNumber(tempValue, 2, true);
            }
            result = zeroFill(value) + BILLION_UNIT;
        } else {
            result = zeroFill(amount);
        }
        return result;
    }

    /**
     * 对数字进行四舍五入，保留2位小数
     *
     * @author
     * @version 1.00.00
     *
     * @date 2018年1月18日
     * @param number
     *            要四舍五入的数字
     * @param decimal
     *            保留的小数点数
     * @param rounding
     *            是否四舍五入
     * @return
     */
    public static Double formatNumber(double number, int decimal, boolean rounding) {
        BigDecimal bigDecimal = new BigDecimal(number);

        if (rounding) {
            return bigDecimal.setScale(decimal, RoundingMode.HALF_UP).doubleValue();
        } else {
            return bigDecimal.setScale(decimal, RoundingMode.DOWN).doubleValue();
        }
    }

    /**
     * 对数字进行四舍五入，保留2位小数
     *
     * @author
     * @version 1.00.00
     *
     * @date 2018年1月18日
     * @param number
     *            要四舍五入的数字
     * @param decimal
     *            保留的小数点数
     * @return
     */
    public static Double formatNumber(double number, int decimal) {
        BigDecimal b = new BigDecimal(number);
        double f1 = b.setScale(decimal, BigDecimal.ROUND_HALF_UP).doubleValue();// 2.转换后的数字四舍五入保留小数点后一位;
        return f1;
    }

    /**
     * 对四舍五入的数据进行补0显示，即显示.00
     *
     * @author
     * @version 1.00.00
     *
     * @date 2018年1月23日
     * @return
     */
    public static String zeroFill(double number) {
        String value = String.valueOf(number);

        if (value.indexOf(".") < 0) {
            value = value + ".00";
        } else {
            String decimalValue = value.substring(value.indexOf(".") + 1);

            if (decimalValue.length() < 2) {
                value = value + "0";
            }
        }
        return value;
    }

    /**
     * 转换为万
     * 
     * @param number
     * @return
     */
    public static double toNumber(double number) {
        double str = 0;
        if (number <= 0) {
            str = 0;
            // } else if (number < 10000) {
            // str = number/10000;
        } else {
            double num = number / 10000;// 1.将数字转换成以万为单位的数字

            BigDecimal b = new BigDecimal(num);
            double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();// 2.转换后的数字四舍五入保留小数点后一位;
            str = f1;
        }
        return str;
    }

    /**
     * 随机整数
     * 
     * @param min
     * @param max
     * @return
     */
    public static int getRandom(int min, int max) {
        // 生成10到30之间的数字
        int num = new Random().nextInt(max - min + 1) + min;
        return num;
    }

    /**
     * 随机小数
     * 
     * @param min
     * @param max
     * @return
     */
    public static double getRandowForDouble(double min, double max) {
        double boundedDouble = min + new Random().nextDouble() * (max - min);
        BigDecimal b = new BigDecimal(boundedDouble);
        double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();// 2.转换后的数字四舍五入保留小数点后一位;

        return f1;
    }

    /**
     * 随机小数
     * 
     * @param min
     * @param max
     * @return
     */
    public static double getRandowForDouble(double min, double max, int s) {
        double boundedDouble = min + new Random().nextDouble() * (max - min);
        BigDecimal b = new BigDecimal(boundedDouble);
        double f1 = b.setScale(s, BigDecimal.ROUND_HALF_UP).doubleValue();// 2.转换后的数字四舍五入保留小数点后一位;
        return f1;
    }

    public static String toChinese(String str) {
        String[] s1 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        String[] s2 = { "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };
        String result = "";
        int n = str.length();
        for (int i = 0; i < n; i++) {
            int num = str.charAt(i) - '0';
            if (i != n - 1 && num != 0) {
                result += s1[num] + s2[n - 2 - i];
            } else {
                result += s1[num];
            }
        }
        return result;
    }

    public static String toChinese2(String str) {
        String[] s2 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        StringBuffer sb = new StringBuffer();
        for (char c : str.toCharArray()) {
            sb.append(s2[Integer.parseInt(String.valueOf(c))]);
        }
        return sb.toString();
    }

    public static Integer asInteger(Object value) {
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            return Integer.valueOf((String) value);
        } else if (value instanceof Boolean) {
            return ((Boolean) value) ? 1 : 0;
        } else {
            return null;
        }
    }

    public static Long asLong(Object value) {
        if (value instanceof Long) {
            return (Long) value;
        } else if (value instanceof Number) {
            return ((Number) value).longValue();
        } else if (value instanceof String) {
            return Long.valueOf((String) value);
        } else if (value instanceof Boolean) {
            return ((Boolean) value) ? 1L : 0L;
        } else {
            return null;
        }
    }

    public static Float asFloat(Object value) {
        if (value instanceof Float) {
            return (Float) value;
        } else if (value instanceof Number) {
            return ((Number) value).floatValue();
        } else if (value instanceof String) {
            return Float.valueOf((String) value);
        } else if (value instanceof Boolean) {
            return ((Boolean) value) ? 1F : 0F;
        } else {
            return null;
        }
    }

    public static Double asDouble(Object value) {
        if (value instanceof Double) {
            return (Double) value;
        } else if (value instanceof Number) {
            return ((Number) value).doubleValue();
        } else if (value instanceof String) {
            return Double.valueOf((String) value);
        } else if (value instanceof Boolean) {
            return ((Boolean) value) ? 1D : 0D;
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println("toChinese：" + toChinese("1"));
        System.out.println("toChinese2：" + toChinese2("2"));
    }

}
