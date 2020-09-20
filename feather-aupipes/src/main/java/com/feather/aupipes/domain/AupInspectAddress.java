package com.feather.aupipes.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.feather.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.feather.common.annotation.Excel;

/**
 * 【请填写功能名称】对象 aup_inspect_address
 * 
 * @author fancy
 * @date 2020-01-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupInspectAddress extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long addressId;
    /** 巡检地点 */
    @Excel(name = "巡检地点")
    private String inspectAddress;
    /** 轨迹坐标集 */
    @Excel(name = "轨迹坐标集")
    private String track;
    /** 任务id */
    @Excel(name = "任务id")
    private Long taskId;
}
