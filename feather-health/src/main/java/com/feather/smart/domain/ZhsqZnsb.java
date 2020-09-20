package com.feather.smart.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

/**
 * smartcommunity对象 ZHSQ_ZNSB
 * 
 * @author fancy
 * @date 2020-09-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZhsqZnsb extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private String id;
    /** 串口 */
    @Excel(name = "串口")
    private String imei;
    /** 心率 */
    @Excel(name = "心率")
    private String heartrate;
    /** 低压 */
    @Excel(name = "低压")
    private String dbp;
    /** 高压 */
    @Excel(name = "高压")
    private String sdp;
    /** 血氧 */
    @Excel(name = "血氧")
    private String oxygen;
    /** 血糖 */
    @Excel(name = "血糖")
    private String bloodsugar;
    /** 温度 */
    @Excel(name = "温度")
    private String temperature;
    /** 纬度 */
    @Excel(name = "纬度")
    private String lat;
    /** 经度 */
    @Excel(name = "经度")
    private String lon;
    /** 类型，判断是定位还是血氧心率 */
    @Excel(name = "类型，判断是定位还是血氧心率")
    private String type;
    /** 创建日期 */
    @Excel(name = "创建日期")
    private String createdate;
    /** “1” sos 预警 “2” 跌倒预警 “3” 脱手预警 “4” 久坐预警 “5” 低电预警 “6” 家庭医生 “7” 房颤预警 */
    @Excel(name = "“1” sos 预警 “2” 跌倒预警 “3” 脱手预警 “4” 久坐预警 “5” 低电预警 “6” 家庭医生 “7” 房颤预警")
    private String warningtype;
    /** 电量 */
    @Excel(name = "电量")
    private String battery;
    /** sos预警地址 */
    @Excel(name = "sos预警地址")
    private String address;
    /**  */
    @Excel(name = "")
    private String longitude;
    /**  */
    @Excel(name = "")
    private String latitude;
    /** 计步数据 */
    @Excel(name = "计步数据")
    private String pedometer;
    /** 温度计电 */
    @Excel(name = "温度计电")
    private String jtmbattery;
    /**  */
    @Excel(name = "")
    private String audio;
    /**  */
    @Excel(name = "")
    private String bluetooth;
    /**  */
    @Excel(name = "")
    private String oriseq;

    private String content;
}
