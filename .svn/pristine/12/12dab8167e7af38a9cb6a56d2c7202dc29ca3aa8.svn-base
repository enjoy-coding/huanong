package com.feather.wx.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feather.common.config.UidWorker;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.json.JSONObject;
import com.feather.common.utils.StringUtils;
import com.feather.wx.config.WxConfig;
import com.feather.wx.entity.WxUser;
import com.feather.wx.service.WxManagerService;
import com.feather.wx.service.WxUserService;
import com.feather.wx.util.MessageUtil;
import com.feather.wx.util.OAuth2RequestParamHelper;
import com.feather.wx.util.OAuthAccessToken;
import com.feather.wx.util.SignatureUtil;
import com.feather.wx.util.WxApi;
import com.feather.wx.util.XmlUtil;
import com.feather.wx.vo.JsWechatConfig;

/**
 * @author feather
 */
@Controller
@RequestMapping("wx")
public class WxController extends BaseController {
    @Autowired
    private WxUserService wxUserService;
    @Autowired
    private UidWorker uidWorker;

    @Autowired
    private WxManagerService wxManagerService;

    // ===============================================================>
    // oauth
    // ===============================================================>
    @RequestMapping("/getJsConfig")
    @ResponseBody
    public AjaxResult getJsConfig(HttpServletRequest request) {
        String url = request.getParameter(WxConfig.URL_PARAM);
        if (StringUtils.isBlank(url)) {
            String error = "缺少参数: url";
            logger.error(error);
            return AjaxResult.error(error);
        }
        JsWechatConfig config = wxManagerService.getJsWechatConfig(url);
        return AjaxResult.success(config);
    }

    @RequestMapping("/getCode")
    public String getCode(HttpServletRequest request) {
        String url = getCallbackUrl(request);
        String state = OAuth2RequestParamHelper.prepareState(request);
        String wxUrl = WxApi.getOAuthCodeUrl(wxManagerService.getWxConfig().getAppid(), url, "snsapi_base", state);
        logger.info("进入微信获取 code, state: {}, 跳转到: {}", url);
        return "redirect:" + wxUrl;
    }

    @RequestMapping("/getOpenId")
    public String getOpenId(HttpServletRequest request) {
        String code = request.getParameter("code");
        if (StringUtils.isBlank(code)) {
            throw new RuntimeException("缺少参数: code");
        }
        String openId = null;
        WxConfig wxConfig = wxManagerService.getWxConfig();
        OAuthAccessToken token = WxApi.getOAuthAccessToken(wxConfig.getAppid(), wxConfig.getAppsecret(), code);
        if (token != null) {
            if (token.getErrcode() != null) {// 获取失败
                logger.error(JSONObject.toJsonString(token));
            } else {
                openId = token.getOpenid();
            }
        }

        String str = openId + "&" + code;
        String key = WxManagerService.REDIS_KEY_OPENID_AND_CODE;
        wxManagerService.getStringRedisTemplate().opsForValue().set(key, str, 5, TimeUnit.MINUTES);

        String url = getCallbackUrl(request);
        String redirect = url + (url.indexOf('?') == -1 ? "?" : "&") + "openId=" + openId + "&code=" + code;
        logger.info("调用微信返回 openId, 跳转到: {}", redirect);
        return "redirect:" + redirect;
    }

    @RequestMapping("/checkOpenId")
    public AjaxResult checkOpenId(HttpServletRequest request) {
        String openId = request.getParameter("openId");
        String code = request.getParameter("code");

        StringRedisTemplate redis = wxManagerService.getStringRedisTemplate();
        String key = WxManagerService.REDIS_KEY_OPENID_AND_CODE;
        String str = redis.opsForValue().get(key);
        redis.delete(key);
        AjaxResult result = (openId + "&" + code).equals(str) ? AjaxResult.success() : AjaxResult.error();
        return result;
    }

    // ===============================================================>
    @RequestMapping(value = "/checkToken", method = RequestMethod.GET)
    @ResponseBody
    public void checkToken() throws Exception {
        this.getRequest().setCharacterEncoding("UTF-8");
        this.getResponse().setCharacterEncoding("UTF-8");
        // 签名
        String signature = this.getRequest().getParameter("signature");
        // 时间戳
        String timestamp = this.getRequest().getParameter("timestamp");
        // 随机数
        String nonce = this.getRequest().getParameter("nonce");
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败

        if (SignatureUtil.checkSignature(signature, timestamp, nonce)) {
            // 随机字符串
            String echostr = this.getRequest().getParameter("echostr");
            this.getResponse().getWriter().write(echostr);
        }
    }

    @RequestMapping(value = "/checkToken", method = RequestMethod.POST)
    @ResponseBody
    public String responseEvent() throws Exception {
        HttpServletRequest req = this.getRequest();
        HttpServletResponse resp = this.getResponse();
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String message = "success";
        try {
            // 把微信返回的xml信息转义成map
            Map<String, String> map = XmlUtil.xmlToMap(req);
            String openid = map.get("FromUserName");// 消息来源用户标识
            String toUserName = map.get("ToUserName");// 消息目的用户标识
            String msgType = map.get("MsgType");// 消息类型(event或者text)
            String eventType = map.get("Event");// 事件类型
            logger.info("--fromUserName--:{},--ToUserName--:{},--MsgType--:{},--eventType--:{}", openid, toUserName,
                    msgType, eventType);
            if (MessageUtil.MSGTYPE_EVENT.equals(msgType)) {// 如果为事件类型
                if (MessageUtil.MESSAGE_SUBSCIBE.equals(eventType)) {
                    // 处理订阅事件
                    if (wxUserService.findByOpenid(openid) == null) {
                        WxUser wxUser = new WxUser();
                        wxUser.setWxid(uidWorker.getNextId());
                        wxUser.setOpenid(openid);
                        wxUser.setSubscrible(true);
                        wxUserService.save(wxUser);
                    } else {
                        wxUserService.updateSubscribleByOpenid(openid, WxUser.SUBSCRIBED);
                    }
                } else if (MessageUtil.MESSAGE_UNSUBSCIBE.equals(eventType)) {
                    // 处理取消订阅事件
                    wxUserService.updateSubscribleByOpenid(openid, WxUser.UNSUBSCRIBE);
                }
            }
        } catch (DocumentException e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("关注微信公众号自动回复的消息内容为:" + message);
        return message;
    }

    /**
     * 从cookie中获取到了openId,有一种情况是openId被删除了 这个时候需要把openId加入到微信用户表
     * 默认给予关注状态，防止删除微信用户后cookie中存在，一直无法报名的问题
     *
     * @param openId
     * @return
     */
    @RequestMapping(value = "/getSubscrible", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getSubscrible(@RequestParam("openId") String openId) {
        Map<String, Object> data = new HashMap<>();
        try {
            if (StringUtils.isNotBlank(openId)) {
                WxUser wxUser = wxUserService.findByOpenid(openId);
                data.put("wxUser", wxUser);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success(data);
    }

    protected String getCallbackUrl(HttpServletRequest request) {
        String url = request.getParameter(WxConfig.URL_PARAM);
        if (StringUtils.isBlank(url)) {
            url = request.getRequestURL().toString();
        }
        logger.info("getCallbackUrl: " + url);
        return url;
    }
}
