package com.feather.aupipes.domain;

import java.util.Date;

import com.feather.common.annotation.Excel;
import com.feather.common.utils.DateUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AupStreetlightDetail {

    @Excel(name = "名称")
    private String name;
    /** 控制器终端id */
    private String cuid;
    /** 集中器协议类型 */
    private String ctype;
    /** 灯终端id */
    @Excel(name = "灯终端id")
    private String luid;
    /** 数量 */
    private String num;
    /** 继电器状态 */
    private String rs;
    /** $column.columnComment */
    private Long rtime;
    /** 操作来源 */
    private String src;
    /** 灯具状态 */
    private String ls;
    /** 灯具状态更新时间 */

    private Long ltime;
    /** 灯头数 */

    private String dim;
    /** 调光值 */
    private String alarms;
    /** 电压 */
    @Excel(name = "电压")
    private double u;
    /** 电流 */
    @Excel(name = "电流")
    private double i;
    /** 功率 */
    @Excel(name = "功率")
    private String p;
    /** 功率因数 */
    @Excel(name = "功率因数")
    private String pf;
    /** 电能 */
    private String e;
    /** 漏电压 */

    private String lu;
    /** 亮灯时长(上行单位:秒) */
    @Excel(name = "亮灯时长(上行单位:秒)")
    private Long life;
    /** 数据采集时间 */

    private Long dtime;

    @Excel(name = "数据采集时间")
    private String d_time;
    /** $column.columnComment */
    private String rp;
    /** $column.columnComment */
    private String ri;
    /** 主键 */
    private Long id;

    private Long startTime;
    private Long endTime;
    @Excel(name = "电量")
    private double power;

    // private String states;

    private AupStreetlight aupStreetlight;

    public AupStreetlightDetail(String luid) {
        this.luid = luid;
    }

    public String time;

    public String getTime() {
        if (this.getLtime() != null) {
            return DateUtils.parseDateToStr("yyyy-MM-dd hh:ss:mm", new Date(this.getLtime()));
        }
        return null;
    }

    public double getPower() {
        if (this.getU() != 0 && this.getI() != 0 && this.getLife() != null) {
            double power = (this.getI() * this.getLife() * this.getU()) / 1000;
            return power;
        }
        return 0;
    }

    public String getStates() {
        if (this.getRs() != null) {
            return this.getRs().equals("2") ? "灭灯" : "亮灯";
        }
        return "";
    }
}
