package com.feather.framework.config.properties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author feather
 */
@Configuration
@ConfigurationProperties(prefix = "feather.websocket")
@ConditionalOnProperty(prefix = "feather.websocket", name = "enabled", havingValue = "true")
public class WebSocketProperties {
    private String endpoint;
    private String origins;
    private boolean sockJS;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getOrigins() {
        return origins;
    }

    public void setOrigins(String origins) {
        this.origins = origins;
    }

    public boolean isSockJS() {
        return sockJS;
    }

    public void setSockJS(boolean sockJS) {
        this.sockJS = sockJS;
    }
}
