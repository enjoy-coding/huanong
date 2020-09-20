package com.feather.wx.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 微信配置
 * 
 * @author feather
 */
@Configuration
@ConfigurationProperties(prefix = "wx.config")
public class WxConfig {
    public static final String URL_PARAM = "url";

    private String appid;
    private String appsecret;
    private String domain;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
