package com.feather.aupipes.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：管网分析查询的图层
 *
 * @Author: fang xinliang
 * @Date: 2020/2/11 16:00
 */
@Component
@ConfigurationProperties(prefix = "query")
public class QueryConfig {
    private List<Map<String, Object>> templates=new ArrayList<Map<String, Object>>();;

    public List<Map<String, Object>> getTemplates(){
        return  templates;
    }
    public void  setTemplates(List<Map<String, Object>> templates){
        this.templates= templates;
    }
}
