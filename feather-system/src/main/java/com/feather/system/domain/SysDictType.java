package com.feather.system.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.feather.common.annotation.Excel;
import com.feather.common.annotation.Excel.ColumnType;
import com.feather.common.core.domain.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 字典类型表 sys_dict_type
 * 
 * @author feather
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysDictType extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 字典主键 */
    @Excel(name = "字典主键", cellType = ColumnType.NUMERIC)
    private Long dictId;

    /** 字典名称 */
    @Excel(name = "字典名称")
    @NotBlank(message = "字典名称不能为空")
    @Size(min = 0, max = 100, message = "字典类型名称长度不能超过100个字符")
    private String dictName;

    /** 字典类型 */
    @Excel(name = "字典类型 ")
    @NotBlank(message = "字典类型不能为空")
    @Size(min = 0, max = 100, message = "字典类型类型长度不能超过100个字符")
    private String dictType;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    @Excel(name = "角色")
    private Long dictRole;
    private String dictRoleKey;
    private String dictRoleName;
}
