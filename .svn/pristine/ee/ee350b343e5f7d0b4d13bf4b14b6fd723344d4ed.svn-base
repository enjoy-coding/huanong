package com.feather.patrol.config;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.feather.common.config.CustomLogin;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.utils.StringUtils;
import com.feather.patrol.service.IPtrLogService;

@Configuration
public class PatrolCustomLogin implements CustomLogin {

    public static final String HEADER_TOKEN = "_token";

    @Autowired
    private IPtrLogService ptrLogService;

    @Override
    public String getLoginTemplate(HttpServletRequest request, HttpServletResponse response) {
        List<String> urls = ptrLogService.selectTopFacadeUrls();
        request.setAttribute("topLogFacade", urls);
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
