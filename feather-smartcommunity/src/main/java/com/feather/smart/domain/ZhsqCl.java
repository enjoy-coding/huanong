package com.feather.smart.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

/**
 * 车辆对象 ZHSQ_CL
 * 
 * @author fancy
 * @date 2020-05-15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZhsqCl extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 车辆id */
    private String clid;
    /** 车牌号码 */
    @Excel(name = "车牌号码")
    private String clhm;
    /** 车型(大型,小型) */
    @Excel(name = "车型(大型,小型)")
    private String cx;
    /** 车主姓名 */
    @Excel(name = "车主姓名")
    private String czxm;
    /** 车主联系电话 */
    @Excel(name = "车主联系电话")
    private String lxdh;
    /** 车主id */
    @Excel(name = "车主id")
    private String jmid;
}
