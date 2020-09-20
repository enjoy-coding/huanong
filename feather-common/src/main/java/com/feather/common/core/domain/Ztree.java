package com.feather.common.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * Ztree树结构实体类
 *
 * @author feather
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ztree implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 节点ID
     */
    private Long id;

    private String id_;

    /**
     * 节点父ID
     */
    private Long pId;
    private String pid_;


    /**
     * 节点层级
     */
    private Integer level;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 节点名称路径
     */
    private String namePath;

    /**
     * 节点代码
     */
    private String code;

    private String paretCode;

    private Map<String, Object> maps;

    private boolean isParent;


    /**
     * 节点标题
     */
    private String title;

    /**
     * 是否勾选
     */
    private boolean checked = false;

    /**
     * 是否展开
     */
    private boolean open = false;

    /**
     * 是否能勾选
     */
    private boolean nocheck = false;

    public boolean getIsParent(){
        return isParent;
    }

    public void setIsParent(boolean isParent){
        this.isParent = isParent;
    }
    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public Ztree(Long id, Long pId, String name, String title, boolean open) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.title = title;
        this.open = open;
    }

    public Ztree(Long id, Long pId, String name, String title, String code, boolean open) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.title = title;
        this.code = code;
        this.open = open;
    }

    public Ztree(Long id, Long pId, String name, boolean open, Map<String, Object> params) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.open = open;
        this.maps = params;
    }
}
