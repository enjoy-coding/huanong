package com.feather.aupipes.domain;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 停水停电消息内容对象 aup_sendMessage_log
 *
 * @author fancy
 * @date 2020-05-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupSendmessageLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 排序Id */
    private Long id;
    /** 消息标题 */
    @Excel(name = "消息标题")
    private String messagetitle;
    /** 消息内容 */
    @Excel(name = "消息内容")
    private String messagecontent;
    /** 发送时间 */
    @Excel(name = "发送时间")
    private String sendtime;
    /** 发送人 */
    @Excel(name = "发送人")
    private String senduser;
    /** 记录时间 */
    @Excel(name = "记录时间")
    private String addtime;
}
