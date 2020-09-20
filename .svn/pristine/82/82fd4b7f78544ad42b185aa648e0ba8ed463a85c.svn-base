package com.feather.lpscommunity.domain;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 社区服务对象 sc_service
 *
 * @author dufan
 * @date 2019-10-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScService extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 社区id */
    private Long serviceId;
    /** 社区id */
     @Excel(name = "部门ID")
    private Long serviceDept;

    /** 部门名称 */
    @Excel(name = "部门名称")
    private String serviceName;

    /** 服务类型 */
    @Excel(name = "服务类型")
    private String serviceType;


    /** 内容路径 */
    @Excel(name = "服务子类型")
    private String serviceCType;

    /** 服务内容 */
    @Excel(name = "服务内容")
    private String serviceContent;

    /** 内容路径 */
    @Excel(name = "内容路径")
    private String serviceFile;



    private String serviceControlType = "";

    public ScService(String serviceType) {
        this.serviceType = serviceType;
    }


    public ScService(String serviceType, String serviceCType) {
        this.serviceType = serviceType;
        this.serviceCType = serviceCType;
    }
}
