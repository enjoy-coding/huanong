package com.feather.aupipes.domain;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 【管线设备】对象 aup_repair_pipeline
 * 
 * @author fancy
 * @date 2020-04-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupRepairPipeline extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;
    /** 关联的任务id */
    @Excel(name = "关联的任务id")
    private Long taskId;
    /** 管线id */
    @Excel(name = "管线id")
    private String guid;
    /** 管线名称 */
    @Excel(name = "管线名称")
    private String name;
    /** 管线坐标 */
    @Excel(name = "管线坐标")
    private String location;
    /** 管线/点编号 */
    @Excel(name = "管线/点编号")
    private String serialNumber;
    /** 管线起点 */
    @Excel(name = "管线起点")
    private String startPoint;
    /** 管线终点 */
    @Excel(name = "管线终点")
    private String endPoint;
    /** 管点属性 */
    @Excel(name = "管点属性")
    private String expNo;
}
