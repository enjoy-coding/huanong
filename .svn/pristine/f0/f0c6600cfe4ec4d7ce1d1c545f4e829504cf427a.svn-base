package com.feather.framework.shiro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.feather.common.constant.Constants;
import com.feather.common.constant.UserConstants;
import com.feather.common.exception.user.UserBlockedException;
import com.feather.common.exception.user.UserNotExistsException;
import com.feather.common.exception.user.UserPasswordNotMatchException;
import com.feather.common.utils.DateUtils;
import com.feather.common.utils.MessageUtils;
import com.feather.framework.manager.AsyncManager;
import com.feather.framework.manager.factory.AsyncFactory;
import com.feather.framework.util.ShiroUtils;
import com.feather.system.domain.SysUser;
import com.feather.system.service.ISysUserService;

/**
 * 登录校验方法
 * 
 * @author feather
 */
@Component
public class SysLoginService {
    @Autowired
    private SysPasswordService passwordService;

    @Autowired
    private ISysUserService userService;

    /**
     * 登录
     */
    public SysUser login(String username, String password, boolean matchPassword) {
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || (matchPassword && StringUtils.isEmpty(password))) {
            AsyncManager.me().execute(
                    AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
            throw new UserNotExistsException();
        }
        // 密码如果不在指定范围内 错误
        if (matchPassword) {
            if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                    || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
                        MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
        }

        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
                    MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }

        // 查询用户信息
        SysUser user = null;
        List<SysUser> ul = userService.selectUserByLoginName(username);
        if (ul != null && ul.size() == 1) {
            user = ul.get(0);
        }

        if (user == null && maybeMobilePhoneNumber(username)) {
            List<SysUser> u = userService.selectUserByPhoneNumber(username);
            if (u != null && u.size() == 1) {
                user = u.get(0);
            }
        }

        if (user == null && maybeIdCard(username)) {
            List<SysUser> u = userService.selectUserByIdCard(username);
            if (u != null && u.size() == 1) {
                user = u.get(0);
            }
        }

        if (user == null && maybeEmail(username)) {
            List<SysUser> u = userService.selectUserByEmail(username);
            if (u != null && u.size() == 1) {
                user = u.get(0);
            }
        }

        if (user == null) {
            List<SysUser> u = userService.selectUserByOpenid(username);
            if (u != null && u.size() == 1) {
                user = u.get(0);
            }
        }

        if (user == null) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
                    MessageUtils.message("user.not.exists")));
            throw new UserNotExistsException();
        }

        if (Constants.FAIL_OR_DISABLED.equals(user.getStatus())) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
                    MessageUtils.message("user.blocked", user.getRemark())));
            throw new UserBlockedException();
        }

        passwordService.validate(user, password, matchPassword);

        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS,
                MessageUtils.message("user.login.success")));
        recordLoginInfo(user);
        return user;
    }

    private boolean maybeEmail(String username) {
        if (!username.matches(UserConstants.EMAIL_PATTERN)) {
            return false;
        }
        return true;
    }

    private boolean maybeMobilePhoneNumber(String username) {
        if (!username.matches(UserConstants.MOBILE_PHONE_NUMBER_PATTERN)) {
            return false;
        }
        return true;
    }

    private boolean maybeIdCard(String username) {
        if (!username.matches(UserConstants.ID_CARD_PATTERN)) {
            return false;
        }
        return true;
    }

    /**
     * 记录登录信息
     */
    public void recordLoginInfo(SysUser user) {
        user.setLoginIp(ShiroUtils.getIp());
        user.setLoginDate(DateUtils.getNowDate());
        userService.updateUserInfo(user);
    }
}
