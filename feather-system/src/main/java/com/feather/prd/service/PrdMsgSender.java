package com.feather.prd.service;

import com.feather.prd.domain.PrdMsg;

/**
 * 消息发送接口
 */
public interface PrdMsgSender {
    /**
     * 检查消息
     * 
     * @param prdMsg
     */
    public void check(PrdMsg prdMsg);

    /**
     * 发送消息
     * 
     * @param prdMsg
     * @param userId
     * @return 异常信息
     */
    public Exception send(PrdMsg prdMsg, Long userId);
}