package com.feather.aupipes.domain;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 区域树结构对象 aup_building_area
 *
 * @author fancy
 * @date 2019-12-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupBuildingArea extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 名称
     */
    @Excel(name = "名称")
    private String name;
    /**
     * 类型
     */
    @Excel(name = "类型")
    private String nodetype;
    /**
     * 坐标
     */
    @Excel(name = "坐标")
    private String xy;
    /**
     * 父节点
     */
    @Excel(name = "父节点")
    private Long mapid;
    /**
     * 展示编码 root-0006-00021
     */
    @Excel(name = "展示编码 root-0006-00021")
    private String code;

    @Excel(name = "展示编码 root-0006-00021")
    private String parentCode;

    private String areaNo;

    private int level; //等级

    private String important;//是否为重点建筑

    private String acreage;//面积

    private String peopleCounting;//人数统计

    private String parentName;//父节点名称

    private String no;//父级编号

    private String buildName;//父级编号

    private String buildingType;//楼栋类型

    private String id_;
    private String pid_;
}