package com.feather.aupipes.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

/**
 * 路灯控制对象 aup_streetlight_control
 *
 * @author fancy
 * @date 2019-12-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupStreetlightControl extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 名称 */
    @Excel(name = "名称")
    private String sname;
    /** 配电柜序号 */
    private String sid;
    /** 当前连接模式 */
    @Excel(name = "当前连接模式")
    private String modelid;
    /** 控制器uid */
    @Excel(name = "控制器uid")
    private String cuid;
    /** 集中器协议类型 */
    @Excel(name = "集中器协议类型")
    private String ctype;
    /** 当前状态(对应: RtuStatusType.XXXX) */
    @Excel(name = "当前状态(对应: RtuStatusType.XXXX)")
    private String status;
    /** 状态变化时间 */
    @Excel(name = "状态变化时间")
    private String stime;
    /** 是否已经完成参数同步 */
    @Excel(name = "是否已经完成参数同步")
    private String sync;
    /** SIM 卡号码 */
    @Excel(name = "SIM 卡号码")
    private String sim;
    /** 连接 */
    @Excel(name = "连接")
    private String conn;
    /** 信号强度 */
    @Excel(name = "信号强度")
    private String signals;
    /** 版本 */
    @Excel(name = "版本")
    private String version;
    /** 电表数量 */
    @Excel(name = "电表数量")
    private String ammeters;
    /** 开关数量 */
    @Excel(name = "开关数量")
    private String breakers;
    /** 柱子数量 */
    @Excel(name = "柱子数量")
    private String poles;
    /** 终端数量 */
    @Excel(name = "终端数量")
    private String lcus;
    /** 灯数 */
    @Excel(name = "灯数")
    private String lamps;
    /** 纬度 */
    @Excel(name = "纬度")
    private String lat;
    /** 经度 */
    @Excel(name = "经度")
    private String lng;
    /** 电表地址 */
    @Excel(name = "电表地址")
    private String madd;
    /** null */
    @Excel(name = "null")
    private String nums;

    @Excel(name = "整形id")
    private Long id;

    private String type ="streetlightControl";
}