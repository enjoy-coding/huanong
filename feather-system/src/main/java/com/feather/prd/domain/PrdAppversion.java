package com.feather.prd.domain;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * App版本对象 prd_app_version
 * 
 * @author flogyin
 * @date 2019-09-25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrdAppversion extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** null */
    private Long versionId;

    /** 版本号 */
    @Excel(name = "版本号")
    private Float versionNumber;

    /** 系统类型 */
    @Excel(name = "系统类型")
    private String versionOs;

    /** 包名 */
    @Excel(name = "包名")
    private String versionPackage;

    private Long appFile;
    private Long iconFile;
    private String iconImagePath;

    /** 特性 */
    @Excel(name = "特性")
    private String versionSpecial;

    /** 名称 */
    @Excel(name = "名称")
    private String versionName;

    private PrdAttachment appAttachment;
    private PrdAttachment iconAttachment;
}