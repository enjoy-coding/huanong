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
 * 参数配置表 sys_config
 * 
 * @author feather
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysConfig extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 参数主键 */
    @Excel(name = "参数主键", cellType = ColumnType.NUMERIC)
    private Long configId;

    /** 参数名称 */
    @Excel(name = "参数名称")
    @NotBlank(message = "参数名称不能为空")
    @Size(min = 0, max = 100, message = "参数名称不能超过100个字符")
    private String configName;

    /** 参数键名 */
    @Excel(name = "参数键名")
    @NotBlank(message = "参数键名长度不能为空")
    @Size(min = 0, max = 100, message = "参数键名长度不能超过100个字符")
    private String configKey;

    /** 参数键值 */
    @Excel(name = "参数键值")
    @NotBlank(message = "参数键值不能为空")
    @Size(min = 0, max = 500, message = "参数键值长度不能超过500个字符")
    private String configValue;

    /** 系统内置（Y是 N否） */
    @Excel(name = "系统内置", readConverterExp = "Y=是,N=否")
    private String configType;
}
