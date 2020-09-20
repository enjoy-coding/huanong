package com.feather.test;

import java.util.HashMap;
import java.util.Map;

import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.model.message.TemplateData;
import org.jeewx.api.core.req.model.message.TemplateMessageSendResult;
import org.jeewx.api.wxsendmsg.JwSendTemplateMsgAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.feather.wx.util.MySendMessageApi;
import com.feather.wx.vo.Article;
import com.feather.wx.vo.Articles;
import com.feather.wx.vo.Message;

/**
 * @author nothing
 * @date 2019-12-05 13:46
 */
public class Main {
    protected static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static final String APP_ID = "wx2561f20ef25e9f62";
    public static final String APP_SCRET = "e6bb9c199435c38e06eee52223b414b7";

    public static void main(String args[]) {
        try {
            // String accessToken = JwTokenAPI.getAccessToken(APP_ID,
            // APP_SCRET);
            /*
             * String accessToken = JwTokenAPI.getAccessToken(APP_ID,
             * APP_SCRET);
             *//*
               * WxMedia wxMedia = new WxMedia(); wxMedia.setFileName(
               * "\\profile\\upload\\2019\\12\\09\\6e16a56cf4678151615848aa60d22d26-jpeg\\1.jpg"
               * ); wxMedia.setFilePath(" http:\\nothing.free.idcfengye.com");
               * wxMedia.setType("image");
               * JwSendMessageAPI.messagePrivate(accessToken,
               * "oj6-Ev5urjhJr21hpgTN_hpkwOFs", wxMedia);
               *//*
                 * String filePath =
                 * "http://nothing.free.idcfengye.com/profile/upload/2019/12/09/8148ac2c5143cdf50783ad38a28e6d12.png";
                 * MySendMessageApi.messagePrivateImage(accessToken,
                 * "oj6-Ev5urjhJr21hpgTN_hpkwOFs", filePath);
                 */
            // JSONObject jsonObject = JwMediaAPI.sendMedia("image",
            // "http:\\nothing.free.idcfengye.com\\profile\\upload\\2019\\12\\09\\6e16a56cf4678151615848aa60d22d26-jpeg\\1.jpg",
            // accessToken);

            // System.out.println(accessToken);
            /*
             * Text text = new Text(); TextEntity textEntity = new TextEntity();
             * text.setMsgtype("text");
             * text.setTouser("oj6-Ev5urjhJr21hpgTN_hpkwOFs");
             * textEntity.setContent("用户：123\r\nname:456\r\n");
             * text.setText(textEntity); JSONObject jsonObject =
             * JwMessageAPI.sendTextMessage(text,
             * "28_4OJvULQ7OV6m2j9Z8Ch4W8Ql0Ix6qxLJ0hNlj8WPrN3gOfI6Thwyn4JlvjkTsLbx7Vaq1wj22L2lXi9WKrcO0sVdMXegcuwVktUEAThNKZRsrIz_eti1QHLeNr3q964v0N8CtjK29x-53LYgGDCeAGAVSR"
             * );
             * 
             * System.out.println(jsonObject);
             */

            // String accessToken =
            // "28_8mNSAy0RFthcgkUw8z4ANLGq1IFDCkM2vKn1KoFy2CyTGF8ftT4aXmo44TBPAkgztQNC6c7eJdMtpGEV3SBwHDF_5uUwx8ftBVXiHuijilSAB6NaqrTtwDir6ir-O5sxmSXH0cf7Ro9quJbcWJWjADAJWJ";

            logger.info("test{}", "123");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getTemplate(String accessToken) throws WexinReqException {
        String str = JwSendTemplateMsgAPI.getTemplateList(accessToken);
        System.out.println(str);
        return str;
    }

    public static void sendTemplateMsg(String accessToken) throws WexinReqException {
        TemplateMessageSendResult sendResult = new TemplateMessageSendResult();
        sendResult.setTouser("oj6-Ev5urjhJr21hpgTN_hpkwOFs");
        sendResult.setTemplate_id("BDaoVebSRwLw9GZpDjAbX20iY8Xk3HUN4eLc9kkajyc");
        Map<String, TemplateData> data = new HashMap<String, TemplateData>();

        data.put("aa", new TemplateData("123", "#173177"));
        data.put("aa",
                new TemplateData(
                        "http://mmbiz.qpic.cn/mmbiz_jpg/TxLqayMQAelJSmuQgRpSN8kYCmTt9x0WclNQMp9VCibvDTpkkwwFscscPVus4nZBvQibicqtgiaouibAzPkVZP8hWAw/0",
                        "#173177"));
        sendResult.setData(data);
        JwSendTemplateMsgAPI.sendTemplateMsg(accessToken, sendResult);
    }

    private static String str() {
        StringBuffer sb = new StringBuffer();
        String meetingTitle = "会议标题";
        String userName = "姓名";
        String school = "学校";
        String unit = "单位";
        String job = "职务";
        String address = "地址";
        String code = "邮编";
        String mobile = "手机号码";
        String telephone = "固定电话";
        sb.append("会议标题:").append(meetingTitle).append("\r\n").append("姓   名:").append(userName).append("\r\n")
                .append("学校:").append(school).append("\r\n").append("单位:").append(unit).append("\r\n").append("职务:")
                .append(job).append("\r\n").append("地址:").append(address).append("\r\n").append("邮编:").append(code)
                .append("\r\n").append("手机号码:").append(mobile).append("\r\n").append("固定电话:").append(telephone)
                .append("\r\n");

        return sb.toString();
    }

    public static void createButton(String accessToken) {
        try {
            // WeixinButton b1 = new WeixinButton();
            // b1.setName("8888");
            // b1.setType("view");
            /*
             * WeixinButton b2 = new WeixinButton(); b2.setName("菜单");
             * List<WeixinButton> subLevel = new ArrayList<WeixinButton>();
             * WeixinButton sub2_b1 = new WeixinButton(); sub2_b1.setName("w2");
             * sub2_b1.setType("view"); sub2_b1.setUrl(
             * "https://open.weixin.qq.cogaom/connect/oauth2/authorize?appid=wx2561f20ef25e9f62&redirect_uri=http%3a%2f%2fnothing.free.idcfengye.com%2Fwx%2FgetOpenId%3F&response_type=code&scope=snsapi_base&state=redirectUrl=nothing.free.idcfengye.com?path=index#wechat_redirect"
             * );
             * 
             * WeixinButton sub2_b2 = new WeixinButton(); sub2_b2.setName("32");
             * sub2_b2.setType("view"); sub2_b2.setUrl(
             * "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx2561f20ef25e9f62&redirect_uri=http%3a%2f%2fnothing.free.idcfengye.com&response_type=code&scope=snsapi_base&state=redirectUrl=nothing.free.idcfengye.com?path=index#wechat_redirect"
             * );
             *//*
               * WeixinButton sub2_b2 = new WeixinButton();
               * sub2_b1.setName("赞一下我们"); sub2_b1.setType("click");
               * sub2_b1.setKey("V1001_GOOD");
               *//*
                 * subLevel.add(sub2_b1); subLevel.add(sub2_b2); //
                 * subLevel.add(sub2_b2); b2.setSub_button(subLevel);
                 * List<WeixinButton> oneLevel = new ArrayList<WeixinButton>();
                 * oneLevel.add(b1); oneLevel.add(b2);
                 */
            /*
             * List<WeixinButton> oneLevel = new ArrayList<WeixinButton>();
             * oneLevel.add(b1); JwMenuAPI.createMenu(accessToken, oneLevel);
             */

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void sendMsg(String accessToken, String content) {
        try {
            MySendMessageApi.messagePrivate(accessToken, "oj6-Ev5urjhJr21hpgTN_hpkwOFs", content);
        } catch (WexinReqException e) {
            e.printStackTrace();
        }
    }

    private static void sendCustomMessage(String openId, String accessToken) {
        try {
            RestTemplate rest = new RestTemplate();
            String postUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken;
            // 推送图文消息
            Message message = new Message();
            message.setTouser(openId);// 普通用户openid
            message.setMsgtype("news");// 图文消息（点击跳转到外链）为news
            Articles news = new Articles();
            Article article = new Article();
            article.setDescription("客服消息图文描述");// 图文消息/视频消息/音乐消息的描述
            article.setPicUrl(
                    "http://mmbiz.qpic.cn/mmbiz_jpg/CDW6Ticice130g6RcXCkNNDWic4dEaAHQDia2OG5atHBqSvsPuCfuqoyeeLWENia4ciaKt3KHWQ9t2LRPDpUo5AkOyyA/0");// 图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80
            article.setTitle("客服消息图文标题");// 图文消息/视频消息/音乐消息的标题
            // 图文推送链接
            String url = "https://www.baidu.com";
            article.setUrl(url);// 图文消息被点击后跳转的链接
            Article[] articles = { article };
            news.setArticles(articles);
            message.setNews(news);
            int i = 1;
            while (i <= 3) {// 循环发送3次
                WeixinResponse response = rest.postForObject(postUrl, message, WeixinResponse.class,
                        new HashMap<String, String>());
                if (response.getErrcode() == 0) {// 发送成功-退出循环发送
                    i = 4;
                    break;
                } else {
                    i++;// 发送失败-继续循环发送
                }
            }
        } catch (Exception e) {
        }
    }
}
