package com.feather.wx.service;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.feather.wx.entity.WxUser;

/**
 * @author feather
 */
@Service
public interface WxUserService extends JpaRepository<WxUser, Long> {
    /**
     * 当且认为openid在表中是唯一的
     */
    WxUser findByOpenid(String openid);

    @Modifying
    @Transactional
    @Query("update WxUser w set w.subscrible=?2 where w.openid=?1")
    void updateSubscribleByOpenid(String openid, Integer subscrible);
}
