/*
 * @Description: 
 * @Version: 1.0
 * @Autor: dufna
 * @Date: 2020-06-24 10:24:25
 * @LastEditors: dufan
 * @LastEditTime: 2020-07-08 09:49:58
 */ 
package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupStreetlight;
import com.feather.aupipes.domain.LayuiTree;

import java.util.List;
import java.util.Map;

/**
 * 路灯Service接口
 *
 * @author fancy
 * @date 2019-12-13
 */
public interface IAupStreetlightService {
    /**
     * 主页路灯情况
     * @return
     */
    Map<String,Object> getIndexDetail();

    /**
     * 查询路灯
     *
     * @param lid 路灯ID
     * @return 路灯
     */
    AupStreetlight selectAupStreetlightById(String lid);

    /**
     * 查询路灯
     *
     * @param luid 路灯ID
     * @return 路灯
     */
    AupStreetlight selectAupStreetlightByIuid(String luid);


    /**
     * 查询路灯列表
     *
     * @param aupStreetlight 路灯
     * @return 路灯集合
     */
    List<AupStreetlight> selectAupStreetlightList(AupStreetlight aupStreetlight);

    void updateLightTime(String offtime,  String ontime);

    Map getLightTime();

     /**
     * 根据父节点查询路灯或者路灯控制器
     */
    List<Map<String,Object>> queryStreetlightControlTree(String pid);

    Map<String,Object> getStreetLightCount();

}
