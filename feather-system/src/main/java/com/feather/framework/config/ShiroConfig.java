package com.feather.framework.config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.mgt.SubjectDAO;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.feather.common.config.CustomLogin;
import com.feather.common.utils.spring.SpringUtils;
import com.feather.framework.config.properties.ShiroUserProperties;
import com.feather.framework.shiro.realm.FreeRealm;
import com.feather.framework.shiro.realm.UserModularRealmAuthenticator;
import com.feather.framework.shiro.realm.UserRealm;
import com.feather.framework.shiro.session.OnlineSessionDAO;
import com.feather.framework.shiro.session.OnlineSessionFactory;
import com.feather.framework.shiro.web.filter.LogoutFilter;
import com.feather.framework.shiro.web.filter.kickout.KickoutSessionFilter;
import com.feather.framework.shiro.web.filter.online.OnlineSessionFilter;
import com.feather.framework.shiro.web.filter.sync.SyncOnlineSessionFilter;
import com.feather.framework.shiro.web.session.OnlineWebSessionManager;
import com.feather.framework.shiro.web.session.SpringSessionValidationScheduler;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

/**
 * 权限配置加载
 *
 * @author feather
 */
@Configuration
public class ShiroConfig {
    public static final String PREMISSION_STRING = "perms[\"{0}\"]";

    // Session超时时间，单位为毫秒（默认30分钟）
    @Value("${shiro.session.expireTime}")
    private int expireTime;

    // 同一个用户最大会话数
    @Value("${shiro.session.maxSession}")
    private int maxSession;

    // 踢出之前登录的/之后登录的用户，默认踢出之前登录的用户
    @Value("${shiro.session.kickoutAfter}")
    private boolean kickoutAfter;

    // 设置Cookie的域名
    @Value("${shiro.cookie.domain}")
    private String domain;

    // 设置cookie的有效访问路径
    @Value("${shiro.cookie.path}")
    private String path;

    // 设置HttpOnly属性
    @Value("${shiro.cookie.httpOnly}")
    private boolean httpOnly;

    // 设置Cookie的过期时间，秒为单位
    @Value("${shiro.cookie.maxAge}")
    private int maxAge;

    @Autowired(required = false)
    private CustomLogin customLogin;

    /**
     * 自定义Realm
     */
    @Bean
    public UserRealm userRealm(org.crazycake.shiro.RedisCacheManager cacheManager) {
        UserRealm userRealm = new UserRealm();
        userRealm.setCacheManager(cacheManager);
        return userRealm;
    }

    /**
     * 自定义sessionDAO会话
     */
    @Bean
    public OnlineSessionDAO sessionDAO(org.crazycake.shiro.RedisManager redisManager) {
        OnlineSessionDAO sessionDAO = new OnlineSessionDAO();
        sessionDAO.setRedisManager(redisManager);
        sessionDAO.setSessionIdGenerator(new JavaUuidSessionIdGenerator());
        return sessionDAO;
    }

    /**
     * 自定义sessionFactory会话
     */
    @Bean
    public SessionFactory sessionFactory() {
        OnlineSessionFactory sessionFactory = new OnlineSessionFactory();
        return sessionFactory;
    }

    /**
     * 会话管理器
     */
    @Bean
    public OnlineWebSessionManager sessionManager(org.crazycake.shiro.RedisCacheManager cacheManager,
            SessionDAO sessionDAO) {
        OnlineWebSessionManager manager = new OnlineWebSessionManager();
        // 加入缓存管理器
        manager.setCacheManager(cacheManager);
        // 删除过期的session
        manager.setDeleteInvalidSessions(true);
        // manager.setSessionIdCookie(rememberMeCookie()); // 设置JSESSIONID
        // 设置全局session超时时间
        manager.setGlobalSessionTimeout(expireTime > 0 ? expireTime * 60 * 1000 : -1);
        // 去掉 JSESSIONID
        manager.setSessionIdUrlRewritingEnabled(false);
        // 定义要使用的无效的Session定时调度器
        manager.setSessionValidationScheduler(SpringUtils.getBean(SpringSessionValidationScheduler.class));
        // 是否定时检查session
        manager.setSessionValidationSchedulerEnabled(true);
        // 自定义SessionDao
        manager.setSessionDAO(sessionDAO);
        // 自定义sessionFactory
        manager.setSessionFactory(sessionFactory());
        manager.setCustomLogin(customLogin);
        return manager;
    }

    @Bean
    public SessionStorageEvaluator sessionStorageEvaluator() {
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }

