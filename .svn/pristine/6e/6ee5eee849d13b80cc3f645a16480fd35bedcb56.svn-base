package com.feather.wx.util;

/**
 * @author nothing
 * @date 2019-12-24 18:33
 */
public class AccessToken {

    private String accessToken;
    private int expiresIn;
    private long createTime;
    private Integer errcode;
    private String errmsg;

    public AccessToken() {
        this.createTime = CalendarUtil.getTimeInSeconds();
    }

    public AccessToken(String accessToken, int expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.createTime = CalendarUtil.getTimeInSeconds();
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
        this.errmsg = ErrCode.errMsg(errcode);
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    /**
     * 是否超时，微信默认7200s超时
     *
     * @return true-超时；false-没有超时
     */
    public boolean isExpires() {
        long now = CalendarUtil.getTimeInSeconds();
        //预留 10s
        return now - this.createTime - 10 >= this.expiresIn;
    }

    /**
     * 是否超时
     *
     * @return true-超时；false-没有超时
     */
    public boolean isExpires(Long expireTime) {
        long now = CalendarUtil.getTimeInSeconds();
        return now - this.createTime - 10 >= expireTime; //预留 10s
    }


}
