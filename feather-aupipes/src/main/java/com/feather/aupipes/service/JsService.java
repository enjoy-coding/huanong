package com.feather.aupipes.service;


import com.feather.aupipes.domain.query.Jsgxgx;
import com.feather.aupipes.domain.query.Jsgxhl;
import com.feather.aupipes.domain.query.Jspoint;
import com.feather.aupipes.domain.query.Point;

import java.util.List;

/**
 * @author tql
 * @description 给水管线点分析接口
 * @date 2020年3月25日15:09:36
 */
public interface JsService {

    List<Jsgxhl> getJsgxhl();

    List<Jsgxgx> getJsgxgx(List<Point> fm);

    /**
     * 根据阀门id查询上游阀门
     * @param fmid 阀门id
     * @return 结果
     */
    List<Jsgxhl> getSyfm(String fmid);

    /**
     * 根据阀门id查询下游阀门
     * @param fmid 阀门id
     * @return 结果
     */
    List<Jsgxhl> getXyfm(String fmid);

    /**
     * 根据阀门编号查询阀门对应详细信息
     * @param list 集合
     * @param type 类型
     * @return 结果
     */
    List<Jspoint> getValveInfo(List<Jsgxhl> list, String type);



}
