package com.feather.wx.util;

import org.jeewx.api.core.common.WxstoreUtils;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.wxsendmsg.JwSendMessageAPI;

/**
 * @author nothing
 * @date 2019-12-09 10:26
 */
public class MySendMessageApi extends JwSendMessageAPI {
    // 消息预览URL
    public static final String message_preview_url = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=ACCESS_TOKEN";
    // 发送客服消息URL
    public static final String message_send_url = "https://api.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";
    // 上传媒体资源URL
    public static final String upload_media_url = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

    /**
     * 通过客服端口发送网络图片消息
     *
     * @param accessToken
     * @param toUser
     *            用户openId
     * @param urlPath
     *            路径
     */
    /*
     * public static void messageKeFuImage(String accessToken, String toUser,
     * String urlPath) throws WexinReqException { messageImage(accessToken,
     * toUser, message_send_url, urlPath); }
     * 
     * public static void messagePreviewImage(String accessToken, String toUser,
     * String urlPath) throws WexinReqException { messageImage(accessToken,
     * toUser, message_preview_url, urlPath); }
     */

    /**
     * 发送客服文本消息给用户
     * 
     * @param accessToken
     * @param toUser
     * @param content
     * @return
     * @throws WexinReqException
     */
    public static String messageKeFuText(String accessToken, String toUser, String content) throws WexinReqException {
        String ret = "";
        if (accessToken != null) {
            String requestUrl = message_send_url.replace("ACCESS_TOKEN", accessToken);
            try {

                net.sf.json.JSONObject obj = new net.sf.json.JSONObject();
                net.sf.json.JSONObject text = new net.sf.json.JSONObject();
                obj.put("touser", toUser);
                obj.put("msgtype", "text");
                text.put("content", content);
                obj.put("text", text);
                net.sf.json.JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
                // System.out.println("微信返回的结果：" + result.toString());
                ret = result.toString();
            } catch (Exception e) {
                throw new WexinReqException(e);
            }
        } else {
            throw new WexinReqException("accesstoken 为空，请检查！");
        }
        return ret;
    }

    public static String messageKeFuTextAndImage(String accessToken, String toUser, String title, String description,
            String urlPath) throws WexinReqException {
        String ret = "";
        if (accessToken != null) {
            String requestUrl = message_send_url.replace("ACCESS_TOKEN", accessToken);
            try {
                net.sf.json.JSONObject obj = new net.sf.json.JSONObject();
                net.sf.json.JSONObject news = new net.sf.json.JSONObject();
                net.sf.json.JSONObject article = new net.sf.json.JSONObject();
                net.sf.json.JSONArray articles = new net.sf.json.JSONArray();
                obj.put("touser", toUser);
                obj.put("msgtype", "news");
                article.put("title", title);
                article.put("description", description);
                article.put("picurl", urlPath);
                articles.add(article);
                news.put("articles", articles);
                obj.put("news", news);
                net.sf.json.JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
                // System.out.println("微信返回的结果：" + result.toString());
                ret = result.toString();
            } catch (Exception e) {
                throw new WexinReqException(e);
            }
        } else {
            throw new WexinReqException("accesstoken 为空，请检查！");
        }
        return ret;
    }

    /*
     * private static void messageImage(String accessToken, String toUser,
     * String send_url, String urlPath) throws WexinReqException { if
     * (accessToken != null) { String requestUrl =
     * send_url.replace("ACCESS_TOKEN", accessToken); try { WxMedia wxMedia =
     * new WxMedia(); wxMedia.setType("image"); String mediaId =
     * getMediaIdFromUrl(accessToken, urlPath); if (Strings.isNotBlank(mediaId))
     * { net.sf.json.JSONObject obj = new net.sf.json.JSONObject();
     * net.sf.json.JSONObject type = new net.sf.json.JSONObject();
     * obj.put("touser", toUser); obj.put("msgtype", wxMedia.getType());
     * type.put("media_id", mediaId); obj.put(wxMedia.getType(), type);
     * net.sf.json.JSONObject result = WxstoreUtils.httpRequest(requestUrl,
     * "POST", obj.toString()); //System.out.println("微信返回的结果：" +
     * result.toString()); if (result.getInt("errcode") != 0) {
     * log.errorf("多媒体消息预览失败！errcode=" + result.getString("errcode") +
     * ",errmsg = " + result.getString("errmsg")); throw new
     * Exception("多媒体消息预览失败！errcode=" + result.getString("errcode") +
     * ",errmsg = " + result.getString("errmsg")); } } } catch (Exception e) {
     * throw new WexinReqException(e); } } else { throw new
     * WexinReqException("accesstoken 为空，请检查！"); } }
     */

}
