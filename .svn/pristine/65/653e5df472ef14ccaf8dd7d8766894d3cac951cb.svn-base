package com.feather.aupipes.sso.supwis;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginOut {
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;
    @Autowired
    HttpSession session;
    public boolean doLogout(HttpServletRequest request) {
        return true;
    }
    {
        if (doLogout(request)) {
            CASUtil.logout(session);
            try {
                response.sendRedirect(CASUtil.getLogoutUrl(request));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                response.sendRedirect(CASUtil.getLoginUrl(request));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
