package com.feather.aupipes.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

/**
 * 配电房对象 aup_power
 *
 * @author fancy
 * @date 2020-01-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupPower extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;
    /** 标识码 */
    @Excel(name = "标识码")
    private String bsm;
    /** 名称 */
    @Excel(name = "名称")
    private String name;
    /** 类型编号0变电站，1一级配电房，2二级配电房，3箱变 */
    @Excel(name = "类型编号0变电站，1一级配电房，2二级配电房，3箱变")
    private String lxbh;
    /** 类型 */
    @Excel(name = "类型")
    private String type;
    /** 父级标识 */
    @Excel(name = "父级标识")
    private String fjbs;
    /** 设备情况 */
    @Excel(name = "设备情况")
    private String sbqk;
    /** X坐标 */
    @Excel(name = "X坐标")
    private String x;
    /** Y坐标 */
    @Excel(name = "Y坐标")
    private String y;
    /** 高程 */
    @Excel(name = "高程")
    private String h;
    /** 面积 */
    @Excel(name = "面积")
    private String mj;
    /** 备注 */
    @Excel(name = "备注")
    private String bz;
    /** 关联楼栋code */
    @Excel(name = "关联楼栋code")
    private String areano;
    /** 父级id */
    @Excel(name = "父级id")
    private Long pid;

    private int byq;

    private int hl;

    private String hlmc; //回路名称

    private String gh;//柜号
    private String hlId;//回路ID
    private String fwId;//房屋ID

    private String parentName;

    private int hasChildren;
}