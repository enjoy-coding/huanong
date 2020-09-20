package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupStreetlightControl;
import com.feather.common.core.domain.Ztree;

import java.util.List;
import java.util.Map;

/**
 * 路灯控制Service接口
 *
 * @author fancy
 * @date 2019-12-13
 */
public interface IAupStreetlightControlService
{
    /**
     * 查询路灯控制
     *
     * @param sid 路灯控制ID
     * @return 路灯控制
     */
    AupStreetlightControl selectAupStreetlightControlById(String sid);

    /**
     * 查询路灯控制列表
     *
     * @param aupStreetlightControl 路灯控制
     * @return 路灯控制集合
     */
    List<AupStreetlightControl> selectAupStreetlightControlList(AupStreetlightControl aupStreetlightControl);

    List<Ztree> selectAupStreetlightTree(String pid);
    List<Ztree> selectAupStreetlightTree();

    

    /**
     * 获取离线数
     * @param pid
     * @return
     */
    List<Ztree> selectAupStreetlightTreeUnline(String pid);

    /**
     * 获取所有离线的路灯
     * @return
     */
    List<Map<String,Object>> queryStreetlightTimeStatus();
}