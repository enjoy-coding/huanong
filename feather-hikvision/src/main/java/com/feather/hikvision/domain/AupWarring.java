package com.feather.hikvision.domain;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 预警记录信息对象 aup_warring
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupWarring extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    private String id;
    /** 类型编码 */
    @Excel(name = "类型编码")
    private String categorycode;
    /** 预警原因 */
    @Excel(name = "预警原因")
    private String author;
    /** 预警内容 */
    @Excel(name = "预警内容")
    private String content;
    /** 预警时间 */
    @Excel(name = "预警时间")
    private String datetime;
    /** PcCallbackUrl */
    @Excel(name = "PcCallbackUrl")
    private String pccallbackurl;
    /** MobileCallbackUrl */
    @Excel(name = "MobileCallbackUrl")
    private String mobilecallbackurl;
    /** ImageUrl1 */
    @Excel(name = "ImageUrl1")
    private String imageurl1;
    /** ImageUrl2 */
    @Excel(name = "ImageUrl2")
    private String imageurl2;
    /** ImageUrl3 */
    @Excel(name = "ImageUrl3")
    private String imageurl3;
    /** ImageUrl4 */
    @Excel(name = "ImageUrl4")
    private String imageurl4;
    /** ImageUrl5 */
    @Excel(name = "ImageUrl5")
    private String imageurl5;
    /** 预警状态 */
    @Excel(name = "预警状态")
    private String state;
    /** 经度 */
    @Excel(name = "经度")
    private String longitude;
    /** 维度 */
    @Excel(name = "维度")
    private String latitude;
    /** 预警名称 */
    @Excel(name = "预警名称")
    private String name;
    /** 位置 */
    @Excel(name = "位置")
    private String path;
    /** 设备编码 */
    @Excel(name = "设备编码")
    private String code;
    /** 预警等级 */
    @Excel(name = "预警等级")
    private String level;
    /** 发送类型 */
    @Excel(name = "发送类型")
    private String sendtype;
    /** 发送人id */
    @Excel(name = "发送人id")
    private String sendusers;
    /** NoReadSend */
    @Excel(name = "NoReadSend")
    private String noreadsend;
    /** Template */
    @Excel(name = "Template")
    private String template;
    /** 来源 */
    @Excel(name = "来源")
    private String formsysname;
    /** 发送人名称 */
    @Excel(name = "发送人名称")
    private String sendusernames;
}
