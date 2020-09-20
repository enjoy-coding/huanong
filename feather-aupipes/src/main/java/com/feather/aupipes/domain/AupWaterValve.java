package com.feather.aupipes.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Create by NieCheng
 * Time   2020/9/10 10:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupWaterValve {

    private static final long serialVersionUID = 1L;

    private String vbCode;
    private String vbType;
    private String vbParent1;
    private String vbParent2;
    private String vbParent3;
    private String vbParent4;
    private String vbParent5;
    private String vbLevel;
    private String vbArea;
    private String vbAreaName;
    private String createBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String updateBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private String fmOid; //选择阀门时候的OID

    private String areaNo;
    private String areaName;
    private String buildingName;
    private String buildingType;

    private String valveOid;
    private String valveName;

}
