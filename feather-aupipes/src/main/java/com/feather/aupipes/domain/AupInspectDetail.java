package com.feather.aupipes.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

/**
 * 巡检设备对象 aup_inspect_detail
 * 
 * @author fancy
 * @date 2020-06-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupInspectDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    /** 设备id */
    @Excel(name = "设备id")
    private Long serviceId;
    /** 设备地址 */
    @Excel(name = "设备地址")
    private String serviceAddress;
    /** 设备类型 */
    @Excel(name = "地点类型")
    private String serviceType;
    /** 设备类型id */
    @Excel(name = "地点类型id")
    private String serviceTypeId;
    /** 设备状态（0正常 1异常） */
    @Excel(name = "设备状态", readConverterExp = "0=正常,1=异常")
    private String serviceStatus;
    /** 巡检人员 */
    @Excel(name = "巡检人员")
    private String inspectWorker;
    /** 巡检人员id */
    @Excel(name = "巡检人员id")
    private String inspectWorkerId;
    /** 异常类型 */
//    @Excel(name = "异常类型")
    private String exceptionType;
    /** 异常类型id */
//    @Excel(name = "异常类型id")
    private String exceptionTypeId;
    /** 巡检记录 */
    @Excel(name = "巡检记录")
    private String description;
    /** 处理结果 */
//    @Excel(name = "处理结果")
    private String result;
    /** 巡检任务id */
//    @Excel(name = "巡检任务id")
    private String taskId;
    /** 巡检任务名称 */
    @Excel(name = "巡检任务名称")
    private String taskName;
    /** 设备名称 */
    @Excel(name = "设备名称")
    private String serviceName;
    @Excel(name = "设备状态")
    private String serviceStatusText;
}
