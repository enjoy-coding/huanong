package com.feather.aupipes.domain;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class LayuiTree1 {

    private long id;
    private String field;
    private String title;
    private Boolean spread;
    private Boolean checked;
    private Map<String,Object> maps;
    private List<LayuiTree1> children;

    public LayuiTree1() {
    }

    public LayuiTree1(long id, String field, String title, Map<String,Object> maps,Boolean checked ) {
        this.id = id;
        this.field = field;
        this.title = title;
        this.maps = maps;
        this.spread = false;
        this.checked = checked;
    }
}
