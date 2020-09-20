package com.feather.aupipes.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AupSearchMapper {
    public List<Map<String,Object>> queryEquipment(Map<String,Object> map);

    public List<String> getType(@Param("content") String content);

    /**
     * 按类型查询运行监控的信息
     * @param params
     * @return
     */
    public List<Map<String,Object>> appYxjkQueryInfoByType(Map<String,Object> params);

    /**
     * 按id查询运行监控详细信息
     * @param params
     * @return
     */
    public List<Map<String,Object>> appYxjkQueryDetailInfoByTypeId(Map<String,Object> params);

    public Map<String,Object> queryPumpDayUseWaterNumber(int pumpId);
 }
