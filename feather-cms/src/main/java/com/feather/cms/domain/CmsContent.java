package com.feather.cms.domain;

import com.feather.common.core.domain.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件内容对象 cms_file
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CmsContent extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String fileId;
    /** 所属域 */
    private byte[] fileContent;

    public boolean hasContent() {
        return fileContent != null && fileContent.length > 0;
    }
}
