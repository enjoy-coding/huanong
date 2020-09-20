package com.feather.aupipes.sso.supwis;

import java.nio.charset.Charset;

public interface Constants {
    // CAS根地址
    String CAS_BASE_PATH = "https://cas.hzau.edu.cn/cas/";
    // 业务系统需要显式使用的端口配置，包括80端口，如果不需要配置显式端口，则配置空字符串""即可
    String CLIENT_SYSTEM_EXPLICIT_PORT = "";

    // CAS票据验证地址
    String CAS_VALIDATE_URL = CAS_BASE_PATH + "serviceValidate";

    // CAS登录地址
    String CAS_LOGIN_URL = CAS_BASE_PATH + "login";

    // CAS注销地址
    String CAS_LOGOUT_URL = CAS_BASE_PATH + "logout";

    // 登录成功默认跳转地址
    String DEF_TARGET_URI = "screen/index";

    // 业务系统认证集成改造之后的登录URI
    String SSO_LOGIN_URI = "sso/dLogin";

    // REQUEST中获取需要跳转URL的KEY
    String TARGET_URL_KEY = "targetUrl";

    // SESSION中判断是否登录的KEY
    String LOGIN_KEY = "isSupwisdomCasLogin";
    String LOGIN_USER_KEY = "supwisdomCasLoginUser";

    // REQUEST中获取票据的KEY
    String TICKET_KEY = "ticket";

    // CAS Server验证成功后需跳转客户端Url的Key
    String SERVICE_KEY = "service";

    // BASE64编码的前缀
    String BASE64_PREFIX = "{base64}";

    // 默认编码字符串格式
    String UTF_8_STR = "UTF-8";

    // 默认编码
    Charset UTF_8 = Charset.forName(UTF_8_STR);
}
