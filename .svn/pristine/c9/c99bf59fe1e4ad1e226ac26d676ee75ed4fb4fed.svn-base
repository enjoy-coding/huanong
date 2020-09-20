package com.feather.aupipes.domain;

import com.feather.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.feather.common.annotation.Excel;

/**
 * 预警类型对象 aup_warring_category
 *
 * @author fancy
 * @date 2019-12-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupWarringCategory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 数据标识 */
    private Long id;
    /** 名称 */
    @Excel(name = "名称")
    private String name;
    /** $column.columnComment */
    @Excel(name = "名称")
    private String code;
    /** 父节点 */
    @Excel(name = "父节点")
    private String pid;
    /** 类型等级 */
    @Excel(name = "类型等级")
    private Long level;
    /** 对接查询类型id */
    @Excel(name = "对接查询类型id")
    private String categoryid;
    /** 对接类型父节点id */
    @Excel(name = "对接类型父节点id")
    private String categoryparentid;
}