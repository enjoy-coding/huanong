package com.feather.aupipes.domain.enums;

/**
 * 功能描述：管线埋设类型枚举
 *
 * @Author: fang xinliang
 * @Date: 2020/2/6 18:43
 */
public enum PipelineLayoutType {
    /// <summary>
    /// 直埋
    /// </summary>
    Layout_ZHIMAI ,

    /// <summary>
    /// 管埋
    /// </summary>
    Layout_GUANMAI ,

    /// <summary>
    /// 管块
    /// </summary>
    Layout_GUANKUAI ,

    /// <summary>
    /// 沟埋
    /// </summary>
    Layout_GOUMAI ,

    /// <summary>
    /// 架空
    /// </summary>
    Layout_JIAKONG ,

    /// <summary>
    /// 综合管沟
    /// </summary>
    Layout_ZONGHEGUANGOU ,

    /// <summary>
    /// 未知
    /// </summary>
    Layout_UNKNOWN ;
   /*
    /// <summary>
    /// 直埋
    /// </summary>
    Layout_ZHIMAI("Layout_ZHIMAI", 0),

    /// <summary>
    /// 管埋
    /// </summary>
    Layout_GUANMAI("Layout_GUANMAI", 1),

    /// <summary>
    /// 管块
    /// </summary>
    Layout_GUANKUAI("Layout_GUANKUAI", 2),

    /// <summary>
    /// 沟埋
    /// </summary>
    Layout_GOUMAI("Layout_GOUMAI", 3),

    /// <summary>
    /// 架空
    /// </summary>
    Layout_JIAKONG("Layout_JIAKONG", 4),

    /// <summary>
    /// 综合管沟
    /// </summary>
    Layout_ZONGHEGUANGOU("Layout_ZONGHEGUANGOU", 4),

    /// <summary>
    /// 未知
    /// </summary>
    Layout_UNKNOWN("Layout_UNKNOWN", 11);

    private final String layoutName;
    private final int code;

    PipelineLayoutType(String layoutName, int code) {
        this.layoutName = layoutName;
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public String getLayoutName() {
        return layoutName;
    }

    */
}
