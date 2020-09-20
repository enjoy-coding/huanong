package com.feather.aupipes.sso.supwis;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feather.common.core.domain.AjaxResult;
import com.feather.framework.shiro.realm.CustomeToken;

@Controller
@RequestMapping("/sso")
public class SsoLoginController {

    @RequestMapping("/doLogin")
    public boolean doLogin(LoginUser loginUser, HttpServletRequest request) {
        String loginName = loginUser.getAccount();
        // 根据登录账户查询用户信息
        // Map map = aupSysUserService.getUserByloginname(loginName);
        // String password = "123456";
        CustomeToken token = new CustomeToken(loginName);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 处理 认证中心回调
     */
    @RequestMapping("/dLogin")
    @ResponseBody
    // @RequestParam("targetUrl") String targetUrl, @RequestParam("ticket")
    // String ticket
    public AjaxResult ajaxLogin1(HttpServletResponse response, HttpServletRequest request)
            throws UnsupportedEncodingException {
        login(request, response);
        // String username = request.getParameter("");
        return null;
    }

    @RequestMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String targetUrl = CASUtil.getTargetUrl(request);

        try {
            if (CASUtil.isLogin(session)) {
                response.sendRedirect(targetUrl);
            } else {
                if (CASUtil.hasTicket(request)) {
                    LoginUser loginUser = CASUtil.getLoginUser(request);
                    if (loginUser.isLogin()) {
                        doLogin(loginUser, request);
                        CASUtil.login(loginUser, session);
                        response.sendRedirect(targetUrl);
                    } else {
                        response.sendRedirect(CASUtil.getLogoutUrl(request));
                    }
                } else {
                    String loginUrl = CASUtil.getLoginUrl(request);
                    response.sendRedirect(loginUrl);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
