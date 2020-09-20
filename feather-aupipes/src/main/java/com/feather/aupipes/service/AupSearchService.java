package com.feather.aupipes.service;

import com.feather.common.json.JSONObject;

import java.util.List;
import java.util.Map;

public interface AupSearchService {

    Map<String,Object> queryEquipment(Map<String,Object> map);

    List<Map<String,Object>> queryStreetLight(Map<String,Object> map);

    List<Map<String,Object>> queryLeak(Map<String,Object> map);

    List<Map<String,Object>> queryCamera(Map<String,Object> map);

    List<Map<String,Object>> queryWaterTable(Map<String,Object> map);

    List<Map<String,Object>> queryElectricityTable(Map<String,Object> map);

    List<Map<String,Object>> queryWaterQuality(Map<String,Object> map);


    List<String> getType(String content);

    /**
     * 移动端运行监控类型查询
     * @param queryType 参数
     * @return List<Map<String,Object>>
     */
    List<Map<String,Object>> appYxjkQueryInfo(String queryType);

    /**
     * 按类型查询运行监控的信息
     * @param params 参数
     * @return List<Map<String,Object>>
     */
    List<Map<String,Object>> appYxjkQueryInfoByType(Map<String,Object> params);

    /**
     * 按id查询运行监控详细信息
     * @param params 参数
     * @return List<Map<String,Object>>
     */
    List<Map<String,Object>> appYxjkQueryDetailInfoByTypeId(Map<String,Object> params);

    Map<String,Object> queryPumpDayUseWaterNumber(int pumpId);

    JSONObject.JSONArray getInspectNum();

    JSONObject.JSONArray getInspectNumCurM();
}

