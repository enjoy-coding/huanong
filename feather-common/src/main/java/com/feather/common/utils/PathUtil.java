package com.feather.common.utils;

/**
 * @author nothing
 * @date 2019-11-14 10:44
 */

import java.io.File;

/**
 * new File("..\path\abc.txt") 中的三个方法获取路径的方法 1： getPath() 获取相对路径，例如
 * ..\path\abc.txt 2： getAbslutlyPath() 获取绝对路径，但可能包含 ".." 或 "." 字符，例如
 * D:\otherPath\..\path\abc.txt 3： getCanonicalPath() 获取绝对路径，但不包含 ".." 或 "."
 * 字符，例如 D:\path\abc.txt
 */
public class PathUtil {
    private static String rootClassPath;

    public static String getPath(Class<?> clazz) {
        String path = clazz.getResource("").getPath();
        return new File(path).getAbsolutePath();
    }

    public static String getPath(Object object) {
        String path = object.getClass().getResource("").getPath();
        return new File(path).getAbsolutePath();
    }

    // 注意：命令行返回的是命令行所在的当前路径
    public static String getRootClassPath() {
        if (rootClassPath == null) {
            try {
                String path = PathUtil.class.getClassLoader().getResource("").toURI().getPath();
                rootClassPath = new File(path).getAbsolutePath();
            } catch (Exception e) {
                String path = PathUtil.class.getClassLoader().getResource("").getPath();
                rootClassPath = new File(path).getAbsolutePath();
            }
        }
        return rootClassPath;
    }

    public static void setRootClassPath(String rootClassPath) {
        PathUtil.rootClassPath = rootClassPath;
    }

    public static String getPackagePath(Object object) {
        Package p = object.getClass().getPackage();
        return p != null ? p.getName().replaceAll("\\.", "/") : "";
    }

    public static File getFileFromJar(String file) {
        throw new RuntimeException("Not finish. Do not use this method.");
    }

    public static String getBaseReourcePath(Class<?> clazz, String split) {
        String path = getPath(clazz);
        int endIndex = path.indexOf(split);
        if (endIndex != -1) {
            path = path.substring(0, endIndex);
        }
        return path;
    }

    public static String getBaseReourcePath(Class<?> clazz) {
        String path = getBaseReourcePath(clazz, "classes");
        path += "classes" + File.separator + "templates" + File.separator;
        return path;
    }

    public static boolean isAbsolutelyPath(String path) {
        return path.startsWith("/") || path.indexOf(":") == 1;
    }
}