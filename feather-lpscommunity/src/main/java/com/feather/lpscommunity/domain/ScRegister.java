package com.feather.lpscommunity.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 志愿者注册对象 sc_register
 *
 * @author fancy
 * @date 2019-11-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScRegister extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** null */
    private Long registerId;
    /** 姓名 */
    @Excel(name = "姓名")
    private String registerName;
    /** 电话 */
    @Excel(name = "电话")
    private String registerTel;
    /** 性别 */
    @Excel(name = "性别")
    private String registerSex;
    /** 出生日期 */
    @Excel(name = "出生日期")
    private String registerBir;
    @Excel(name = "设备id")
    private String registerIDCard;
    /** 地址 */
    @Excel(name = "地址")
    private String registerAddress;
    /** 职业 */
    @Excel(name = "职业")
    private String registerProfession;
    /** 个人简介 */
    @Excel(name = "个人简介")
    private String registerSynopsis;
    /** 地址 */
    @Excel(name = "设备id")
    private String equipmentId;

    /** 审核状态 */
    @Excel(name = "审核状态")
    private String auditState;
    /** 审核通过状态 */
    @Excel(name = "审核通过状态")
    private String auditPassState;
    /** 审核内容 */
    @Excel(name = "审核内容")
    private String auditContent;
    /** 审核人 */
    @Excel(name = "审核人")
    private String auditBy;
    /** 审核时间 */
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    private String headsculpture;// 头像，方便查询


    public ScRegister(Long registerId, String registerName, String registerTel, String registerSex, String registerBir,
            String registerIDCard, String registerAddress, String registerProfession, String registerSynopsis,
            String equipmentId, String auditState, String auditPassState) {
        this.registerId = registerId;
        this.registerName = registerName;
        this.registerTel = registerTel;
        this.registerSex = registerSex;
        this.registerBir = registerBir;
        this.registerIDCard = registerIDCard;
        this.registerAddress = registerAddress;
        this.registerProfession = registerProfession;
        this.registerSynopsis = registerSynopsis;
        this.equipmentId = equipmentId;
        this.auditState = auditState;
        this.auditPassState = auditPassState;
    }

    public ScRegister(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public ScRegister(String equipmentId, String auditState, String auditPassState) {
        this.equipmentId = equipmentId;
        this.auditState = auditState;
        this.auditPassState = auditPassState;
    }
}