package com.feather.aupipes.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

/**
 * 决策保障管理员对象 aup_jcbz_manager
 * 
 * @author fancy
 * @date 2020-01-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupJcbzManager extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @Excel(name = "id")
    private Long id;
    /** 管理员名称 */
    @Excel(name = "管理员名称")
    private String managername;
    /** 管理员类型 */
    @Excel(name = "管理员类型")
    private String managetype;
    /** 管理一级单位 */
    @Excel(name = "管理一级单位")
    private String levfir;
    /** 管理二级单位 */
    @Excel(name = "管理二级单位")
    private String levsec;
    /** 管理三级单位 */
    @Excel(name = "管理三级单位")
    private String levthr;
    /** 展示编码 */
    @Excel(name = "展示编码")
    private String code;
    /** 父级编码 */
    @Excel(name = "父级编码")
    private String parentcode;
    /** 修改时间 */
    @Excel(name = "修改时间")
    private String edittime;
    /** 用户手机号 */
    @Excel(name = "手机号")
    private String phonenum;
    /** 用户微信号 */
    @Excel(name = "微信号")
    private String wxnum;

    //管理员唯一Id
    private Long managerUuid;

}
