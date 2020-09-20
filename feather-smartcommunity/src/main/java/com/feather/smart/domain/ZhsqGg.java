package com.feather.smart.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

/**
 * 公告对象 ZHSQ_GG
 * 
 * @author fancy
 * @date 2020-05-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZhsqGg extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 公告ID */
    @Excel(name = "公告ID")
    private String ggid;
    /** 公告类型 */
    @Excel(name = "公告类型")
    private String gglx;
    /** 标题 */
    @Excel(name = "标题")
    private String bt;
    /** 内容 */
    @Excel(name = "内容")
    private String nr;
    /** 发布时间 */
    @Excel(name = "发布时间")
    private String fbsj;
    /** 发布人员 */
    @Excel(name = "发布人员")
    private String fbry;
    /** 信息来源 */
    @Excel(name = "信息来源")
    private String xxly;
}
