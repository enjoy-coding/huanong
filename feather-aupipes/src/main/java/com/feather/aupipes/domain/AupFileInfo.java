package com.feather.aupipes.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 【请填写功能名称】对象 aup_file_info
 * 
 * @author fancy
 * @date 2020-04-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupFileInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
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
}
