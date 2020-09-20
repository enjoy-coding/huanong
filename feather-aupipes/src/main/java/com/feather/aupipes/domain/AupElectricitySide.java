package com.feather.aupipes.domain;

import com.feather.common.annotation.Excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用电分区对象 aup_electricity_side
 *
 * @author fancy
 * @date 2020-06-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupElectricitySide {

    /** 主键 */
    private Long id;
    /** 父id */
    @Excel(name = "父id")
    private Long pid;
    /** 编码 */
    @Excel(name = "编码")
    private String cacheid;
    /** 等级 */
    @Excel(name = "等级")
    private Long cachelevel;
    /** 名称 */
    @Excel(name = "名称")
    private String cachename;
    /** 类型 */
    @Excel(name = "类型")
    private String type;
    /** 类型id */
    @Excel(name = "类型id")
    private String typeid;
 
    /** 类型编号 */
    @Excel(name = "类型编号")
    private String typeno;
    /** 建筑面积 */
    @Excel(name = "建筑面积")
    private Long buildarea;
    /** 人口数量 */
    @Excel(name = "人口数量")
    private Long personcount;
    /** 电表数量 */
    @Excel(name = "电表数量")
    private Long tableCount;
    /** 变比 */
    @Excel(name = "变比")
    private String bb;
    /** 回路名称 */
    @Excel(name = "回路名称")
    private String hlmc;
    /** 是否计算变比 */
    @Excel(name = "是否计算变比")
    private Long sfjsbb;
    /** 排序 */
    @Excel(name = "排序")
    private Long sortcode;
    /** 是否存在子节点 */
    @Excel(name = "是否存在子节点")
    private Long haschildren;
    /** 表号 */
    @Excel(name = "表号")
    private String tableid;
    /**
     * 是否计算住房用能
     */
    private Long sfjszh;
    /**
     * 用能性质id
     */
    private String categoryId;
    /**
     * 用能性质名称
     */
    private String categoryName;
    private String parentName;
    private String pcode;

    public AupElectricitySide(String cacheid) {
        this.cacheid = cacheid;
    }

    
}
