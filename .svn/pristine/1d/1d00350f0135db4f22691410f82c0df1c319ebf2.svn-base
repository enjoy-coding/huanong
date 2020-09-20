package com.feather.aupipes.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

/**
 * 巡检任务人员关联对象 aup_inspect_worker
 * 
 * @author fancy
 * @date 2020-06-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupInspectWorker extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;
    /** 巡检任务id */
    @Excel(name = "巡检任务id")
    private Long inspectId;
    /** 用户id */
    @Excel(name = "用户id")
    private Long inspectWorkerId;
}
