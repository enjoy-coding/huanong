package com.feather.website.config;

import com.feather.website.dialect.CmsDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author nothing
 * @date 2019-11-04 17:59
 */
@Configuration
public class ThymeleafDialectConfig {
    /**
     * 系统方言
     *
     * @return
     */
    @Bean
    public CmsDialect cmsDialect() {
        return new CmsDialect();
    }
}
