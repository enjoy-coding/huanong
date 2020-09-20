package com.feather.wx.service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import javax.annotation.Resource;

import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.model.message.TemplateMessageSendResult;
import org.jeewx.api.wxsendmsg.JwSendTemplateMsgAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.feather.common.json.JSONObject;
import com.feather.common.utils.StringUtils;
import com.feather.wx.config.WxConfig;
import com.feather.wx.util.HttpMethod;
import com.feather.wx.util.SignatureUtil;
import com.feather.wx.util.WxApi;
import com.feather.wx.vo.JsWechatConfig;

@Service
@EnableScheduling
public class WxManagerService {
    private final Logger logger = LoggerFactory.getLogger(WxManagerService.class);

    public static final String REDIS_KEY_ACESSTOKEN = "acessToken";
    public static final String REDIS_KEY_JSAPITICKET = "jsApiTicket";
    public static final String REDIS_KEY_OPENID_AND_CODE = "openid&code@wx";

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisLockRegistry redisLockRegistry;

    @Autowired
    private WxConfig wxConfig;

    public WxConfig getWxConfig() {
        return wxConfig;
    }

    public StringRedisTemplate getStringRedisTemplate() {
        return stringRedisTemplate;
    }

    /**
     * 获取全局 AccessToken
     */
    public String getAccessToken() {
        String accessToken = stringRedisTemplate.opsForValue().get(REDIS_KEY_ACESSTOKEN);
        if (StringUtils.isNotBlank(accessToken)) {
            logger.info("从缓存获取accessToken:{}", accessToken);
            return accessToken;
        }
        if (StringUtils.isBlank(wxConfig.getAppid())) {
            logger.error("empty (wx config) appid");
            return null;
        }
        if (StringUtils.isBlank(wxConfig.getAppsecret())) {
            logger.error("empty (wx config) appsecret");
            return null;
        }
        return createAccessToken();
    }

    private String createAccessToken() {
        Lock lock = redisLockRegistry.obtain("AccessToken@wx");
        lock.lock();

        String accessToken = stringRedisTemplate.opsForValue().get(REDIS_KEY_ACESSTOKEN);
        if (StringUtils.isNotBlank(accessToken)) {
            lock.unlock();
            return accessToken;
        }

        String tockenUrl = WxApi.getTokenUrl(wxConfig.getAppid(), wxConfig.getAppsecret());
        for (int i = 0; i < 3; i++) {
            try {
                JSONObject jsonObject = WxApi.httpsRequest(tockenUrl, HttpMethod.GET, null);

                if (StringUtils.isBlank((CharSequence) jsonObject.get("errmsg"))) {
                    accessToken = jsonObject.getString("access_token");
                    // 向redis里存入数据和设置缓存时间
                    stringRedisTemplate.opsForValue().set(REDIS_KEY_ACESSTOKEN, accessToken, 115, TimeUnit.MINUTES);
                    logger.info("刷新缓存accessToken:{}", accessToken);
                    break;
                } else {
                    logger.error(jsonObject.toString());
                }
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
        lock.unlock();
        return accessToken;
    }

    @Scheduled(fixedRate = 115 * 60 * 1000)
    public void refreshAccessToken() {
        logger.info("刷新token");
        getAccessToken();
    }

    /**
     * 获取js端配置文件信息
     */
    public JsWechatConfig getJsWechatConfig(String url) {
        String jsapi_ticket = getJsApiTicket(wxConfig.getAppid(), wxConfig.getAppsecret());
        String noncestr = UUID.randomUUID().toString();
        long timestamp = System.currentTimeMillis() / 1000;

        JsWechatConfig jsWechatConfig = new JsWechatConfig();
        jsWechatConfig.setAppId(wxConfig.getAppid());
        jsWechatConfig.setTimestamp(timestamp);
        jsWechatConfig.setNoncestr(noncestr);
        String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url="
                + url;
        String signature = SignatureUtil.createSha1Sign(str);
        jsWechatConfig.setSignature(signature);
        return jsWechatConfig;
    }

    /**
     * 根据token获取jsApiTicket
     *
     * @param token
     */
    public String getJsApiTicket(String token) {
        String jsApiTicket = stringRedisTemplate.opsForValue().get(REDIS_KEY_JSAPITICKET);
        if (StringUtils.isNotBlank(jsApiTicket)) {
            logger.info("从缓存获取jsApiTicket:{}", jsApiTicket);
            return jsApiTicket;
        }
        if (StringUtils.isNotBlank(token)) {
            String jsApiTicketUrl = WxApi.getJsApiTicketUrl(token);
            JSONObject jsonObject = WxApi.httpsRequest(jsApiTicketUrl, HttpMethod.GET, null);
            if (ObjectUtils.isEmpty(jsonObject) && 0 == jsonObject.getInt("errcode")) {
                jsApiTicket = jsonObject.getString("ticket");
                stringRedisTemplate.opsForValue().set(REDIS_KEY_ACESSTOKEN, jsApiTicket, 115, TimeUnit.MINUTES);
                return jsApiTicket;
            } else {
                logger.error(jsonObject.toString());
            }
        }
        return null;
    }

    /**
     * 获取网页端的jsApiTicket
     *
     * @param appId
     * @param appSecret
     */
    public String getJsApiTicket(String appId, String appSecret) {
        String jsApiTicket = stringRedisTemplate.opsForValue().get(REDIS_KEY_JSAPITICKET);
        if (StringUtils.isNotBlank(jsApiTicket)) {
            logger.info("从缓存获取jsApiTicket:{}", jsApiTicket);
            return jsApiTicket;
        }
        String token = getAccessToken();
        if (StringUtils.isNotBlank(token)) {
            String jsApiTicketUrl = WxApi.getJsApiTicketUrl(token);
            JSONObject jsonObject = WxApi.httpsRequest(jsApiTicketUrl, HttpMethod.GET, null);
            if (!ObjectUtils.isEmpty(jsonObject) && 0 == jsonObject.getInt("errcode")) {
                jsApiTicket = jsonObject.getString("ticket");
                stringRedisTemplate.opsForValue().set(REDIS_KEY_JSAPITICKET, jsApiTicket, 115, TimeUnit.MINUTES);
                return jsApiTicket;
            } else {
                logger.error(jsonObject.toString());
            }
        }
        return null;
    }

    /**
     * 发送模板消息
     * 
     * @param msgSend
     * @return
     * @throws WexinReqException
     */
    public String sendTemplateMsg(TemplateMessageSendResult msgSend) throws WexinReqException {
        String msg = null;
        for (int i = 0; i < 2; i++) {
            String accessToken = getAccessToken();
            msg = JwSendTemplateMsgAPI.sendTemplateMsg(accessToken, msgSend);
            if (msg != null && msg.indexOf("invalid credential, access_token is invalid or not latest rid:") > 0) {
                stringRedisTemplate.delete(REDIS_KEY_ACESSTOKEN);
                continue;
            }
            break;
        }
        return msg;
    }
}
