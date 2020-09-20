package com.feather.wx.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feather.common.json.JSONObject;

/**
 * @author nothing
 * @date 2019-12-10 19:38
 */

import com.feather.wx.vo.MediaContent;
import com.feather.wx.vo.Message;
import com.feather.wx.vo.TextContent;

public class MyMessageAPI {
    private static final Logger logger = LoggerFactory.getLogger(MyMessageAPI.class);

    private static String message_send_url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
    // 上传媒体资源URL
    private static String upload_media_url = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

    public MyMessageAPI() {
    }

    public static JSONObject sendTextMessage(String accessToken, String openId, String content) {
        String url = message_send_url.replace("ACCESS_TOKEN", accessToken);

        TextContent textContent = TextContent.builder().content(content).build();
        Message message = Message.builder().touser(openId).text(textContent).msgtype("text").build();
        String jsonString = JSONObject.toJsonString(message);
        JSONObject jsonObject = JeecgHttpUtil.sendPost(url, jsonString);
        return jsonObject;
    }

    public static JSONObject sendImageMessage(String accessToken, String openId, String filePath) throws Exception {
        String url = message_send_url.replace("ACCESS_TOKEN", accessToken);
        String mediaId = getMediaIdFromUrl(accessToken, filePath);
        MediaContent mediaContent = MediaContent.builder().mediaId(mediaId).build();
        Message message = Message.builder().touser(openId).image(mediaContent).msgtype("image").build();
        String jsonString = JSONObject.marshal(message);
        JSONObject jsonObject = JeecgHttpUtil.sendPost(url, jsonString);
        return jsonObject;
    }

    private static String getMediaIdFromUrl(String accessToken, String urlPath) throws Exception {
        String result = null;
        String requestUrl = upload_media_url.replace("ACCESS_TOKEN", accessToken).replace("TYPE", "image");
        String fileName = urlPath.substring(urlPath.lastIndexOf("/") + 1);
        // 获取网络图片
        URL mediaUrl = new URL(urlPath);
        HttpURLConnection meidaConn = (HttpURLConnection) mediaUrl.openConnection();
        meidaConn.setDoOutput(true);
        meidaConn.setRequestMethod("GET");

        /**
         * 第一部分
         */
        URL urlObj = new URL(requestUrl);
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
        // 以Post方式提交表单，默认get方式
        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        // post方式不能使用缓存
        con.setUseCaches(false);
        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        // 设置边界
        String boundary = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        // 请求正文信息
        // 第一部分：
        StringBuilder sb = new StringBuilder();
        // 必须多两道线
        sb.append("--");
        sb.append(boundary);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"media\";filename=\"" + fileName + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");
        byte[] head = sb.toString().getBytes("utf-8");
        // 获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出表头
        out.write(head);
        // 文件正文部分
        // 把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(meidaConn.getInputStream());
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();
        // 结尾部分,定义最后数据分隔线
        byte[] foot = ("\r\n--" + boundary + "--\r\n").getBytes("utf-8");
        out.write(foot);
        out.flush();
        out.close();
        meidaConn.disconnect();
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            logger.info("发送POST请求出现异常！ {}", e);
            logger.error(e.getMessage(), e);
            throw new IOException("数据读取异常");
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        JSONObject jsonObj = JSONObject.unmarshal(result);
        logger.info("getMediaId jsonObj: {}", jsonObj);
        return jsonObj.getString("media_id");
    }
}
