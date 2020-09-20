package com.feather.aupipes.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

/**
 * 水分区对象 aup_water_side
 *
 * @author fancy
 * @date 2020-05-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupWaterSide extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;
    /** 姓名 */
    @Excel(name = "姓名")
    private String name;
    /** 等级 */
    @Excel(name = "等级")
    private int level;
    /** 父节点 */
    @Excel(name = "父节点")
    private Long pid;
    private String parentName;
    /** 类型 */
    @Excel(name = "类型")
    private String type;
    /** 类型id */
    @Excel(name = "类型id")
    private String typeid;
    /** 类型编码 */
    @Excel(name = "类型编码")
    private String typeno;

    private String typecode;
    /** 表号 */
    @Excel(name = "表号")
    private String tableid;
    /** 排序 */
    @Excel(name = "排序")
    private String sortcode;

    @Excel(name = "查询id")
    private String cacheId;
    /** 查询名称 */
    @Excel(name = "查询名称")
    private String cacheName;
    /** 等级 */
    @Excel(name = "等级")
    private int cacheLevel;

    /**
     * 是否有子节点 （0：无，1：有)
     */
    private int hasChildren;

    
    /**
     * 建筑面积
     */
    private double buildArea;

    /**
     * 人数
     */
    private int personCount;

    /**
     * 水表数
     */
    private int waterCount;

    /**
     * 針對房屋定位定位到樓棟
     */
    private String pCode;

    private String selectValue;

    private String categoryId;

    private String categoryName;

}