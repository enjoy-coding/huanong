package com.feather.aupipes.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

/**
 * 控制器能源消耗对象 aup_control_energy
 * 
 * @author fancy
 * @date 2020-06-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupControlEnergy extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private String id;
    /** 控制器编号 */
    @Excel(name = "控制器编号")
    private String cuid;
    /** 年 */
    @Excel(name = "年")
    private String years;
    /** 月 */
    @Excel(name = "月")
    private String month;
    /** 日 */
    @Excel(name = "日")
    private String day;
    /** 时间 */
    @Excel(name = "时间")
    private String stime;
    /**  */
    @Excel(name = "")
    private String fullpower;
    /** 亮灯时长 */
    @Excel(name = "亮灯时长")
    private String lighttime;
    /**  */
    @Excel(name = "")
    private String ontime;
    /**  */
    @Excel(name = "")
    private String offtime;
    /**  */
    @Excel(name = "")
    private String ratedenergy;
    /** 当日使用电能 */
    @Excel(name = "当日使用电能")
    private String userdenengy;
    /** 关灯数 */
    @Excel(name = "关灯数")
    private String offcount;
    /** 亮灯数 */
    @Excel(name = "亮灯数")
    private String lightcount;
}
