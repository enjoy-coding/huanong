package com.feather.lpscommunity.domain;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 智慧社区实体多图对象 sc_file_info
 *
 * @author dufan
 * @date 2019-10-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScFileInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** null */
    private Long fileId;

    /** 来源实体 */
    @Excel(name = "来源实体")
    private Long fileTarget;

    /** 对应字符 */
    @Excel(name = "对应字符")
    private String fileCode;

    /** 类型 */
    @Excel(name = "类型")
    private String fileType;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String fileName;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filePath;

    public ScFileInfo(Long fileTarget, String fileCode) {
        this.fileTarget = fileTarget;
        this.fileCode = fileCode;
    }
}