    @Bean
    public SubjectDAO subjectDAO() {
        DefaultSubjectDAO defaultSubjectDAO = new DefaultSubjectDAO();
        defaultSubjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator());
        return defaultSubjectDAO;
    }

    /**
     * 安全管理器
     */
    @Bean
    public SecurityManager securityManager(UserRealm userRealm, SubjectDAO subjectDAO,
            SpringSessionValidationScheduler springSessionValidationScheduler,
            org.crazycake.shiro.RedisCacheManager cacheManager, SessionDAO sessionDAO) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.

        securityManager.setAuthenticator(modularRealmAuthenticator());
        List<Realm> realms = new ArrayList<>();
        // 密码登录realm
        realms.add(userRealm);
        // 免密登录realm
        realms.add(freeRealm());
        securityManager.setRealms(realms);

        securityManager.setSubjectDAO(subjectDAO);
        // 记住我
        securityManager.setRememberMeManager(rememberMeManager());
        // 注入缓存管理器
        securityManager.setCacheManager(cacheManager);
        // session管理器
        securityManager.setSessionManager(sessionManager(cacheManager, sessionDAO));
        return securityManager;
    }

    /**
     * 身份认证realm;
     * 
     * @return
     */
    @Bean
    public FreeRealm freeRealm() {
        FreeRealm realm = new FreeRealm();
        // 不需要加密，直接用数据库密码进行登录
        return realm;
    }

    /*
     * @Bean public HashedCredentialsMatcher hashedCredentialsMatcher(){
     * HashedCredentialsMatcher hashedCredentialsMatcher = new
     * HashedCredentialsMatcher();
     * hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
     * hashedCredentialsMatcher.setHashIterations(1);//散列的次数，md5("") return
     * hashedCredentialsMatcher; }
     */
    /**
     * 系统自带的Realm管理，主要针对多realm
     */
    @Bean
    public ModularRealmAuthenticator modularRealmAuthenticator() {
        // 自己重写的ModularRealmAuthenticator
        UserModularRealmAuthenticator modularRealmAuthenticator = new UserModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }

    /**
     * Shiro过滤器配置
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,
            org.crazycake.shiro.RedisCacheManager cacheManager, SessionManager sessionManager,
            ShiroUserProperties shiroUserProperties) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // Shiro的核心安全接口,这个属性是必须的
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 身份认证失败，则跳转到登录页面的配置
        shiroFilterFactoryBean.setLoginUrl(shiroUserProperties.getLoginUrl());
        // 权限认证失败，则跳转到指定页面
        shiroFilterFactoryBean.setUnauthorizedUrl(shiroUserProperties.getUnauthorizedUrl());
        // Shiro连接约束配置，即过滤链的定义
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 设置匿名访问
        if (shiroUserProperties.getAnon() != null) {
            for (String cfg : shiroUserProperties.getAnon()) {
                if (cfg.length() > 0) {
                    filterChainDefinitionMap.put(cfg, "anon");
                }
            }
        }
        // 退出 logout地址，shiro去清除session
        filterChainDefinitionMap.put("/logout", "logout");
        // 登录
        filterChainDefinitionMap.put("/login", "anon");
        // 系统权限列表
        // filterChainDefinitionMap.putAll(SpringUtils.getBean(IMenuService.class).selectPermsAll());

        Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
        filters.put("user", new UserFilter());
        filters.put("onlineSession", onlineSessionFilter(shiroUserProperties));
        filters.put("syncOnlineSession", syncOnlineSessionFilter());
        if (maxSession > 0) {
            filters.put("kickout", kickoutSessionFilter(cacheManager, sessionManager));
        }
        // 注销成功，则跳转到指定页面
        filters.put("logout", logoutFilter(cacheManager, shiroUserProperties));
        shiroFilterFactoryBean.setFilters(filters);

        // 所有请求需要认证
        if (maxSession > 0) {
            filterChainDefinitionMap.put("/**", "user,kickout,onlineSession,syncOnlineSession");
        } else {
            filterChainDefinitionMap.put("/**", "user,onlineSession,syncOnlineSession");
        }
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 自定义在线用户处理过滤器
     */
    @Bean
    public OnlineSessionFilter onlineSessionFilter(ShiroUserProperties shiroUserProperties) {
        OnlineSessionFilter onlineSessionFilter = new OnlineSessionFilter();
        onlineSessionFilter.setLoginUrl(shiroUserProperties.getLoginUrl());
        return onlineSessionFilter;
    }

    /**
     * 自定义在线用户同步过滤器
     */
    @Bean
    public SyncOnlineSessionFilter syncOnlineSessionFilter() {
        SyncOnlineSessionFilter syncOnlineSessionFilter = new SyncOnlineSessionFilter();
        return syncOnlineSessionFilter;
    }

    /**
     * 退出过滤器
     */
    public LogoutFilter logoutFilter(org.crazycake.shiro.RedisCacheManager cacheManager,
            ShiroUserProperties shiroUserProperties) {
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setCacheManager(cacheManager);
        logoutFilter.setLoginUrl(shiroUserProperties.getLoginUrl());
        return logoutFilter;
    }

    /**
     * thymeleaf模板引擎和shiro框架的整合
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    /**
     * 开启Shiro注解通知器
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            @Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * cookie 属性设置
     */
    public SimpleCookie rememberMeCookie() {
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setHttpOnly(httpOnly);
        cookie.setMaxAge(maxAge * 24 * 60 * 60);
        return cookie;
    }

    /**
     * 记住我
     */
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey(Base64.decode("fCq+/xW488hMTCD+cmJ3aQ=="));
        return cookieRememberMeManager;
    }

    /**
     * 同一个用户多设备登录限制
     */
    public KickoutSessionFilter kickoutSessionFilter(org.crazycake.shiro.RedisCacheManager cacheManager,
            SessionManager sessionManager) {
        KickoutSessionFilter kickoutSessionFilter = new KickoutSessionFilter();
        kickoutSessionFilter.setCacheManager(cacheManager);
        kickoutSessionFilter.setSessionManager(sessionManager);
        // 同一个用户最大的会话数，默认-1无限制；比如2的意思是同一个用户允许最多同时两个人登录
        kickoutSessionFilter.setMaxSession(maxSession);
        // 是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；踢出顺序
        kickoutSessionFilter.setKickoutAfter(kickoutAfter);
        // 被踢出后重定向到的地址；
        kickoutSessionFilter.setKickoutUrl("/login?kickout=1");
        return kickoutSessionFilter;
    }
}
