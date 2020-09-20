package com.feather.framework.config.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * shiro 配置属性
 * 
 * @author feather
 */
@Configuration
@ConfigurationProperties(prefix = "shiro.user")
public class ShiroUserProperties {
    // 登录地址
    private String loginUrl;
    // 权限认证失败地址
    private String unauthorizedUrl;
    // 首页地址
    private String indexUrl;
    // 匿名访问地址
    private List<String> anon;

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getUnauthorizedUrl() {
        return unauthorizedUrl;
    }

    public void setUnauthorizedUrl(String unauthorizedUrl) {
        this.unauthorizedUrl = unauthorizedUrl;
    }

    public String getIndexUrl() {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }

    public List<String> getAnon() {
        return anon;
    }

    public void setAnon(List<String> anon) {
        this.anon = anon;
    }
}
