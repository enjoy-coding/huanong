package com.feather.framework.shiro.service;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.feather.common.constant.Constants;
import com.feather.common.constant.ShiroConstants;
import com.feather.common.exception.user.UserPasswordNotMatchException;
import com.feather.common.exception.user.UserPasswordRetryLimitExceedException;
import com.feather.common.utils.MessageUtils;
import com.feather.framework.manager.AsyncManager;
import com.feather.framework.manager.factory.AsyncFactory;
import com.feather.system.domain.SysUser;

/**
 * 登录密码方法
 * 
 * @author feather
 */
@Component
public class SysPasswordService {
    @Autowired
    private CacheManager cacheManager;

    private Cache<String, AtomicInteger> loginRecordCache;

    @Value("${feather.user.password.maxRetryCount}")
    private int maxRetryCount;

    @PostConstruct
    public void init() {
        // 密码错误{maxRetryCount}次锁定, 配置大于0 时有效
        if (maxRetryCount > 0) {
            loginRecordCache = cacheManager.getCache(ShiroConstants.LOGINRECORDCACHE);
        }
    }

    public void validate(SysUser user, String password, boolean matchPassword) {
        String loginName = user.getLoginName();

        AtomicInteger retryCount = null;
        if (loginRecordCache != null) {
            retryCount = loginRecordCache.get(loginName);
            if (retryCount == null) {
                retryCount = new AtomicInteger(0);
                loginRecordCache.put(loginName, retryCount);
            }
            if (retryCount.incrementAndGet() > maxRetryCount) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL,
                        MessageUtils.message("user.password.retry.limit.exceed", maxRetryCount)));
                throw new UserPasswordRetryLimitExceedException(maxRetryCount);
            }
        }

        if (matchPassword && !matches(user, password)) {
            if (loginRecordCache != null) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL,
                        MessageUtils.message("user.password.retry.limit.count", retryCount)));
                loginRecordCache.put(loginName, retryCount);
            }
            throw new UserPasswordNotMatchException();
        } else {
            clearLoginRecordCache(loginName);
        }
    }

    public boolean matches(SysUser user, String newPassword) {
        return user.getPassword().equals(encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
    }

    public void clearLoginRecordCache(String username) {
        if (loginRecordCache != null) {
            loginRecordCache.remove(username);
        }
    }

    public String encryptPassword(String username, String password, String salt) {
        return new Md5Hash(username + password + salt).toHex().toString();
    }

}
