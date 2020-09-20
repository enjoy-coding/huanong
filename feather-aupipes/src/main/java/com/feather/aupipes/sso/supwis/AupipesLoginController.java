package com.feather.aupipes.sso.supwis;

import java.util.List;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feather.common.config.CustomLogin;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.framework.shiro.realm.CustomeToken;
import com.feather.framework.util.ShiroUtils;
import com.feather.system.domain.SysUser;
import com.feather.system.service.ISysRoleService;
import com.feather.system.service.ISysUserService;

@Controller
@RequestMapping("/sso")
public class AupipesLoginController extends BaseController implements CustomLogin {
    public static final String OPENID_SESSION_KEY = "current-openid";

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysRoleService roleService;

    /**
     * 移动端登录 原逻辑：根据openid获取用户信息，如果属于mobile角色则自动登录
     * 根据要求调整：只有属于mobile角色的用户才能（自动）登录移动端，其他人无权限
     */
    @RequestMapping("/mobile/loginWithOpenId")
    @ResponseBody
    public AjaxResult loginWithOpenId(HttpServletRequest request, HttpServletResponse response) {
        String openid = request.getParameter("openid"); // 获取微信端openid
        if (openid == null) {
            return AjaxResult.error();
        } else {
            SysUser user = new SysUser();
            user.setOpenid(openid);
            List<SysUser> list = sysUserService.selectUserList(user);
            if (list.size() > 0) {
                Long userId = list.get(0).getUserId();
                Set<String> roles = roleService.selectRoleKeys(userId);
                // 判断是否含有权限字符
                if (roles.contains("mobile")) {
                    String loginname = list.get(0).getLoginName();
                    CustomeToken token = new CustomeToken(loginname);
                    Subject subject = SecurityUtils.getSubject();
                    try {
                        subject.login(token);
                    } catch (Exception e) {
                        return AjaxResult.error("用户名或者密码不正确");
                    }
                }
                return AjaxResult.error("您没有此权限");
            } else {
                request.getSession().setAttribute(OPENID_SESSION_KEY, openid);
                return AjaxResult.error();
            }
        }
    }

    @Override
    public String getLoginTemplate(HttpServletRequest request, HttpServletResponse response) {
        if (getMobileDevice(request) == null) {
            return "login";
        } else {
            if (ShiroUtils.getSysUser() != null) {
                return "aupipes/mobile/home";
            }
            String openid = request.getParameter("openid");
            request.setAttribute("openid", openid);
            // 访问移动端登录页
            return "loginMobile";
        }
    }

    @Override
    public AjaxResult preLogin(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public void afterLogin(HttpServletRequest request, AjaxResult result) {
        try {
            String openid = (String) request.getSession().getAttribute(OPENID_SESSION_KEY);
            if (StringUtils.isNotEmpty(openid)) {
                Long userId = ShiroUtils.getUserId();
                SysUser user = new SysUser();
                user.setUserId(userId);
                user.setOpenid(openid);
                sysUserService.updateUser(user);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    public boolean existsSessionId(ServletRequest request) {
        return false;
    }

    @Override
    public String getSessionId(ServletRequest request, ServletResponse response) {
        return null;
    }

    @Override
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) {

    }

    // android: 所有android设备
    // mac os: iphone ipad
    // windows phone: Nokia等windows系统的手机
    public static String getMobileDevice(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent");
        String[] deviceArray = new String[] { "android", "micromessenger", "windows phone" };
        if (userAgent != null) {
            userAgent = userAgent.toLowerCase();
            for (int i = 0; i < deviceArray.length; i++) {
                if (userAgent.indexOf(deviceArray[i]) > 0) {
                    return deviceArray[i];
                }
            }
        }
        return null;
    }
}
