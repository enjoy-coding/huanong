package com.feather.website.analysis;

import org.nutz.lang.util.NutMap;

public interface IModelAnalysis {
    /**
     * 列表页
     *
     * @param params
     * @return
     */
    String list(NutMap params);

    /**
     * 列表页另一种可能主要针对因为红版有的栏目列表有另外一种可能的情况
     *
     * @param params
     * @return
     */
    String list1(NutMap params);

    /**
     * 轮播图1
     *
     * @param params
     * @return
     */
    String carousel1(NutMap params);

    /**
     * 轮播图2
     *
     * @param params
     * @return
     */
    String carousel2(NutMap params);

    /**
     * logo
     *
     * @param params
     * @return
     */
    String logo(NutMap params);

    /**
     * 导航
     *
     * @param params
     * @return
     */
    String nav(NutMap params);

    /**
     * 底部
     *
     * @param params
     * @return
     */
    String footerInfo(NutMap params);
}
