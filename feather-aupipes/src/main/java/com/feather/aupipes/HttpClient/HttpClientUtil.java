package com.feather.aupipes.HttpClient;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feather.common.json.JSONObject;

/**
 * HttpClient工具类
 */
public class HttpClientUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 请求编码
     */
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 执行post请求
     *
     * @param url
     *            路径
     * @param param
     *            请求参数
     */
    public static String httpPostWithJSON(String url, Map<String, ?> param) {
        CloseableHttpClient client = null;
        try {
            if (url == null || url.trim().length() == 0) {
                throw new Exception("URL is null");
            }
            HttpPost httpPost = new HttpPost(url);
            client = HttpClients.createDefault();
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            if (param != null) {
                // BasicNameValuePair封装post请求中的参数名称和值
                for (Map.Entry<String, ?> entry : param.entrySet()) {
                    if (entry.getValue() instanceof String) {
                        nvps.add(new BasicNameValuePair(entry.getKey(), (String) entry.getValue()));
                    }
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvps, DEFAULT_CHARSET);
                httpPost.setEntity(entity);
            }
            HttpResponse resp = client.execute(httpPost);
            if (resp.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(resp.getEntity(), DEFAULT_CHARSET);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            close(client);
        }
        return null;
    }

    /**
     * 执行get请求
     *
     * @param url
     *            请求路径
     * @param param
     *            请求参数
     * @return
     */
    public static String httpGetWithJSON(String url, Map<String, ?> param) {
        CloseableHttpClient client = null;
        try {
            if (url == null || url.trim().length() == 0) {
                throw new Exception("URL is null");
            }
            client = HttpClients.createDefault();
            if (param != null) {
                StringBuffer sb = new StringBuffer("?");
                for (String key : param.keySet()) {
                    sb.append(key).append("=").append(param.get(key)).append("&");
                }
                url = url.concat(sb.toString());
                url = url.substring(0, url.length() - 1);
            }
            HttpGet httpGet = new HttpGet(url);
            HttpResponse resp = client.execute(httpGet);
            if (resp.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(resp.getEntity(), DEFAULT_CHARSET);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            close(client);
        }
        return null;
    }

    /**
     * 请求参数
     * 
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public static String httpPostWithjson(String url, String json) {
        String result = "";
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            BasicResponseHandler handler = new BasicResponseHandler();
            StringEntity entity = new StringEntity(json, "utf-8");// 解决中文乱码问题
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            result = httpClient.execute(httpPost, handler);
            return result;
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                close(httpClient);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 关闭Http请求
     *
     * @param client
     */
    private static void close(CloseableHttpClient client) {
        if (client == null) {
            return;
        }
        try {
            client.close();
        } catch (Exception e) {

        }
    }

    /*
     * 获取cookie
     *
     */
    public static JSONObject post4cookie(String url, JSONObject reqParam) {
        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        try {
            // 创建httppost.
            HttpPost httppost = new HttpPost(url);
            RequestConfig defaultRequestConfig = RequestConfig.custom().setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000).setSocketTimeout(15000).build();
            httppost.setConfig(defaultRequestConfig);
            // System.out.println("executing request " + httppost.getURI());
            // 装填参数
            StringEntity reqEntity = new StringEntity(reqParam.toString(), Charset.forName("UTF-8"));
            reqEntity.setContentType("application/json");
            httppost.setEntity(reqEntity);
            CloseableHttpResponse response = httpclient.execute(httppost);
            // System.out.println("got response");
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    JSONObject res = new JSONObject();
                    String httpRes = EntityUtils.toString(entity, Charset.forName("UTF-8"));
                    res.put("httpRes", httpRes);
                    JSONObject cookie = new JSONObject();
                    List<Cookie> cookies = cookieStore.getCookies();
                    for (int i = 0; i < cookies.size(); i++) {
                        cookie.put(cookies.get(i).getName(), cookies.get(i).getValue());
                    }
                    res.put("cookie", cookie);
                    return res;
                }
            } finally {
                response.close();

            }
        } catch (Exception e) {
            return null;
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /*
     *
     * 根据cookie和参数请求数据
     *
     */
    public static String post(String url, String reqParam, String cookie) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(url);
            RequestConfig defaultRequestConfig = RequestConfig.custom().setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000).setSocketTimeout(15000).build();
            httppost.setConfig(defaultRequestConfig);
            httppost.addHeader(new BasicHeader("Cookie", "AXWEBSID=" + cookie));
            // System.out.println("executing request " + httppost.getURI());
            StringEntity reentity = new StringEntity(reqParam);
            httppost.setEntity(reentity);
            CloseableHttpResponse response = httpclient.execute(httppost);
            // System.out.println("got response");
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity, Charset.forName("UTF-8"));
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    /*
     *
     * 根据cookie和参数请求数据
     *
     */
    public static JSONObject posts(String url) {
        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        try {
            HttpPost httppost = new HttpPost(url);
            RequestConfig defaultRequestConfig = RequestConfig.custom().setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000).setSocketTimeout(15000).build();
            httppost.setConfig(defaultRequestConfig);
            // System.out.println("executing request " + httppost.getURI());

            CloseableHttpResponse response = httpclient.execute(httppost);
            // System.out.println("got response");
            try {
                // 获取响应实体
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    JSONObject res = new JSONObject();
                    String httpRes = EntityUtils.toString(entity, Charset.forName("UTF-8"));
                    res.put("httpRes", httpRes);
                    JSONObject cookie = new JSONObject();
                    List<Cookie> cookies = cookieStore.getCookies();
                    for (int i = 0; i < cookies.size(); i++) {
                        cookie.put(cookies.get(i).getName(), cookies.get(i).getValue());
                    }
                    res.put("cookie", cookie);
                    return res;
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /*
     *
     * 根据cookie和参数请求数据
     *
     */
    public static String getCelou(String url,  String cookie) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httppost = new HttpGet(url);
            RequestConfig defaultRequestConfig = RequestConfig.custom().setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000).setSocketTimeout(15000).build();
            httppost.setConfig(defaultRequestConfig);
            httppost.addHeader(new BasicHeader("Cookie", cookie));
            // System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            // System.out.println("got response");
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity, Charset.forName("UTF-8"));
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    /*
     *
     * 根据cookie和参数请求数据
     *
     */
    public static String getPump(String url,  String cookie) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httppost = new HttpGet(url);
            RequestConfig defaultRequestConfig = RequestConfig.custom().setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000).setSocketTimeout(15000).build();
            httppost.setConfig(defaultRequestConfig);
            httppost.addHeader(new BasicHeader("Cookie", "JSESSIONID=" + cookie));
            // System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            // System.out.println("got response");
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity, Charset.forName("UTF-8"));
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    
}
