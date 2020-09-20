package com.feather.aupipes.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

/**
 * 巡检记录设备对象 aup_inspect_service
 * 
 * @author fancy
 * @date 2020-06-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupInspectService extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    /** 设备名称 */
    @Excel(name = "设备名称")
    private String serviceName;
    /** 设备状态 */
    @Excel(name = "设备状态")
    private String serviceStatus;
    /** 设备情况 */
    @Excel(name = "设备情况")
    private String serviceSituation;
    /** 巡检任务id */
    @Excel(name = "巡检任务id")
    private Long taskId;
    /** 巡检记录id */
    @Excel(name = "巡检记录id")
    private Long detailId;
    /** 设备id */
    @Excel(name = "设备id")
    private Long serviceId;

    private String ids;
    private String serviceIds;

    @Excel(name = "巡检任务")
    private String taskName;

    @Excel(name = "巡检记录")
    private String detailName;

    @Excel(name = "设备状态")
    private String serviceStatusText;
}
