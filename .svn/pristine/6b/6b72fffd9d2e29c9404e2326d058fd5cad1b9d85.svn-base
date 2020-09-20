package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupArea;
import com.feather.common.core.domain.LayuiTreeStringResult;

import java.util.List;
import java.util.Map;


public interface AupAreaService {
    
     /**
     * 根据条件查询区域列表
     * @param aupArea 参数
     * @return 结果
     */
    List<AupArea> selectAupAreaList(AupArea aupArea);

    /**
     * 查询获取树的根节点
     */
    List<LayuiTreeStringResult> selectAupAreaLayuiResultList(AupArea aupArea);

    /**
     *  查询区域更新历史信息
     *  @param area 参数
     *  @return 结果
     */
    List<Map<String,Object>> selectAupAreaUpdateHistoryList(AupArea area);

     /**
     * 查询水电表行政地区结构，直接查询能源大厅结构
     * @param area 参数
     * @return List<AupArea>
     */
    List<AupArea> queryMeterArea(AupArea area);
    
    /**
     * 查询水电行政区域详细用能信息
     */
    List<Map<String,Object>> queryMeterInfo(Map<String,Object> parmas);

    AupArea selectAupAreaById(String id);

}