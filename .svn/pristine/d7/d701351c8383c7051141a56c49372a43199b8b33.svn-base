package com.feather.lpscommunity.domain;

import java.util.Date;
import java.util.List;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 租用信息发布
 *
 * @author dufan
 * @date 2019-10-15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("返回类")
public class ScParkrent extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** null */
    private Long parkrentId;
    /** 负责人 */
    @Excel(name = "负责人")
    @ApiModelProperty(value = "负责人")
    private String parkrentName;
    /** 标题 */
    @ApiModelProperty(value = "标题")
    @Excel(name = "标题")
    private String parkrentTitle;
    /** 类型 */
    @Excel(name = "类型")
    @ApiModelProperty(value = "类型")
    private String parkrentType;
    /** 联系电话 */
    @Excel(name = "联系电话")
    @ApiModelProperty(value = "联系电话")
    private String parkrentTel;
    /** 内容 */
    @Excel(name = "内容")
    @ApiModelProperty(value = "内容")
    private String parkrentContent;
    /** 文件路径 */
    @Excel(name = "文件路径")
    @ApiModelProperty(value = "文件路径")
    private String parkrentFile;
    /** 状态 */
    @Excel(name = "状态")
    @ApiModelProperty(value = "状态")
    private String parkrentStatus;
    
    /** 设备id */
    @Excel(name = "设备id")
    @ApiModelProperty(value = "设备id")
    private String equipmentId;
    /** 分数 */
    @Excel(name = "分数")
    @ApiModelProperty(value = "分数")
    private double parkrentScore;
    /** 任务开始时间 */
    @Excel(name = "任务开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date beginTime;
    /** 任务结束时间 */
    @Excel(name = "任务结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;
    /** 审核状态 */
    @Excel(name = "审核状态")
    private String auditState;
    /** 审核通过状态 */
    @Excel(name = "审核通过状态")
    private String auditPassState;
    /** 审核内容 */
    @Excel(name = "审核内容")
    private String auditContent;
    /** 审核时间 */
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date auditTime;
    /** 审核人 */
    @Excel(name = "审核人")
    private String auditBy;

    /**
     * 删除状态
     */
    private String delFlag;
    
    /**
     * 照片集合
     */
    private List<ScFileInfo> files;

    


    private String parkentAuditState;

    public ScParkrent(String parkrentType, String parkrentStatus, String parkentAuditState) {
        this.parkrentType = parkrentType;
        this.parkrentStatus = parkrentStatus;
        this.parkentAuditState = parkentAuditState;
    }

    public ScParkrent(Long parkrentId, String parkrentName, String parkrentTitle, String parkrentType,
            String parkrentTel, String parkrentContent, String parkrentStatus, String parkentAuditState) {
        this.parkrentId = parkrentId;
        this.parkrentName = parkrentName;
        this.parkrentTitle = parkrentTitle;
        this.parkrentType = parkrentType;
        this.parkrentTel = parkrentTel;
        this.parkrentContent = parkrentContent;
        this.parkrentStatus = parkrentStatus;
        this.parkentAuditState = parkentAuditState;
    }

    public ScParkrent(Long parkrentId, String parkrentName, String parkrentTitle, String parkrentType,
                      String parkrentTel, String parkrentContent, String parkrentStatus, String parkentAuditState,String equipmentId) {
        this.parkrentId = parkrentId;
        this.parkrentName = parkrentName;
        this.parkrentTitle = parkrentTitle;
        this.parkrentType = parkrentType;
        this.parkrentTel = parkrentTel;
        this.parkrentContent = parkrentContent;
        this.parkrentStatus = parkrentStatus;
        this.parkentAuditState = parkentAuditState;
        this.equipmentId = equipmentId;
    }


    public ScParkrent(Long parkrentId) {
        this.parkrentId = parkrentId;
    }

    public ScParkrent(String parkrentType, String equipmentId) {
        this.parkrentType = parkrentType;
        this.equipmentId = equipmentId;
    }

    public ScParkrent(String parkrentType, String parkrentStatus, String equipmentId, String parkentAuditState) {
        this.parkrentType = parkrentType;
        this.parkrentStatus = parkrentStatus;
        this.equipmentId = equipmentId;
        this.parkentAuditState = parkentAuditState;
    }


    
    
    
}
