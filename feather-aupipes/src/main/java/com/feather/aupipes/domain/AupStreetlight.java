package com.feather.aupipes.domain;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 路灯对象 aup_streetlight
 *
 * @author fancy
 * @date 2019-12-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupStreetlight extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 灯id
     */
    private String lid;
    /**
     * 项目id
     */
    @Excel(name = "项目id")
    private String pid;
    /**
     * 所属卡
     */
    @Excel(name = "所属卡")
    private String sid;
    /**
     * 通讯网关 UID
     */
    @Excel(name = "通讯网关 UID")
    private String cuid;
    /**
     * 类型
     */
    @Excel(name = "类型")
    private String ctype;
    /**
     * 终端 UID
     */
    @Excel(name = "终端 UID")
    private String luid;
    /**
     * 终端
     */
    @Excel(name = "终端")
    private String lch;
    /**
     * 终端数量
     */
    @Excel(name = "终端数量")
    private String lchs;
    /**
     * null
     */
    @Excel(name = "null")
    private String name;
    /**
     * null
     */
    @Excel(name = "null")
    private String num;
    /**
     * 纬度
     */
    @Excel(name = "纬度")
    private String lat;
    /**
     * 经度
     */
    @Excel(name = "经度")
    private String lng;
    /**
     * 继电器状态
     */
    @Excel(name = "继电器状态")
    private String rs;
    /**
     * 灯具状态
     */
    @Excel(name = "灯具状态")
    private String ls;
    /**
     * 调光值
     */
    @Excel(name = "调光值")
    private String dim;
    /**
     * 调光调光等级
     */
    @Excel(name = "调光调光等级")
    private String dims;
    /**
     * 警报列表
     */
    @Excel(name = "警报列表")
    private String alarms;
    /**
     * 开关地址
     */
    @Excel(name = "开关地址")
    private String kadd;
    /**
     * 开关通道
     */
    @Excel(name = "开关通道")
    private String kch;
    /**
     * 太阳能灯具数量能
     */
    @Excel(name = "太阳能灯具数量能")
    private String solar;
    /**
     * 分组序号
     */
    @Excel(name = "分组序号")
    private String groupid;
    /**
     * 分组组号
     */
    @Excel(name = "分组组号")
    private String lgnum;

    @Excel(name = "整形id")
    private Long id;

    @Excel(name = "整形父id")
    private Long parentId;

    private String type = "streetlight";
    /**
     * 电压
     */
    private String u;
    /**
     * 电流
     */
    private String i;
    /**
     * 功率
     */
    private String p;
    /**
     * 功率因数
     */
    private String pf;
    /**
     * 电能
     */
    private String e;
    /**
     * 漏电压
     */
    private String lu;
    /**
     * 亮灯时长
     */
    private String life;
    /**
     * 所属控制器
     */
    private String control;

    public String getU() {
        if (u != null) {
            DecimalFormat format = new DecimalFormat("0.00");
            return format.format(new BigDecimal(u));
        } else {
            return u;
        }
    }

    public void setU(String u) {
        this.u = u;
    }

    public String getI() {
        if (i != null) {
            DecimalFormat format = new DecimalFormat("0.00");
            return format.format(new BigDecimal(i));
        } else {
            return i;
        }

    }

    public void setI(String i) {
        this.i = i;
    }

    public String getP() {
        if (p != null) {
            DecimalFormat format = new DecimalFormat("0.00");
            return format.format(new BigDecimal(p));
        } else {
            return p;
        }
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getPf() {
        return pf;
    }

    public void setPf(String pf) {
        this.pf = pf;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getLu() {
        return lu;
    }

    public void setLu(String lu) {
        this.lu = lu;
    }

    public String getLife() {
        return life;
    }

    public void setLife(String life) {
        this.life = life;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public AupStreetlight(String lid, String name, String num, String lat, String lng, String rs, String ls,
                          String dims, String kadd, String kch) {
        this.lid = lid;
        this.name = name;
        this.num = num;
        this.lat = lat;
        this.lng = lng;
        this.rs = rs;
        this.ls = ls;
        this.dims = dims;
        this.kadd = kadd;
        this.kch = kch;
    }

    public AupStreetlight(String cuid) {
        this.cuid = cuid;
    }
}
