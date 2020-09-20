package com.feather.aupipes.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 预警信息详情对象 aup_yjinfotables
 * 
 * @author fancy
 * @date 2020-01-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupYjinfotables extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 预警详情id */
    @Excel(name = "预警详情id")
    private String itid;
    /** 预警统计设备id */
    @Excel(name = "预警统计设备id")
    private String tid;
    /** 地址编号 */
    @Excel(name = "地址编号")
    private String areano;
    /** 预警名称 */
    @Excel(name = "预警名称")
    private String name;
    /** 预警信息 */
    @Excel(name = "预警信息")
    private String content;
    /** 设备名称 */
    @Excel(name = "设备名称")
    private String sname;
    /** 设备位置 */
    @Excel(name = "设备位置")
    private String sarea;
    /** 设备类型 */
    @Excel(name = "设备类型")
    private String stype;
    /** 设备id */
    @Excel(name = "设备id")
    private String sid;
    /** 预警名称 */
    @Excel(name = "预警名称")
    private String yjname;
    /** 预警时间 */
    @Excel(name = "预警时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date yjtime;
    /** 预警状态 */
    @Excel(name = "预警状态")
    private String yjstatus;
    /** 预警状态 */
    @Excel(name = "预警状态")
    private String itstatus;
    /** 预警等级 */
    @Excel(name = "预警等级")
    private String yjlevel;
    /** 预警来源 */
    @Excel(name = "预警来源")
    private String yori;
    /** 灯头 */
    @Excel(name = "灯头")
    private String light;
    /** 电流 */
    @Excel(name = "电流")
    private String a;
    /** 电压 */
    @Excel(name = "电压")
    private String v;
    /** 功率 */
    @Excel(name = "功率")
    private String w;
    /** 功率因素 */
    @Excel(name = "功率因素")
    private String pw;
    /** 电流 */
    @Excel(name = "电流")
    private String pl;
    /** 压力值 */
    @Excel(name = "压力值")
    private String presure;
}
