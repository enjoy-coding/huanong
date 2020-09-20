package com.feather.aupipes.domain;

import com.feather.common.annotation.Excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 流速水位监测对象 aup_waterQuality_lsjc
 *
 * @author fancy
 * @date 2020-03-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupWaterqualityLsjc {

    /** $column.columnComment */
    private Long id;
    /** 监测时间 */
    @Excel(name = "监测时间")
    private String jcsj;
    /** 流速 */
    @Excel(name = "流速")
    private double ls;
    /** 水位 */
    @Excel(name = "水位")
    private double sw;

    private Long oid; // 水质;
}
