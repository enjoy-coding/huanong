package com.feather.aupipes.domain.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

import java.util.Map;

/**
 * 用电平衡统计对象 aup_TjElectricityByYearMonth
 * 
 * @author fancy
 * @date 2020-08-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupTjElectricityByYearMonth extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;
    /**  */
    @Excel(name = "编号")
    private String cacheId;
    /**  */
    @Excel(name = "名称")
    private String cacheName;
    /**  */
    @Excel(name = "等级")
    private Long cacheLevel;
    /** 年份 */
    @Excel(name = "年份")
    private Long year;
    /** 月份 */
    @Excel(name = "月份")
    private Long month;
    /** 用量 */
    @Excel(name = "总用量(度)")
    private double tableUseNumber;
    /** 变比用量 */
    @Excel(name = "变比用量")
    private double scaleTableUseNumber;
    /** 汇总用量(度) */
    @Excel(name = "汇总用量(度)")
    private double sumUseNumber;
    /** 汇总变比用量（度） */
    @Excel(name = "汇总变比用量(度)")
    private double scaleSumUseNumber;
    /** 损耗率 */
    @Excel(name = "损耗率")
    private double shl;

    /**变比损耗率 */
    @Excel(name = "变比损耗率")
    private double  scaleShl;

    /** 表具数量 */
    @Excel(name = "表具数量")
    private Long tableCount;
    private double preSumUseNumber;
    @Excel(name = "汇总量环比")
    private double sumUseNumberHb;
    /**环比上升还是下降*/
    private int hbIcon;
    /** 请求参数 **/
    private Map<String, Object> params;
}