package com.feather.aupipes.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 回路 属性 Create by NieCheng Time 2020/3/25 17:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupHl {

    private String id;

    private String 回路id;

    private String 配电房id;

    private String 变压器id;

    private String 变压器名称;

    private String 柜号;

    private String 回路名称;

    private String 开关模数;

    private String 空开型号;

    private String 变比;

    private String 电缆线径;

    private String 额定载流量;

    private String 使用值;

    private String 使用率;

    private String 备注;

}
