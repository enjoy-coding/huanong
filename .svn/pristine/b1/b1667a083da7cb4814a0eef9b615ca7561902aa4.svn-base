package com.feather.aupipes.domain;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 停水停电通知消息对象 aup_tdtsMessage_log
 * 
 * @author fancy
 * @date 2020-05-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupTdtsmessageLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 排序Id */
    private Long id;
    /** 发送用户表的ID */
    @Excel(name = "发送用户表的ID")
    private Long userid;
    /** 发送人姓名 */
    @Excel(name = "发送人姓名")
    private String username;
    /** 微信openId */
    @Excel(name = "微信openId")
    private String userwxopenid;
    /** 手机号 */
    @Excel(name = "手机号")
    private Long userphone;
    /** 发送时间 */
    @Excel(name = "发送时间")
    private String sendtime;
    /** 消息表Id */
    @Excel(name = "消息表Id")
    private Long messageid;
}
