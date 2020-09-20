package com.feather.web;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.ui.ModelMap;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import com.feather.common.config.Global;

@Configuration
public class ThymeleafConfig {
    @Resource
    private void configureThymeleafStaticVars(ThymeleafViewResolver viewResolver) {
        if (viewResolver != null) {
            ModelMap vars = new ModelMap();

            Object obj = Global.getConfigObject("spring.thymeleaf.staticVariables");
            if (obj instanceof Map) {
                @SuppressWarnings({ "rawtypes", "unchecked" })
                Map<String, String> map = (Map) obj;
                for (Entry<String, String> entry : map.entrySet()) {
                    vars.put(entry.getKey(), entry.getValue());
                }
            }
            viewResolver.setStaticVariables(vars);
        }
    }
}
