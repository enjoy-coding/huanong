package com.feather.common.config;

/**
 * @author feather
 */
public interface MessageDelegate {
    String getTopic();

    MessageReceiveDelegate getMessageReceiveDelegate();

    void send(Object payload);
}
