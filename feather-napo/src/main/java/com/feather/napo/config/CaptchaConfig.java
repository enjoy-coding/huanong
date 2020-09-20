package com.feather.napo.config;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.feather.common.config.Global;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

/**
 * 验证码配置
 * 
 * @author feather
 */
@Configuration
public class CaptchaConfig {
    @Bean
    public Producer getKaptchaBean() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        setPropertiesItem(Constants.KAPTCHA_SESSION_CONFIG_KEY, properties);
        setPropertiesItem(Constants.KAPTCHA_SESSION_CONFIG_DATE, properties);
        setPropertiesItem(Constants.KAPTCHA_BORDER, properties);
        setPropertiesItem(Constants.KAPTCHA_BORDER_COLOR, properties);
        setPropertiesItem(Constants.KAPTCHA_BORDER_THICKNESS, properties);
        setPropertiesItem(Constants.KAPTCHA_NOISE_COLOR, properties);
        setPropertiesItem(Constants.KAPTCHA_NOISE_IMPL, properties);
        setPropertiesItem(Constants.KAPTCHA_OBSCURIFICATOR_IMPL, properties);
        setPropertiesItem(Constants.KAPTCHA_PRODUCER_IMPL, properties);
        setPropertiesItem(Constants.KAPTCHA_TEXTPRODUCER_IMPL, properties);
        setPropertiesItem(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, properties);
        setPropertiesItem(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, properties);
        setPropertiesItem(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, properties);
        setPropertiesItem(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, properties);
        setPropertiesItem(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, properties);
        setPropertiesItem(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, properties);
        setPropertiesItem(Constants.KAPTCHA_WORDRENDERER_IMPL, properties);
        setPropertiesItem(Constants.KAPTCHA_BACKGROUND_IMPL, properties);
        setPropertiesItem(Constants.KAPTCHA_BACKGROUND_CLR_FROM, properties);
        setPropertiesItem(Constants.KAPTCHA_BACKGROUND_CLR_TO, properties);
        setPropertiesItem(Constants.KAPTCHA_IMAGE_WIDTH, properties);
        setPropertiesItem(Constants.KAPTCHA_IMAGE_HEIGHT, properties);
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

    private void setPropertiesItem(String paramName, Properties properties) {
        String key = paramName;
        if (Constants.KAPTCHA_BORDER.equals(paramName)) {
            key = Constants.KAPTCHA_BORDER + ".border";
        }
        String config = Global.getConfig(key);
        if (StringUtils.isNotBlank(config)) {
            properties.setProperty(paramName, config);
        }
    }
}
