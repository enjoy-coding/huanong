package com.feather.wx.util;

/**
 * @author nothing
 * @date 2019-12-04 9:54
 */
public class TextMessage extends BaseMessage {
    private String content;
    private String msgId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

}
