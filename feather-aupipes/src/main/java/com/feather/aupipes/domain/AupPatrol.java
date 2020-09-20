package com.feather.aupipes.domain;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sky
 * @date 2020/1/15 16:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupPatrol extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String startUser;
    @Excel(name = "任务id")
    private String id;
    @Excel(name = "任务名称")
    private String taskName;
    @Excel(name = "任务区域")
    private String patrolMemo;
    @Excel(name = "发布人id")
    private String IPQC;
    @Excel(name = "发布人")
    private String IPQCName;
    @Excel(name = "巡检人员")
    private String username;
    private String userTime;
    @Excel(name = "开始时间")
    private String startdate;
    @Excel(name = "结束时间")
    private String enddate;
    @Excel(name = "巡检状态", readConverterExp = "0=在巡,1=未巡,2=已巡")
    private String status;
    @Excel(name = "下发时间")
    private String createDate;
}
