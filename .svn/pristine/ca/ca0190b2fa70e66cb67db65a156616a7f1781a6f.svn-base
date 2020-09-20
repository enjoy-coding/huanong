package com.feather.common4nutz.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.nutz.lang.Lang;

/**
 * <p>
 * Title: HTML相关的正则表达式工具类
 * </p>
 * <p>
 * Description: 包括过滤HTML标记，转换HTML标记，替换特定HTML标记
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 *
 * @author hejian
 * @version 1.0
 * @createtime 2006-10-16
 */

public class HtmlRegexpUtil {
    public static final String MORE = "更多...";

    private final static String regxpForHtml = "<([^>]*)>"; // 过滤所有以<开头以>结尾的标签

    /**
     *
     */
    public HtmlRegexpUtil() {
        // TODO Auto-generated constructor stub
    }

    /**
     *
     * 基本功能：替换标记以正常显示
     * <p>
     *
     * @param input
     * @return String
     */
    public String replaceTag(String input) {
        if (!hasSpecialChars(input)) {
            return input;
        }
        StringBuffer filtered = new StringBuffer(input.length());
        char c;
        for (int i = 0; i <= input.length() - 1; i++) {
            c = input.charAt(i);
            switch (c) {
            case '<':
                filtered.append("&lt;");
                break;
            case '>':
                filtered.append("&gt;");
                break;
            case '"':
                filtered.append("&quot;");
                break;
            case '&':
                filtered.append("&amp;");
                break;
            default:
                filtered.append(c);
            }

        }
        return (filtered.toString());
    }

    /**
     *
     * 基本功能：判断标记是否存在
     * <p>
     *
     * @param input
     * @return boolean
     */
    public boolean hasSpecialChars(String input) {
        boolean flag = false;
        if ((input != null) && (input.length() > 0)) {
            char c;
            for (int i = 0; i <= input.length() - 1; i++) {
                c = input.charAt(i);
                switch (c) {
                case '>':
                    flag = true;
                    break;
                case '<':
                    flag = true;
                    break;
                case '"':
                    flag = true;
                    break;
                case '&':
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     *
     * 基本功能：过滤所有以"<"开头以">"结尾的标签
     * <p>
     *
     * @param str
     * @return String
     */
    public static String filterHtml(String str) {
        Pattern pattern = Pattern.compile(regxpForHtml);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        boolean result1 = matcher.find();
        while (result1) {
            matcher.appendReplacement(sb, "");
            result1 = matcher.find();
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String filterHtml(Element element) {
        String elementStr = element.outerHtml();
        return filterHtml(elementStr);
    }

    /**
     *
     * @param element
     * @param newVal
     * @param removeStrs
     *            排除掉一些不需要的元素
     * @return
     */
    public static String filterAndReplaceHtml(Element element, String newVal, List<String> removeStrs) {
        String elementStr = element.outerHtml();
        String oldVal = filterHtml(element);
        if (!Lang.isEmpty(removeStrs) && removeStrs.size() > 0) {
            for (String removeStr : removeStrs) {
                if (oldVal.contains(removeStr)) {
                    oldVal = oldVal.replace(removeStr, "");
                }
            }
            oldVal = oldVal.replace("\n", "").trim();
        }
        return elementStr.replace(oldVal, newVal);
    }

    public static String filterAndReplaceHtml(Element element, String newVal) {
        List<String> removeStrs = new ArrayList<String>();
        removeStrs.add(MORE);
        return filterAndReplaceHtml(element, newVal, removeStrs);
    }

    /**
     *
     * 基本功能：过滤指定标签
     * <p>
     *
     * @param str
     * @param tag
     *            指定标签
     * @return String
     */
    public static String fiterHtmlTag(String str, String tag) {
        String regxp = "<\\s*" + tag + "\\s+([^>]*)\\s*>";
        Pattern pattern = Pattern.compile(regxp);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        boolean result1 = matcher.find();
        while (result1) {
            matcher.appendReplacement(sb, "");
            result1 = matcher.find();
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     *
     * 基本功能：替换指定的标签
     * <p>
     *
     * @param str
     * @param beforeTag
     *            要替换的标签
     * @param tagAttrib
     *            要替换的标签属性值
     * @param startTag
     *            新标签开始标记
     * @param endTag
     *            新标签结束标记
     * @return String @如：替换img标签的src属性值为[img]属性值[/img]
     */
    public static String replaceHtmlTag(String str, String beforeTag, String tagAttrib, String startTag,
            String endTag) {
        String regxpForTag = "<\\s*" + beforeTag + "\\s+([^>]*)\\s*>";
        String regxpForTagAttrib = tagAttrib + "=\"([^\"]+)\"";
        Pattern patternForTag = Pattern.compile(regxpForTag);
        Pattern patternForAttrib = Pattern.compile(regxpForTagAttrib);
        Matcher matcherForTag = patternForTag.matcher(str);
        StringBuffer sb = new StringBuffer();
        boolean result = matcherForTag.find();
        while (result) {
            StringBuffer sbreplace = new StringBuffer();
            Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag.group(1));
            if (matcherForAttrib.find()) {
                matcherForAttrib.appendReplacement(sbreplace, startTag + matcherForAttrib.group(1) + endTag);
            }
            matcherForTag.appendReplacement(sb, sbreplace.toString());
            result = matcherForTag.find();
        }
        matcherForTag.appendTail(sb);
        return sb.toString();
    }

    public static void main(String[] args) {
        String str = "<span><h4>123</h4></span>";
        String filterStr = HtmlRegexpUtil.filterHtml(str);
        System.out.println(filterStr);
    }
}