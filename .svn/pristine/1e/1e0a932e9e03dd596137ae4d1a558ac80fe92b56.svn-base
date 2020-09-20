package com.feather.lpscommunity.config;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.util.WebUtils;
import org.springframework.context.annotation.Configuration;

import com.feather.common.config.CustomLogin;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.utils.StringUtils;

@Configuration
public class LpsCustomLogin implements CustomLogin {

    public static final String HEADER_TOKEN = "token";

    @Override
    public String getLoginTemplate(HttpServletRequest request, HttpServletResponse response) {
        return "login";
    }

    @Override
    public AjaxResult preLogin(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public void afterLogin(HttpServletRequest request, AjaxResult result) {
        if (result.get(AjaxResult.DATA_TAG) != null) {
            result.put(HEADER_TOKEN, request.getSession().getId());
        }
    }

    @Override
    public boolean existsSessionId(ServletRequest request) {
        return StringUtils.isNotEmpty(WebUtils.toHttp(request).getHeader(HEADER_TOKEN));
    }

    @Override
    public String getSessionId(ServletRequest request, ServletResponse response) {
        return WebUtils.toHttp(request).getHeader(HEADER_TOKEN);
    }

    @Override
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) {

    }
}
