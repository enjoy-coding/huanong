package com.feather.framework.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.feather.common.utils.bean.BeanUtils;
import com.feather.framework.shiro.realm.UserRealm;
import com.feather.system.domain.SysUser;

/**
 * shiro 工具类
 * 
 * @author feather
 */
public class ShiroUtils {
    public static Subject getSubject() {
        try {
            return SecurityUtils.getSubject();
        } catch (Exception ex) {
            return null;
        }
    }

    public static Session getSession() {
        try {
            return SecurityUtils.getSubject().getSession();
        } catch (Exception ex) {
            return null;
        }
    }

    public static void logout() {
        Subject subject = getSubject();
        if (subject != null) {
            subject.logout();
        }
    }

    public static SysUser getSysUser() {
        SysUser user = null;
        Subject subject = getSubject();
        if (subject != null) {
            try {
                Object obj = subject.getPrincipal();
                if (obj != null) {
                    user = new SysUser();
                    BeanUtils.copyBeanProp(user, obj);
                }
            } catch (Exception ex) {
            }
        }
        return user;
    }

    public static void setSysUser(SysUser user) {
        Subject subject = getSubject();
        if (subject != null) {
            try {
                PrincipalCollection principalCollection = subject.getPrincipals();
                String realmName = principalCollection.getRealmNames().iterator().next();
                PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
                // 重新加载Principal
                subject.runAs(newPrincipalCollection);
            } catch (Exception ex) {
            }
        }
    }

    public static void clearCachedAuthorizationInfo() {
        try {
            RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
            UserRealm realm = (UserRealm) rsm.getRealms().iterator().next();
            realm.clearCachedAuthorizationInfo();
        } catch (Exception ex) {
        }
    }

    public static Long getUserId() {
        SysUser user = getSysUser();
        return user != null ? user.getUserId() : null;
    }

    public static String getLoginName() {
        SysUser user = getSysUser();
        return user != null ? user.getLoginName() : null;
    }

    public static String getIp() {
        Subject subject = getSubject();
        if (subject != null) {
            try {
                return subject.getSession().getHost();
            } catch (Exception ex) {
            }
        }
        return null;
    }

    public static String getSessionId() {
        Subject subject = getSubject();
        if (subject != null) {
            try {
                return String.valueOf(getSubject().getSession().getId());
            } catch (Exception ex) {
            }
        }
        return null;
    }

    /**
     * 生成随机盐
     */
    public static String randomSalt() {
        // 一个Byte占两个字节，此处生成的3字节，字符串长度为6
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes(3).toHex();
        return hex;
    }
}
