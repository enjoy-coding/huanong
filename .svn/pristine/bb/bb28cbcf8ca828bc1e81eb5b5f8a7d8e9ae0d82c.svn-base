package com.feather.system.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feather.common.annotation.Log;
import com.feather.common.config.CustomLogin;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.ServletUtils;
import com.feather.common.utils.StringUtils;
import com.feather.framework.util.ShiroUtils;
import com.feather.system.domain.SysUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 登录验证
 * 
 * @author feather
 */
@Api(tags = "登录验证")
@Controller
public class SysLoginController extends BaseController {

    @Autowired(required = false)
    private CustomLogin customLogin;

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 如果是Ajax请求，返回Json字符串。
        if (ServletUtils.isAjaxRequest(request)) {
            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }
        if (customLogin != null) {
            return customLogin.getLoginTemplate(request, response);
        }
        return "login";
    }

    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        if (customLogin != null) {
            customLogin.getCaptcha(request, response);
        } else {
            AjaxResult.error("Not support captcha.");
        }
    }

    @PostMapping("/login")
    @ResponseBody
    public AjaxResult ajaxLogin(HttpServletRequest request, HttpServletResponse response, String username,
            String password, Boolean rememberMe) {
        if (customLogin != null) {
            AjaxResult result = customLogin.preLogin(request, response);
            if (result != null) {
                return result;
            }
        }
        String msg = doLogin(request, username, password, rememberMe);
        if (StringUtils.isEmpty(msg)) {
            AjaxResult result = AjaxResult.success();
            if (customLogin != null) {
                customLogin.afterLogin(request, result);
            }
            return result;
        }
        return AjaxResult.error(msg);
    }

    @Log(title = "登录", businessType = BusinessType.API)
    @ApiOperation(value = "登录")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "username", value = "账号", required = true), //
            @ApiImplicitParam(name = "password", value = "密码", required = true) //
    })
    @PostMapping("/api/login")
    @ResponseBody
    public AjaxResult restLogin(HttpServletRequest request, HttpServletResponse response, String username,
            String password) {
        if (customLogin != null) {
            AjaxResult result = customLogin.preLogin(request, response);
            if (result != null) {
                return result;
            }
        }
        String msg = doLogin(request, username, password, false);
        if (StringUtils.isEmpty(msg)) {
            SysUser sysUser = ShiroUtils.getSysUser();
            sysUser.setPassword(null);
            AjaxResult result = AjaxResult.success(sysUser);
            if (customLogin != null) {
                customLogin.afterLogin(request, result);
            }
            return result;
        }
        return AjaxResult.error(msg);
    }

    @GetMapping("/unauth")
    public String unauth() {
        return "error/unauth";
    }

    private String doLogin(HttpServletRequest request, String username, String password, Boolean rememberMe) {
        if (rememberMe == null) {
            rememberMe = false;
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return null;
        } catch (AuthenticationException e) {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage())) {
                msg = e.getMessage();
            }
            return msg;
        }
    }
}
