package com.feather.napo.config;

import com.feather.napo.filter.RequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * @author nothing
 * @date 2020-06-30 16:28
 */

@Configuration
public class RequestFilterConfig {

    @Bean
    public Filter requestFilter() {
        return new RequestFilter();
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(requestFilter());
        filterRegistrationBean.addUrlPatterns("/np/api/listNpInfo");
        filterRegistrationBean.setOrder(Integer.MIN_VALUE);
        return filterRegistrationBean;
    }

}
