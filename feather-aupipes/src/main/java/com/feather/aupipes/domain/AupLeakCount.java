package com.feather.aupipes.domain;

import com.feather.common.annotation.Excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 探漏运行统计次数对象 aup_leak_count
 *
 * @author fancy
 * @date 2020-01-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupLeakCount {

    /**
     * 正常点的数量
     */
    @Excel(name = "正常点的数量")
    private int normal;
    /**
     * 漏水点的数量
     */
    @Excel(name = "漏水点的数量")
    private int leakage;
    /**
     * 丢失点的数量
     */
    @Excel(name = "丢失点的数量")
    private int lose;
    /**
     * 干扰点的数量
     */
    @Excel(name = "干扰点的数量")
    private int lnterfere;
    /**
     * 疑漏点的数量
     */
    @Excel(name = "疑漏点的数量")
    private int doubtleak;

}
