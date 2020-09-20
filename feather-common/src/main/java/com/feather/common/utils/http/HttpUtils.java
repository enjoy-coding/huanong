package com.feather.common.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.opensagres.xdocreport.core.utils.StringUtils;

/**
 * 通用http发送方法
 * 
 * @author feather
 */
public class HttpUtils {
    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 向指定 URL 发送GET方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @return 所代表远程资源的响应结果
     */
    public static String sendGet(String url) {
        return (String) send(url, null, new InputStreamCallback() {
            @Override
            public Object handle(InputStream is) throws IOException {
                StringBuilder builder = new StringBuilder();
                try (BufferedReader in = new BufferedReader(new InputStreamReader(is))) {
                    String line = null;
                    while ((line = in.readLine()) != null) {
                        builder.append(line);
                    }
                }
                return builder.toString();
            }
        }, false);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        return (String) send(url, param, new InputStreamCallback() {
            @Override
            public Object handle(InputStream is) throws IOException {
                StringBuilder builder = new StringBuilder();
                try (BufferedReader in = new BufferedReader(new InputStreamReader(is))) {
                    String line;
                    while ((line = in.readLine()) != null) {
                        builder.append(line);
                    }
                }
                return builder.toString();
            }
        }, true);
    }

    public static Object send(String url, String param, InputStreamCallback callback, boolean post) {
        InputStream is = null;
        PrintWriter out = null;
        Object result = null;
        try {
            log.info("sendGet - {}", url);
            URL realUrl = null;
            if (post) {
                realUrl = new URL(url);
            } else {
                realUrl = new URL(url + (StringUtils.isNotEmpty(param) ? "?" + param : ""));
            }
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            if (post) {
                conn.setRequestProperty("Accept-Charset", "utf-8");
                conn.setRequestProperty("contentType", "utf-8");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setUseCaches(false);
                out = new PrintWriter(conn.getOutputStream());
                out.print(param);
                out.flush();
            } else {
                conn.connect();
            }
            if (callback != null) {
                is = conn.getInputStream();
                result = callback.handle(is);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        return result;
    }

    public static String sendSSLPost(String url, String param) {
        StringBuilder result = new StringBuilder();
        String urlNameString = url + "?" + param;
        try {
            log.info("sendSSLPost - {}", urlNameString);
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
            URL console = new URL(urlNameString);
            HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String ret = "";
            while ((ret = br.readLine()) != null) {
                if (ret != null && !ret.trim().equals("")) {
                    result.append(new String(ret.getBytes("ISO-8859-1"), "utf-8"));
                }
            }
            log.info("recv - {}", result);
            conn.disconnect();
            br.close();
        } catch (ConnectException e) {
            log.error("调用HttpUtils.sendSSLPost ConnectException, url=" + url + ",param=" + param, e);
        } catch (SocketTimeoutException e) {
            log.error("调用HttpUtils.sendSSLPost SocketTimeoutException, url=" + url + ",param=" + param, e);
        } catch (IOException e) {
            log.error("调用HttpUtils.sendSSLPost IOException, url=" + url + ",param=" + param, e);
        } catch (Exception e) {
            log.error("调用HttpsUtil.sendSSLPost Exception, url=" + url + ",param=" + param, e);
        }
        return result.toString();
    }

    private static class TrustAnyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    public static interface InputStreamCallback {
        Object handle(InputStream is) throws IOException;
    }
}