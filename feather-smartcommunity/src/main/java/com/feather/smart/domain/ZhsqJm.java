package com.feather.smart.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

/**
 * 居民对象 ZHSQ_JM
 *
 * @author fancy
 * @date 2020-05-15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZhsqJm extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 居民id */
    @Excel(name = "居民id")
    private String jmid;
    /** 姓名 */
    @Excel(name = "姓名")
    private String xm;
    /** 性别 */
    @Excel(name = "性别")
    private String xb;
    /** 电话 */
    @Excel(name = "电话")
    private String dh;
    /** 年龄 */
    @Excel(name = "年龄")
    private String nl;
    /** 籍贯 */
    @Excel(name = "籍贯")
    private String jg;
    /** 户籍性质 */
    @Excel(name = "户籍性质")
    private String hjxz;
    /** 民族 */
    @Excel(name = "民族")
    private String mz;
    /** 政治面貌 */
    @Excel(name = "政治面貌")
    private String zzmm;
    /** 学历 */
    @Excel(name = "学历")
    private String xl;
    /** 是否租户(是,否) */
    @Excel(name = "是否租户(是,否)")
    private String sfzh;
    /** 是否房主(是,否) */
    @Excel(name = "是否房主(是,否)")
    private String sffz;
    /** 房屋id */
    @Excel(name = "房屋id")
    private String fwid;
    /** 楼栋id */
    @Excel(name = "楼栋id")
    private String ldid;
    /** 住址 */
    @Excel(name = "住址")
    private String zz;
    /** 是否独居老人(是,否) */
    @Excel(name = "是否独居老人(是,否)")
    private String sfdj;
    /** 是否空巢老人(是,否) */
    @Excel(name = "是否空巢老人(是,否)")
    private String sfkc;
    /** 是否刑满释放(是,否) */
    @Excel(name = "是否刑满释放(是,否)")
    private String sfxmsf;
    /** 是否退役军人(是,否) */
    @Excel(name = "是否退役军人(是,否)")
    private String sftyjr;
    /** 是否低保(是,否) */
    @Excel(name = "是否低保(是,否)")
    private String sfdb;
    /** 是否残疾人(是,否) */
    @Excel(name = "是否残疾人(是,否)")
    private String sfcj;
}