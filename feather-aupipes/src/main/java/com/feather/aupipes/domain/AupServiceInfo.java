package com.feather.aupipes.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

/**
 * 设备对象 aup_service_info
 * 
 * @author fancy
 * @date 2020-06-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupServiceInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 设备id */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    /** 设备名称 */
    @Excel(name = "设备名称")
    private String serviceName;
    /** 设备型号 */
    @Excel(name = "设备型号")
    private String serviceModelNum;
    /** 设备地址 */
    @Excel(name = "设备地址")
    private String serviceAddress;
    /** 设备状态，输入以|分隔的状态 */
    @Excel(name = "设备状态")
    private String serviceStatus;
}
