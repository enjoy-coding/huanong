package com.feather.cms.domain;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 参数对象 cms_args
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CmsArgs extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long argsId;
    /** 参数主体 */
    @Excel(name = "参数主体")
    private Long argsEntity;
    /** 参数主体 */
    @Excel(name = "参数标签")
    private Long argsTag;
    /** 参数键 */
    @Excel(name = "参数键")
    private String argsKey;
    /** 参数值 */
    @Excel(name = "参数值")
    private String argsValue;
    /** $column.columnComment */
    @Excel(name = "排序值")
    private Long argsSort;
}
