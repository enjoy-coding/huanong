package com.feather.aupipes.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupBuildingWaterValve implements Serializable {
    private static final long serialVersionUID = 1L;

    private String buildingNo;
    private String valveNo;
    private String createBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String areaNo;
    private String areaName;
    private String buildingName;
    private String buildingType;

    private String valveOid;
    private String valveName;
}
