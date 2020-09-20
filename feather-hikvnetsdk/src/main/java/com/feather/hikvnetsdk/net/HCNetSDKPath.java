package com.feather.hikvnetsdk.net;

import java.io.UnsupportedEncodingException;

public class HCNetSDKPath {
    private static String DLL_PATH;// HCNetSDK.dll 文件路径

    public static void main(String[] args) {
        getDllPath();
    }

    public static String getDllPath() {
        if (DLL_PATH == null) {
            String path = (HCNetSDKPath.class.getResource("/HCNetSDK/HCNetSDK.dll").getPath()).replaceAll("%20", " ")
                    .substring(1).replace("/", "\\");
            System.out.println(path);
            try {
                DLL_PATH = java.net.URLDecoder.decode(path, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            System.out.println(DLL_PATH);
        }
        return DLL_PATH;
    }
}
