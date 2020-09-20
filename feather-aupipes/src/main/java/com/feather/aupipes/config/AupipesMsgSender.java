package com.feather.aupipes.config;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jeewx.api.core.req.model.message.TemplateData;
import org.jeewx.api.core.req.model.message.TemplateMessageSendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.feather.aupipes.HttpClient.HttpClientUtil;
import com.feather.common.config.TopicMessageDelegate;
import com.feather.common.json.JSONObject;
import com.feather.common.utils.StringUtils;
import com.feather.common.utils.security.Md5Utils;
import com.feather.prd.domain.PrdMsg;
import com.feather.prd.service.PrdMsgSender;
import com.feather.system.domain.SysUser;
import com.feather.system.mapper.SysUserMapper;
import com.feather.wx.service.WxManagerService;

@Configuration
public class AupipesMsgSender implements PrdMsgSender {
    public static final String WX_MSG = "wx";
    public static final String SMS_MSG = "sms";

    private static Map<String, AupipesWxTemplateMsg> AUP_WX_TEMPLATE = new HashMap<>();
    static {
        // 消息提醒
        AUP_WX_TEMPLATE.put(AupipesNoticeWxMsg.TYPE, new AupipesNoticeWxMsg());
        // 预警通知
        AUP_WX_TEMPLATE.put(AupipesWarningWxMsg.TYPE, new AupipesWarningWxMsg());
        // 停电通知
        AUP_WX_TEMPLATE.put(AupipesBlackoutWxMsg.TYPE, new AupipesBlackoutWxMsg());
        // 停水通知
        AUP_WX_TEMPLATE.put(AupipesWaterShutoffWxMsg.TYPE, new AupipesWaterShutoffWxMsg());
        // 巡检结果通知
        AUP_WX_TEMPLATE.put(AupipesInspectWxMsg.TYPE, new AupipesInspectWxMsg());
        // 工单处理通知
        AUP_WX_TEMPLATE.put(AupipesWorkOrderWxMsg.TYPE, new AupipesWorkOrderWxMsg());
    }

    private static final String APIID = "cf_sfdz";
    private static final String APIKEY = "e10adc3949ba59abbe56e057f20f883e";

    private static final String REDIS_KEY_PREFIX_WX_MSG = "wxmsg";

    private String topic = "/aup/websocket/msg-push";

    @Value("${aupipes.smsapi}")
    private String smsapi;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private WxManagerService wxManagerService;

    @Autowired
    private TopicMessageDelegate topicMessageDelegate;

    public void setSmsapi(String smsapi) {
        this.smsapi = smsapi;
    }

    public String getSmsapi() {
        return smsapi;
    }

    @Override
    public void check(PrdMsg prdMsg) {

    }

    @Override
    public Exception send(PrdMsg prdMsg, Long userId) {
        sendWebSocketMessage(prdMsg.getMsgContent());
        SysUser user = sysUserMapper.selectUserById(userId);
        String msgApp = prdMsg.getMsgApp();
        switch (msgApp) {
        case WX_MSG: {
            return sendWxMessage(prdMsg, user);
        }
        case SMS_MSG: {
            return sendSmsMessage(prdMsg, user);
        }
        }
        return new RuntimeException("未识别的应用类型");
    }

    /**
     * 调用接口，发送微信信息
     */
    public Exception sendWxMessage(PrdMsg prdMsg, SysUser user) {
        AupipesWxTemplateMsg aupWxTempMsg = AUP_WX_TEMPLATE.get(prdMsg.getMsgType());
        if (aupWxTempMsg == null) {
            return new RuntimeException("不支持的消息类型：" + prdMsg.getMsgType());
        }
        String msgContent = prdMsg.getMsgContent();
        if (msgContent == null) {
            msgContent = "";
        }
        int at = -1;
        String pdfCZQ = "配电房操作区";
        if ((at = msgContent.indexOf(pdfCZQ)) > 0) {
            msgContent = msgContent.substring(0, at + pdfCZQ.length());
        }
        String cttMD5 = Md5Utils.hash(msgContent);
        String key = String.format("%s-%s-%s", REDIS_KEY_PREFIX_WX_MSG, cttMD5, user.getUserId());
        String value = wxManagerService.getStringRedisTemplate().opsForValue().get(key);
        if (StringUtils.isNotEmpty(value)) {
            return new RuntimeException("相同内容，30分钟内不予发送");
        }

        try {
            TemplateMessageSendResult tempMessage = aupWxTempMsg.getTemplateMessage(prdMsg, user);
            String result = wxManagerService.sendTemplateMsg(tempMessage);

            JSONObject parse = JSONObject.parse(result);
            if (parse.getInt("errcode") == 0) {
                wxManagerService.getStringRedisTemplate().opsForValue().set(key, "1", 30, TimeUnit.MINUTES);
                return null;
            }
            return new RuntimeException(result);
        } catch (Exception ex) {
            return ex;
        }
    }

