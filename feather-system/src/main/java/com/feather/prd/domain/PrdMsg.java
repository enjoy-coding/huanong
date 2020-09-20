package com.feather.prd.domain;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;
import com.feather.common.utils.DateUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息发送日志对象 prd_msg
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrdMsg extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long msgId;

    /** 消息批次 */
    private Long msgBatch;

    /** 应用类型 */
    @Excel(name = "应用类型")
    private String msgApp;
    private String msgAppName;
    /** 消息分类 */
    @Excel(name = "消息分类")
    private String msgType;
    private String msgTypeName;
    /** 消息标题 */
    @Excel(name = "消息标题")
    private String msgTitle;
    /** 消息内容 */
    @Excel(name = "消息内容")
    private String msgContent;
    /** 消息链接 */
    @Excel(name = "消息链接")
    private String msgUrl;

    // private List<SysUser> users;
    private int sendCount;
    private int okCount;

    public String getSendTime() {
        return DateUtils.parseDateToStr("yyyy-MM-dd HH:mm", getCreateTime());
    }

    public PrdMsg copy() {
        PrdMsg prdMsg = new PrdMsg();
        prdMsg.setMsgId(msgId);
        prdMsg.setMsgBatch(msgBatch);
        prdMsg.setMsgApp(msgApp);
        prdMsg.setMsgAppName(msgAppName);
        prdMsg.setMsgType(msgType);
        prdMsg.setMsgTypeName(msgTypeName);
        prdMsg.setMsgTitle(msgTitle);
        prdMsg.setMsgContent(msgContent);
        prdMsg.setMsgUrl(msgUrl);
        prdMsg.setSendCount(sendCount);
        prdMsg.setOkCount(okCount);
        prdMsg.setCreateBy(getCreateBy());
        prdMsg.setCreateTime(getCreateTime());
        return prdMsg;
    }
}
