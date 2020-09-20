package com.feather.aupipes.domain;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupArea {
    private String id;
    private String name;
    private String type;
    private String pid;
    private String level;
    private String areaNo;
    private double useArea;
    private String dingweiCode;
    private String updateTime;
    private String parentName;
    private Map<String,Object> params;
    
    
    public Map<String, Object> getParams() {
        if (params == null) {
            params = new HashMap<>();
        }
        return params;
    }

   
}