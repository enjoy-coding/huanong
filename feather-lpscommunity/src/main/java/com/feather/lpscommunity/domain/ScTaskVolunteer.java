package com.feather.lpscommunity.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 志愿者任务表对象 sc_task_volunteer
 * 
 * @author fancy
 * @date 2019-11-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScTaskVolunteer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** null */
    private Long tvId;
    /** 任务id */
    @Excel(name = "任务id")
    private Long taskId;
    /** 志愿者id */
    @Excel(name = "志愿者id")
    private Long volunteerId;
    /** 审核状态 */
    @Excel(name = "审核状态")
    private String auditState;
    /** 审核是否通过 */
    @Excel(name = "审核是否通过")
    private String auditPassState;
    
    /** 审核是否通过 */
    @Excel(name = "审核内容")
    private String auditContent;

    /** 审核人 */
    @Excel(name = "审核人")
    private String auditBy;
    /** 审核时间 */
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date auditTime;

    /**
     * 任务
     */
    private ScParkrent parkrent;

    /**
     * 志愿者们
     */
    private ScVolunteer volunteer;

    public ScTaskVolunteer(Long tvId, Long taskId, Long volunteerId, String auditState, String auditPassState,
            Date auditTime) {
        this.tvId = tvId;
        this.taskId = taskId;
        this.volunteerId = volunteerId;
        this.auditState = auditState;
        this.auditPassState = auditPassState;
        this.auditTime = auditTime;
    }

    public ScTaskVolunteer(Long tvId, Long taskId, Long volunteerId, String auditState) {
        this.tvId = tvId;
        this.taskId = taskId;
        this.volunteerId = volunteerId;
        this.auditState = auditState;
    }

    public ScTaskVolunteer(Long volunteerId, String auditState, String auditPassState) {
        this.volunteerId = volunteerId;
        this.auditState = auditState;
        this.auditPassState = auditPassState;
    }

    public ScTaskVolunteer(Long parkrentId) {
        this.taskId = parkrentId;
    }
    
    
}