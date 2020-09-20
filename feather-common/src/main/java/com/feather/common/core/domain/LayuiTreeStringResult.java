package com.feather.common.core.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * layui treetable 后台构造实体类
 * id,pid为String类型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LayuiTreeStringResult extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String pid;
    private String state;
    private boolean open;
    private List<LayuiTreeResult> children;
    private boolean haveChild;

}
