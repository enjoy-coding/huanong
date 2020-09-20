package com.feather.aupipes.domain;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class LayuiTree {

    private long id;
    private String field;
    private String title;
    private Boolean spread;
    private Map<String,Object> maps;
    private List<LayuiTree> children;

    public LayuiTree() {
    }

    public LayuiTree(long id, String field, String title,Map<String,Object> maps ) {
        this.id = id;
        this.field = field;
        this.title = title;
        this.maps = maps;
        this.spread = false;
    }
}
