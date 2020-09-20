package com.feather.framework.shiro.realm;

public enum LoginType {
    PASSWORD("User"), // 密码登录
    NOPASSWORD("Free"); // 免密登录

    private String code;// 状态值

    private LoginType(String code) {
        this.code = code;
    }
    public String getCode () {
        return code;
    }
}
