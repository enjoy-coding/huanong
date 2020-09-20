package com.feather.cms.domain;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件对象 cms_file
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CmsFile extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String fileId;
    /** 所属域 */
    @Excel(name = "所属域")
    private Long fileDomain;
    /** 名称 */
    @Excel(name = "名称")
    private String fileName;
    /** 属性 */
    @Excel(name = "属性")
    private String fileAttr;
    /** 排序值 */
    @Excel(name = "排序值")
    private Long fileSort;
    /** 人工保存 */
    @Excel(name = "人工保存")
    private String fileManual;

    public boolean isManualFile() {
        return "1".equals(fileManual);
    }
}
