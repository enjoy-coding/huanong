package com.feather.aupipes.domain;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 维修记录对象 aup_repair
 * 
 * @author fancy
 * @date 2020-04-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupRepair extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 维修id值 */
    private Long id;
    /** 维修名称 */
    @Excel(name = "维修名称")
    private String name;
    /** 维修人员id值 */
    @Excel(name = "维修人员id值")
    private Long userId;
    /** 维修人员 */
    @Excel(name = "维修人员")
    private String userName;
    /** 维修时间 */
    @Excel(name = "维修时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date repairTime;
    /** 详细位置 */
    @Excel(name = "详细位置")
    private String address;
    /** 坐标 */
    @Excel(name = "坐标")
    private String location;
    /** 故障类型 */
    @Excel(name = "故障类型")
    private String faultType;
    /** 故障类型id值 */
    @Excel(name = "故障类型id值")
    private Long faultId;
    /** 关联管线设备id值 */
    @Excel(name = "关联管线设备id值")
    private Long deviceId;
    /** 关联管线设备名称 */
    @Excel(name = "关联管线设备名称")
    private String deviceName;
    /** 详细描述 */
    @Excel(name = "详细描述")
    private String description;
}
