package com.feather.aupipes.mapper;

import java.util.List;
import java.util.Map;

import com.feather.aupipes.domain.AupArea;

public interface AupAreaMapper {
    
    /**
     * 根据条件查询区域列表
     * @param aupArea 参数
     * @return 结果
     */
    List<AupArea> selectAupAreaList(AupArea aupArea);

    /**
     *  查询区域更新历史信息
     *  @param aupArea 参数
     *  @return 结果
     */
    List<Map<String,Object>> selectAupAreaUpdateHistoryList(AupArea area);

    /**
     * 查询水电表行政地区结构，直接查询能源大厅结构
     * @param parmas
     * @return
     */
    List<AupArea> queryMeterArea(AupArea aupArea);
    
    /**
     * 查询水电行政区域详细用能信息
     */
    List<Map<String,Object>> queryMeterInfo(Map<String,Object> parmas);

    AupArea selectAupAreaById(String id);
}