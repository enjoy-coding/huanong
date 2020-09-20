package com.feather.framework.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.feather.framework.config.properties.WebSocketProperties;

/**
 * @author feather
 */
@Configuration
@ConditionalOnProperty(prefix = "feather.websocket", name = "enabled", havingValue = "true")
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Autowired
    private WebSocketProperties properties;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        StompWebSocketEndpointRegistration reg = registry.addEndpoint(properties.getEndpoint());
        if (StringUtils.isNotEmpty(properties.getOrigins())) {
            reg.setAllowedOrigins(properties.getOrigins());
        }
        if (properties.isSockJS()) {
            reg.withSockJS();
        }
    }
}
