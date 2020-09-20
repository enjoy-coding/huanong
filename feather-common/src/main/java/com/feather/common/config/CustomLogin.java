package com.feather.common.config;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.feather.common.core.domain.AjaxResult;

/**
 * @author feather
 */
public interface CustomLogin {
    String getLoginTemplate(HttpServletRequest request, HttpServletResponse response);

    AjaxResult preLogin(HttpServletRequest request, HttpServletResponse response);

    void afterLogin(HttpServletRequest request, AjaxResult result);

    boolean existsSessionId(ServletRequest request);

    String getSessionId(ServletRequest request, ServletResponse response);

    void getCaptcha(HttpServletRequest request, HttpServletResponse response);
}
