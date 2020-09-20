package com.feather.aupipes.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 巡检任务对象 aup_inspect
 * 
 * @author fancy
 * @date 2020-06-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupInspect extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 巡检id */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    /** 巡检任务名称 */
    @Excel(name = "巡检任务名称")
    private String inspectName;
    /** 巡检人员 */
    @Excel(name = "巡检人员")
    private String inspectWorker;
    /** 巡检人员id */
    @Excel(name = "巡检人员id")
    private String inspectWorkerId;
    /** 巡检区域 */
    @Excel(name = "巡检区域")
    private String inspectArea;
    /** 下发时间 */
    @Excel(name = "下发时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date distributeTime;
    /** 发布人员 */
    @Excel(name = "发布人员")
    private String distributeWorker;
    /** 发布人员id */
    @Excel(name = "发布人员id")
    private String distributeWorkerId;
    /** 巡检状态 */
    @Excel(name = "巡检状态", readConverterExp = "0=未巡,1=在巡,2=已巡")
    private String inspectStatus;
    /** 巡检类型id */
//    @Excel(name = "巡检类型id")
    private String inspectTypeId;
    /** 巡检类型 */
//    @Excel(name = "巡检类型")
    private String inspectType;
    @Excel(name = "巡检记录数量")
    private String inspectRecordNum;
    /** 开始时间 */
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /** 结束时间 */
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

}
