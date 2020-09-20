package com.feather.wx.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author feather
 */
@Data
@Entity
@Table(name = "wx_user")
public class WxUser {
    public static final int SUBSCRIBED = 1;
    public static final int UNSUBSCRIBE = 0;

    @Id
    @Column(name = "wxid")
    private Long wxid;

    @Column(name = "openid")
    private String openid;

    @Column(name = "subscrible")
    private Integer subscrible;

    public boolean isSubscrible() {
        return subscrible != null && subscrible == SUBSCRIBED;
    }

    public void setSubscrible(boolean flag) {
        subscrible = flag ? SUBSCRIBED : UNSUBSCRIBE;
    }
}
