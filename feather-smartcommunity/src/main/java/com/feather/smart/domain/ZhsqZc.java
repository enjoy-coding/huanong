package com.feather.smart.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

/**
 * 社区资产对象 ZHSQ_ZC
 *
 * @author fancy
 * @date 2020-05-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZhsqZc extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 资产id */
    private String zcid;
    /** 设备类型 */
    @Excel(name = "设备类型")
    private String sblx;
    /** 设备名称 */
    @Excel(name = "设备名称")
    private String sbmc;
    /** 品牌名称 */
    @Excel(name = "品牌名称")
    private String ppmc;
    /** 设备型号 */
    @Excel(name = "设备型号")
    private String sbxh;
    /** 设备状态 */
    @Excel(name = "设备状态")
    private String sbzt;
    /** 经度 */
    @Excel(name = "经度")
    private String x;
    /** 纬度 */
    @Excel(name = "纬度")
    private String y;
    /** 高程 */
    @Excel(name = "高程")
    private String z;
    /** 位置 */
    @Excel(name = "位置")
    private String wz;
    /** 小区id */
    @Excel(name = "小区id")
    private String xqid;
}