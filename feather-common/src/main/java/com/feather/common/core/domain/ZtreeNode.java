package com.feather.common.core.domain;

import java.io.Serializable;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nothing
 * @date 2020-01-20 11:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZtreeNode implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 节点ID
     */
    private String id;

    /**
     * 节点父ID
     */
    private String pId;

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
    private boolean checked;
    /**
     * 是否展开
     */
    private boolean open;
    /**
     * 是否能勾选
     */
    private boolean nocheck;

    public ZtreeNode(String id, String pId, String name, String title) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.title = title;
    }

    public boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(boolean isParent) {
        this.isParent = isParent;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

}
