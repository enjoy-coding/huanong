package com.feather.aupipes.sso.supwis;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.BooleanUtils;

import com.feather.common.utils.StringUtils;

public class CASUtil {

    /** 判断是否已经登录过 */
    public static boolean isLogin(HttpSession session) {
        Object isLogin = session.getAttribute(Constants.LOGIN_KEY);
        return BooleanUtils.toBoolean(String.valueOf(isLogin));
    }

    public static String getBasePath(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();

        // 判断是否配置了显式端口
        boolean explicit_port = Constants.CLIENT_SYSTEM_EXPLICIT_PORT != null
                && !Constants.CLIENT_SYSTEM_EXPLICIT_PORT.isEmpty();

        if (explicit_port) {
            try {
                serverPort = Integer.parseInt(Constants.CLIENT_SYSTEM_EXPLICIT_PORT);
            } catch (Exception e) {

                // 异常时赋值，方便双方排查问题
                serverPort = 19000;
            }

            String url = scheme + "://" + serverName + ":" + serverPort + contextPath + "/";
            return url;

        } else {
            if ((serverPort == 80) || (serverPort == 443)) {

                String url = scheme + "://" + serverName + contextPath + "/";
                return url;
            } else {
                String url = scheme + "://" + serverName + ":" + serverPort + contextPath + "/";
                return url;
            }
        }

    }

    /** 获取TargetUrl */
    public static String getTargetUrl(HttpServletRequest request) {
        String basePath = getBasePath(request);

        // 获取请求中的targetUrl
        String targetUrl = request.getParameter(Constants.TARGET_URL_KEY);

        if (StringUtils.isEmpty(targetUrl)) {
            // 若不存在，则使用默认页面作为targetUrl
            targetUrl = basePath + Constants.DEF_TARGET_URI;
        } else {
            // 判断target是否编码
            if (targetUrl.startsWith(Constants.BASE64_PREFIX)) {
                targetUrl = targetUrl.substring(Constants.BASE64_PREFIX.length());
                targetUrl = CommonUtils.Base64Utils.decodeBase64Str(targetUrl);
            }
        }
        return targetUrl;
    }

    /** 判断票据是否存在 */
    public static boolean hasTicket(HttpServletRequest request) {
        Object ticket = request.getParameter(Constants.TICKET_KEY);
        return ticket != null && !StringUtils.isEmpty(String.valueOf(ticket));
    }

    public static String getURLEncodeServiceUrl(HttpServletRequest request) throws UnsupportedEncodingException {

        // 编码成系统可识别的加密串
        String targetUrl = getTargetUrl(request);
        String base64TargetUrl = CommonUtils.Base64Utils.encodeBase64Str(targetUrl);

        String serviceUrlRoot = getBasePath(request) + Constants.SSO_LOGIN_URI;

        String serviceUrl = serviceUrlRoot + "?" + Constants.TARGET_URL_KEY + "=" + Constants.BASE64_PREFIX
                + base64TargetUrl;
        return URLEncoder.encode(serviceUrl, Constants.UTF_8_STR);
    }

    /** 获取Cas登录Url 登录成功后返回票据 */
    public static String getLoginUrl(HttpServletRequest request) throws UnsupportedEncodingException {
        String encodeServiceUrl = getURLEncodeServiceUrl(request);

        return Constants.CAS_LOGIN_URL + "?" + Constants.SERVICE_KEY + "=" + encodeServiceUrl;
    }

    /** 获取校验票据Url */
    public static String getServiceValidateUrl(HttpServletRequest request) throws UnsupportedEncodingException {

        String encodeServiceUrl = getURLEncodeServiceUrl(request);
        Object ticket = request.getParameter(Constants.TICKET_KEY);

        return Constants.CAS_VALIDATE_URL + "?" + Constants.TICKET_KEY + "=" + ticket + "&" + Constants.SERVICE_KEY
                + "=" + encodeServiceUrl;
    }

    public static LoginUser getLoginUser(HttpServletRequest request) throws IOException {
        String serviceValidateUrl = getServiceValidateUrl(request);

        // System.out.println("serviceValidateUrl + " + serviceValidateUrl);

        String casUserInfoXml = CommonUtils.HttpRequestUtils.doGet(serviceValidateUrl);

        // System.out.println("casUserInfoXml + " + casUserInfoXml);
        return new LoginUser(casUserInfoXml);
    }

    /** 获取登出地址 */
    public static String getLogoutUrl(HttpServletRequest request) throws UnsupportedEncodingException {
        // 获取本次请求的根Path
        String loginUrlRoot = getBasePath(request) + Constants.SSO_LOGIN_URI;
        String encodeLoginUrlRoot = URLEncoder.encode(loginUrlRoot, Constants.UTF_8_STR);

        return Constants.CAS_LOGOUT_URL + "?" + Constants.SERVICE_KEY + "=" + encodeLoginUrlRoot;
    }

    /** 写入单页面登录判断标志 */
    public static void login(LoginUser loginUser, HttpSession session) {
        session.setAttribute(Constants.LOGIN_KEY, true);

    }

    /** 移出单页面登录判断标志 */
    public static void logout(HttpSession session) {
        session.removeAttribute(Constants.LOGIN_KEY);
        // session.removeAttribute(Constants.LOGIN_USER_KEY);
    }

}
