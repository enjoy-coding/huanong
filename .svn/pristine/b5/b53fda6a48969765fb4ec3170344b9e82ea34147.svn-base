package com.feather.framework.shiro.realm;

public class CustomeToken extends org.apache.shiro.authc.UsernamePasswordToken {

    private static final long serialVersionUID = -2564928913725078138L;

    private LoginType type;

    public CustomeToken() {
        super();
    }

    public CustomeToken(String username, char[] password, LoginType type, boolean rememberMe, String host) {
        super(username, password, rememberMe, host);
        this.type = type;
    }

    /** 免密登录 */
    public CustomeToken(String username) {
        super(username, "", false, "nopassword");
        this.type = LoginType.NOPASSWORD;
    }

    /** 账号密码登录 */
    public CustomeToken(String username, char[] pwd) {
        super(username, pwd, false, null);
        this.type = LoginType.PASSWORD;
    }

    public LoginType getType() {
        return type;
    }

    public void setType(LoginType type) {
        this.type = type;
    }
}
