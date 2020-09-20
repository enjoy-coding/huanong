package com.feather.aupipes.domain;

import com.feather.common.annotation.Excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 泵组基本数据值对象 aup_pump_point_base
 *
 * @author fancy
 * @date 2020-04-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupPumpPointBase {

    /** $column.columnComment */
    private Long id;
    /** 组表 */
    @Excel(name = "组表")
    private Long pointId;
    /** 泵表 */
    @Excel(name = "泵表")
    private Long pumpId;
    /** 读取时间 */
    @Excel(name = "读取时间")
    private String readTime;

    private String attrName;
    private String attrValue;

    private String szyl;
}