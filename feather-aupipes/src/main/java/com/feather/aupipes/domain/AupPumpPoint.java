package com.feather.aupipes.domain;

import com.feather.common.annotation.Excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 泵房点位对象 aup_pump_point
 *
 * @author fancy
 * @date 2020-04-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupPumpPoint {

    private Long id;
    /** 对应泵房 */
    private Long pumpId;
    /** 入网ip */
    @Excel(name = "入网ip")
    private String ip;
    /** 位号 */
    @Excel(name = "位号")
    private String point;
    /**
     * 基础属性名称
     */
    private String attrName;
    /**
     * 基础属性值
     */
    private String attrValue;
    /**
     * 频率
     */
    private String pl;

    /**
     * 电压
     */
    private String dy;

    /**
     * 电流
     */
    private String dl;
    /**
     * 用电量
     */
    private String ydl;
    /** 变频 */
    @Excel(name = "变频")
    private String bp;
    /** 工频 */
    @Excel(name = "工频")
    private String gp;
    /** 泵数 */
    @Excel(name = "泵数")
    private Long punpcount;

    private String name;

    /**
     * 市政压力
     */
    private String szyl;

    /**
     * 泵名称
     */
    private String pointName;
}