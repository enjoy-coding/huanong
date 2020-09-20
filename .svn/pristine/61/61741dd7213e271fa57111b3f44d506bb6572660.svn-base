package com.feather.aupipes.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

/**
 * 预警信息统计对象 aup_yjtables
 * 
 * @author fancy
 * @date 2020-01-06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupYjtables extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private String tid;
    /** 当前报警数 */
    @Excel(name = "当前报警数")
    private String pubcurtatal;
    /** 报警总数 */
    @Excel(name = "报警总数")
    private String pubtatal;
    /** 百分比 */
    @Excel(name = "百分比")
    private String pubpercent;
    /** 名称 */
    @Excel(name = "名称")
    private String name;
    /** 样式 */
    @Excel(name = "样式")
    private String icon;
}
