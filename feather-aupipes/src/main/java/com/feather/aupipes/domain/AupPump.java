package com.feather.aupipes.domain;

import com.feather.common.annotation.Excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 泵房
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AupPump {

    /**
     * id
     */
    private Long id;
    /**
     * 名称
     */
    @Excel(name = "名称")
    private String name;
    /**
     * 父节点
     */
    @Excel(name = "")
    private Long pid;
    /**
     *
     */
    @Excel(name = "")
    private String bfid;
    /**
     * 隐藏id
     */
    @Excel(name = "隐藏id")
    private String cacheid;
    /**
     * 隐藏名称
     */
    @Excel(name = "隐藏名称")
    private String cachename;
    /**
     * 等级
     */
    @Excel(name = "等级")
    private Long cachelevel;

    /** 等级 */
    @Excel(name = "等级")
    private int pLevel;
    /** 加压情况 */
    @Excel(name = "加压情况")
    private String jyqk;

    private String value;
    private String statusname;
    private String status;
    private String readTime;

    public AupPump(Long id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public AupPump(String statusname, String status, String readTime) {
        this.statusname = statusname;
        this.status = status;
        this.readTime = readTime;
    }
}