    /**
     * 调用接口，发送手机信息
     */
    public Exception sendSmsMessage(PrdMsg prdMsg, SysUser user) {
        if (StringUtils.isBlank(smsapi)) {
            return new RuntimeException("未指定接口");
        }
        Map<String, String> params = new HashMap<>();
        String phone = user.getPhonenumber();
        String content = prdMsg.getMsgContent();

        params.put("account", APIID);
        params.put("password", APIKEY);
        params.put("mobile", phone);
        params.put("content", content);
        String resultStr = HttpClientUtil.httpPostWithJSON(smsapi, params);
        try {
            Document doc = DocumentHelper.parseText(resultStr);
            Element root = doc.getRootElement();
            String code = root.elementText("code");
            if ("2".equals(code)) {
                return null;
            }
            return new RuntimeException(resultStr);
        } catch (DocumentException ex) {
            return ex;
        }
    }

    private void sendWebSocketMessage(String content) {
        topicMessageDelegate.getDelegate(topic).send(content);
    }

    public static void main(String[] args) {
        Map<String, String> params = new HashMap<>();
        String phone = "13871544150";
        String content = "跌幅达飞而对对对的";

        params.put("account", APIID);
        params.put("password", APIKEY);
        params.put("mobile", phone);
        params.put("content", content);
        String resultStr = HttpClientUtil.httpPostWithJSON("http://106.ihuyi.cn/webservice/sms.php?method=Submit",
                params);
        System.out.println(resultStr);
    }

    // 消息提醒
    public static class AupipesNoticeWxMsg implements AupipesWxTemplateMsg {
        public static final String TYPE = "notice";

        @Override
        public String getTemplateType() {
            return TYPE;
        }

        @Override
        public String getTemplateId() {
            // TODO:
            return "";
        }

        @Override
        public TemplateMessageSendResult getTemplateMessage(PrdMsg prdMsg, SysUser user) {
            // TODO:
            return null;
        }
    }

    // 预警通知
    public static class AupipesWarningWxMsg implements AupipesWxTemplateMsg {
        public static final String TYPE = "warning";

        @Override
        public String getTemplateType() {
            return TYPE;
        }

        @Override
        public String getTemplateId() {
            return "xvbtwzkBe8Ne_LVCrShmenVsY5PBYHs772Ct9Xm1vwA";
        }

        @Override
        public TemplateMessageSendResult getTemplateMessage(PrdMsg prdMsg, SysUser user) {
            String msgContent = prdMsg.getMsgContent();
            Pattern p = Pattern.compile("\\s*|\t|\r|\n|&nbsp;");
            Matcher m = p.matcher(msgContent);

            String title = prdMsg.getMsgTitle();

            String content = m.replaceAll("");
            String date = prdMsg.getSendTime();

            String color = "#000000";
            TemplateMessageSendResult sendResult = new TemplateMessageSendResult();
            Map<String, TemplateData> data = new HashMap<>();
            data.put("first", new TemplateData(content, color));
            data.put("keyword1", new TemplateData(title, color));
            data.put("keyword2", new TemplateData(date, color));
            data.put("remark", new TemplateData("", color));
            sendResult.setTouser(user.getOpenid());
            sendResult.setTemplate_id(getTemplateId());
            sendResult.setData(data);
            return sendResult;
        }
    }

    // 停电通知
    public static class AupipesBlackoutWxMsg implements AupipesWxTemplateMsg {
        public static final String TYPE = "blackout";

        @Override
        public String getTemplateType() {
            return TYPE;
        }

