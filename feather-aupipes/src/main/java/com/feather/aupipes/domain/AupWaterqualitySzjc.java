package com.feather.aupipes.domain;

import com.feather.common.annotation.Excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 水质监测对象 aup_waterQuality_szjc
 *
 * @author fancy
 * @date 2020-03-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupWaterqualitySzjc {

    /** $column.columnComment */
    private Long id;
    /** 监测时间 */
    @Excel(name = "监测时间")
    private String jcsj;
    /** 监测类型（水质监测/流速水位监测） */
    @Excel(name = "监测类型", readConverterExp = "水=质监测/流速水位监测")
    private String jclx;
    /** 荧光法溶解氧 */
    @Excel(name = "荧光法溶解氧")
    private int ygfrjy;
    /** 电磁式电导率 */
    @Excel(name = "电磁式电导率")
    private int dcsddl;
    /** 浊度 */
    @Excel(name = "浊度")
    private double zd;
    /** pH 值 */
    @Excel(name = "pH 值")
    private int ph;
    /** ORP */
    @Excel(name = "ORP")
    private int orp;
    /** 温度 */
    @Excel(name = "温度")
    private int wd;

    private Long oid; // 水质;
}