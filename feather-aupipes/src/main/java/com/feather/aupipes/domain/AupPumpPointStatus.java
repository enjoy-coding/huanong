package com.feather.aupipes.domain;

import com.feather.common.annotation.Excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 泵组状态值对象 aup_pump_point_status
 *
 * @author fancy
 * @date 2020-04-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupPumpPointStatus {

    /** $column.columnComment */
    private Long id;
    /** 组表 */
    @Excel(name = "组表")
    private Long pointId;
    /** 泵表 */
    @Excel(name = "泵表")
    private Long pumpId;
    /** 状态名 */
    @Excel(name = "状态名")
    private String statusname;
    /** 状态值 */
    @Excel(name = "状态值")
    private String status;
    /** 读取时间 */
    @Excel(name = "读取时间")
    private String readtime;

    private String pointCode;

    private AupPumpPoint aupPumpPoint;
}