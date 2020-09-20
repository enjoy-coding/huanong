package com.feather.prd.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息发送日志用户对象 prd_msg_user
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrdMsgUser {
    private Long msgId;
    private Long userId;

    private String loginName;
    private String userName;

    private String phonenumber;
    private String openid;

    /*
     * 0成功 1失败 -等待
     */
    private String sendStatus;
    /** 发送时间 */
    private Date sendTime;
    /** 发送次数 */
    private int sendCount;

    /** 发送错误 */
    private String sendError;
}
