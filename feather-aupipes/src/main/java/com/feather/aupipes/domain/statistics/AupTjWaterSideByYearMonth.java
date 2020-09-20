package com.feather.aupipes.domain.statistics;

import java.util.Map;

import com.feather.common.annotation.Excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统计用水分区每一年每一月的叠加用量和号对应用量对象 aup_TjWaterSideByYearMonth
 *
 * @author fancy
 * @date 2020-05-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupTjWaterSideByYearMonth {

    /** 主键 */
    private Long id;
    /** 分区id */
    private String cacheid;
    /** 查询名称 */
    @Excel(name = "分区名称")
    private String cachename;

    private Long cachelevel;
    /** 年份 */
    @Excel(name = "年份")
    private Long year;
    /** 月份 */
    @Excel(name = "月份")
    private Long month;
    /** 表号对应用量 */
    @Excel(name = "总量（吨）")
    private double tableusenumber;
    /** 叠加求和用量 */
    @Excel(name = "汇总量（吨）")
    private double sumusenumber;

    private Long sideid;

    private String createtime;

    @Excel(name = "损耗率")
    private double shl;
    /** 请求参数 **/
    private Map<String, Object> params;
}
