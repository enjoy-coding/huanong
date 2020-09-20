package com.feather.smart.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

/**
 * 车位对象 ZHSQ_CW
 * 
 * @author fancy
 * @date 2020-05-15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZhsqCw extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 车位id */
    @Excel(name = "车位id")
    private String cwid;
    /** 车位类型(地上,地下) */
    @Excel(name = "车位类型(地上,地下)")
    private String cwlx;
    /** 车位编号 */
    @Excel(name = "车位编号")
    private String cwbh;
    /** 当前车位状态(空闲,占用) */
    @Excel(name = "当前车位状态(空闲,占用)")
    private String cwzt;
    /** 小区id */
    @Excel(name = "小区id")
    private String xqid;
    /** 社区id */
    @Excel(name = "社区id")
    private String sqid;
    /** 停车场id */
    @Excel(name = "停车场id")
    private String tccid;
    /** 停车场名称 */
    @Excel(name = "停车场名称")
    private String tccmc;
    /** 车位种类 */
    @Excel(name = "车位种类")
    private String cwzl;
}
