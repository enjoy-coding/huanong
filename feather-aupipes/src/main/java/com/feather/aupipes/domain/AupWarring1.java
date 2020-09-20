package com.feather.aupipes.domain;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 预警记录信息对象 aup_warring
 * 
 * @author fancy
 * @date 2020-01-15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupWarring1 extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 预警原因 */
    @Excel(name = "预警来源")
    private String author;
    /** 预警内容 */
    @Excel(name = "预警内容")
    private String content;
    /** 预警时间 */
    @Excel(name = "预警时间")
    private String dateTime;

    /** 预警状态 */
    @Excel(name = "预警状态")
    private String state;

    /** 预警名称 */
    @Excel(name = "预警原因")
    private String name;

    /** 设备编码 */
    @Excel(name = "设备编码")
    private String code;
    /** 预警等级 */
    @Excel(name = "预警等级")
    private String level;


    /** 来源 */
    @Excel(name = "预警名称")
    private String formSysName;

}