        @Override
        public String getTemplateId() {
            return "VeWedb_KxLmqSGjVfU_1pp9WqvoGYavN4Go0oYNT-xo";
        }

        @Override
        public TemplateMessageSendResult getTemplateMessage(PrdMsg prdMsg, SysUser user) {
            String msgContent = prdMsg.getMsgContent();
            Pattern p = Pattern.compile("\\s*|\t|\r|\n|&nbsp;");
            Matcher m = p.matcher(msgContent);

            String content = m.replaceAll("");
            String date = prdMsg.getSendTime();

            String color = "#000000";
            TemplateMessageSendResult sendResult = new TemplateMessageSendResult();
            Map<String, TemplateData> data = new HashMap<>();
            data.put("first", new TemplateData(content, color));
            data.put("keyword1", new TemplateData("", color));
            data.put("keyword2", new TemplateData(date, color));
            data.put("remark", new TemplateData("", color));
            sendResult.setTouser(user.getOpenid());
            sendResult.setTemplate_id(getTemplateId());
            sendResult.setData(data);
            return sendResult;
        }
    }

    // 停水通知
    public static class AupipesWaterShutoffWxMsg implements AupipesWxTemplateMsg {
        public static final String TYPE = "water_shutoff";

        @Override
        public String getTemplateType() {
            return TYPE;
        }

        @Override
        public String getTemplateId() {
            return "GG-TgITZVhLvEPqSvC7GgpsQElJ4JlwZQXhQ5ZZ_JwI";
        }

        @Override
        public TemplateMessageSendResult getTemplateMessage(PrdMsg prdMsg, SysUser user) {
            String msgContent = prdMsg.getMsgContent();
            Pattern p = Pattern.compile("\\s*|\t|\r|\n|&nbsp;");
            Matcher m = p.matcher(msgContent);

            String content = m.replaceAll("");
            String date = prdMsg.getSendTime();

            String color = "#000000";
            TemplateMessageSendResult sendResult = new TemplateMessageSendResult();
            Map<String, TemplateData> data = new HashMap<>();
            data.put("first", new TemplateData(content, color));
            data.put("keyword1", new TemplateData("", color));
            data.put("keyword2", new TemplateData(date, color));
            data.put("remark", new TemplateData("", color));
            sendResult.setTouser(user.getOpenid());
            sendResult.setTemplate_id(getTemplateId());
            sendResult.setData(data);
            return sendResult;
        }
    }

    // 巡检结果通知
    public static class AupipesInspectWxMsg implements AupipesWxTemplateMsg {
        public static final String TYPE = "inspect";

        @Override
        public String getTemplateType() {
            return TYPE;
        }

        @Override
        public String getTemplateId() {
            return "DkdXrAae29_BenqZio9h01-LFvSKfRgZSnW7LTltPLw";
        }

        @Override
        public TemplateMessageSendResult getTemplateMessage(PrdMsg prdMsg, SysUser user) {
            String msgContent = prdMsg.getMsgContent();
            Pattern p = Pattern.compile("\\s*|\t|\r|\n|&nbsp;");
            Matcher m = p.matcher(msgContent);

            String content = m.replaceAll("");
            String date = prdMsg.getSendTime();

            String color = "#000000";
            TemplateMessageSendResult sendResult = new TemplateMessageSendResult();
            Map<String, TemplateData> data = new HashMap<>();
            data.put("first", new TemplateData(content, color));
            data.put("keyword1", new TemplateData(date, color));
            data.put("keyword2", new TemplateData("", color));
            data.put("remark", new TemplateData("", color));
            sendResult.setTouser(user.getOpenid());
            sendResult.setTemplate_id(getTemplateId());
            sendResult.setData(data);
            return sendResult;
        }
    }

    // 工单处理通知
    public static class AupipesWorkOrderWxMsg implements AupipesWxTemplateMsg {
        public static final String TYPE = "work_order";

        @Override
        public String getTemplateType() {
            return TYPE;
        }

        @Override
        public String getTemplateId() {
            return "MTdyLkYadi5apnJg4Ghkc5d33KUtChSe1-4Rm4SutdI";
        }

        @Override
        public TemplateMessageSendResult getTemplateMessage(PrdMsg prdMsg, SysUser user) {
            // TODO:
            return null;
        }
    }
}
