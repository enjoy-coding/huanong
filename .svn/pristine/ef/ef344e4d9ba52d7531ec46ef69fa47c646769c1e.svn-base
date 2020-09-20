package com.feather.aupipes.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class FeatureServer {
    /**
     * 请求gis服务
     * 
     * @param baseUrl
     * @param params
     * @return
     */
    public static String DoPost2GIS(String url, String params) {
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url.split("\\?")[0]);

            HttpURLConnection conn = (HttpURLConnection) (realUrl.openConnection());

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(params.length()));

            outputStream = conn.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream, "utf-8");

            outputStreamWriter.write(params);
            outputStreamWriter.flush();

            // System.out.println("发送GIS服务参数："+url+" "+params);
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            // System.out.println("GIS服务返回结果："+result+"\n");
        } catch (Exception e) {
            // System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        } finally {
            try {
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                return "{'message':'图形操作服务异常','command':'error'}";
            }
        }
        return result;
    }

    /**
     * 转换度分秒 转
     * 
     * @param d
     * @return
     */
    public static String trandu2m(double d) {
        try {

            String str = "" + d;

            int p = str.indexOf(".");
            int dt = Integer.parseInt(str.substring(0, p));
            d = d - dt;
            double M = d * 60;
            int mt = (int) M;
            M = (M - mt) * 60;
            if (Math.abs(M - 60) < 0.001) {
                M = 0;
                mt = mt + 1;

            }
            if (mt == 60) {
                dt = dt + 1;
                mt = 0;
            }
            return "" + dt + "°" + mt + "′" + M + "″";
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    private static float convertRationalLatLonToFloat(String rationalString, String ref) {
        try {
            String[] parts = rationalString.split(",");

            String[] pair;
            pair = parts[0].split("/");
            double degrees = Double.parseDouble(pair[0].trim()) / Double.parseDouble(pair[1].trim());

            pair = parts[1].split("/");
            double minutes = Double.parseDouble(pair[0].trim()) / Double.parseDouble(pair[1].trim());

            pair = parts[2].split("/");
            double seconds = Double.parseDouble(pair[0].trim()) / Double.parseDouble(pair[1].trim());

            double result = degrees + (minutes / 60.0) + (seconds / 3600.0);
            if ((ref.equals("S") || ref.equals("W"))) {
                return (float) -result;
            }
            return (float) result;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException();
        }

    }
}
