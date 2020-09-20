package com.feather.aupipes.domain;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 预案管理对象 aup_plans_manage
 *
 * @author niecheng
 * @date 2019-12-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupPlansManage extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 排序ID
     */
    private Long id;
    /**
     * 父节点ID
     */
    @Excel(name = "父节点ID")
    private Long pid;
    /**
     * 文件名称
     */
    @Excel(name = "文件名称")
    private String filename;
    /**
     * 文件路径
     */
    @Excel(name = "文件路径")
    private String fileurl;
    /**
     * $column.columnComment
     */
    @Excel(name = "上传名称")
    private String plansName1;
    /**
     * $column.columnComment
     */
    //@Excel(name = "文件路径")
    private String plansName2;
    /**
     * $column.columnComment
     */
    //@Excel(name = "文件路径")
    private String plansName3;

    private String editTime;
}
