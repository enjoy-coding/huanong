package com.feather.aupipes.domain;

import com.feather.common.annotation.Excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 楼栋分区对象 aup_building_side
 *
 * @author fancy
 * @date 2020-03-25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupBuildingSide {

    /**
     * $column.columnComment
     */
    private int id;
    /**
     * 名称
     */
    @Excel(name = "名称")
    private String name;
    /**
     * 编码
     */
    @Excel(name = "编码")
    private String areano;
    /**
     * 父节点
     */
    @Excel(name = "父节点")
    private int pid;
    /**
     * 类型
     */
    @Excel(name = "类型")
    private String type;

    /**
     * 是否为重点建筑
     */
    @Excel(name = "重点建筑")
    private String important;
    /**
     * 等级
     */
    private int level_;

    /**
     * 查询编码
     */
    private String cacheId;

    /**
     * 楼栋类型
     */
    private String buildingType;

    public AupBuildingSide(String important, int level_, String cacheId) {
        this.important = important;
        this.level_ = level_;
        this.cacheId = cacheId;
    }
}