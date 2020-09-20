package com.feather.aupipes.service.impl;

import com.feather.aupipes.domain.AupInspect;
import com.feather.aupipes.domain.AupPatrolCount;
import com.feather.aupipes.mapper.AupInspectMapper;
import com.feather.aupipes.mapper.AupSearchMapper;
import com.feather.aupipes.service.AupSearchService;
import com.feather.common.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AupSearchServiceImpl implements AupSearchService {

    @Autowired
    private AupSearchMapper aupSearchMapper;

    @Autowired
    private AupInspectMapper aupInspectMapper;

    @Override
    public Map<String,Object> queryEquipment(Map<String,Object> map){
        Map<String,Object> maps = new HashMap<>();
        maps.put("水表",this.queryWaterTable(map));
        maps.put("电表",this.queryElectricityTable(map));
        maps.put("水质",this.queryWaterQuality(map));
        maps.put("路灯",this.queryStreetLight(map));
        maps.put("探漏",this.queryLeak(map));
        maps.put("监控",this.queryCamera(map));
        return maps;
    }

    @Override
    public List<Map<String, Object>> queryStreetLight(Map<String, Object> map)
    {
        map.put("type",4);
        return aupSearchMapper.queryEquipment(map);
    }


    @Override
    public List<Map<String, Object>> queryLeak(Map<String, Object> map) {
        map.put("type",5);
        return aupSearchMapper.queryEquipment(map);
    }

    @Override
    public List<Map<String, Object>> queryCamera(Map<String, Object> map) {
        map.put("type",6);
        return aupSearchMapper.queryEquipment(map);
    }

    @Override
    public List<Map<String, Object>> queryWaterTable(Map<String, Object> map) {
        map.put("type",1);
        return aupSearchMapper.queryEquipment(map);
    }

    @Override
    public List<Map<String, Object>> queryElectricityTable(Map<String, Object> map) {
        map.put("type",2);
        return aupSearchMapper.queryEquipment(map);
    }

    @Override
    public List<Map<String, Object>> queryWaterQuality(Map<String, Object> map) {
        map.put("type",3);
        return aupSearchMapper.queryEquipment(map);
    }

    @Override
    public List<String> getType(String content) {
        return aupSearchMapper.getType(content);
    }

    @Override
    public List<Map<String, Object>> appYxjkQueryInfoByType(Map<String, Object> params) {
        return aupSearchMapper.appYxjkQueryInfoByType(params);
    }

    @Override
    public List<Map<String, Object>> appYxjkQueryDetailInfoByTypeId(Map<String, Object> params) {
        return aupSearchMapper.appYxjkQueryDetailInfoByTypeId(params);
    }

    @Override
    public List<Map<String,Object>> appYxjkQueryInfo(String queryType){
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("queryType",queryType);
        List<Map<String,Object>> baseInfoList = this.appYxjkQueryInfoByType(params);
        if("pump".equals(queryType)){
            for (Map<String,Object> m:baseInfoList) {
                String typeId = m.get("id").toString();
                Map<String,Object> paramsDetail = new HashMap<String,Object>();
                paramsDetail.put("queryType",queryType);
                paramsDetail.put("typeId",typeId);
                List<Map<String,Object>> pumpDetailInfoList = this.appYxjkQueryDetailInfoByTypeId(paramsDetail);
                List<String> pointIds = new ArrayList<>();
                List<String> pointNames = new ArrayList<>();
                for (Map<String,Object> d: pumpDetailInfoList) {
                    String pointId = d.get("pointId").toString();
                    if (!pointIds.contains(pointId)) {
                        pointIds.add(pointId);
                        pointNames.add(d.get("pointName").toString());
                    }
                }
                Map<String,Object> pointMap = new HashMap<>();
                for (int i =0;i<pointIds.size();i++) {
                    List<Map<String,Object>> pointsList = new ArrayList<>();
                    for (Map<String,Object> d: pumpDetailInfoList) {
                        if(pointIds.get(i).equals(d.get("pointId").toString())){
                            pointsList.add(d);

                        }
                    }
                    pointMap.put(pointNames.get(i),pointsList);

                }
                m.put("points",pointMap);
            }

        }
        return baseInfoList;

    }

    @Override
    public Map<String,Object> queryPumpDayUseWaterNumber(int pumpId){
        Map<String,Object> maps = aupSearchMapper.queryPumpDayUseWaterNumber(pumpId);
        if(maps==null){
            maps = new HashMap<>();
            maps.put("thisUseNumber",0);
            maps.put("thisUseNumber1",0);
            maps.put("thisUseNumber2",0);

        }
        return maps;
    }

    @Override
    public JSONObject.JSONArray getInspectNum() {
        List<AupPatrolCount> list = aupInspectMapper.selectAupInspectCount(new AupInspect());
        Integer curMonthCount = aupInspectMapper.selectCurMonthCount();
        JSONObject.JSONArray jsonArray = new JSONObject.JSONArray();
        for (AupPatrolCount aupPatrolCount : list) {
            JSONObject jsonObject = new JSONObject();

            if (aupPatrolCount.getStatus().equals("2")) {// 已巡
                jsonObject.put("name", "已巡");
                jsonObject.put("value", aupPatrolCount.getTotal());
            } else if (aupPatrolCount.getStatus().equals("1")) {
                jsonObject.put("name", "在巡");
                jsonObject.put("value", aupPatrolCount.getTotal());
            } else if (aupPatrolCount.getStatus().equals("0")) {
                jsonObject.put("name", "未巡");
                jsonObject.put("value", aupPatrolCount.getTotal());
            }

            jsonArray.add(jsonObject);
        }

        if(curMonthCount != null){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", "当月");
            jsonObject.put("value", curMonthCount);
            jsonArray.add(jsonObject);
        }

        if (jsonArray.size() == 0) {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("name", "已巡");
            jsonObject1.put("value", 0);
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("name", "在巡");
            jsonObject2.put("value", 0);
            JSONObject jsonObject3 = new JSONObject();
            jsonObject3.put("name", "未巡");
            jsonObject3.put("value", 0);
            JSONObject jsonObject4 = new JSONObject();
            jsonObject4.put("name", "当月");
            jsonObject4.put("value", 0);
            jsonArray.add(jsonObject1);
            jsonArray.add(jsonObject2);
            jsonArray.add(jsonObject3);
            jsonArray.add(jsonObject4);
        }

        return jsonArray;
    }

    @Override
    public JSONObject.JSONArray getInspectNumCurM() {
        //当月任务数 所有待巡检和巡检中的，加上这个月已完成的
        List<AupPatrolCount> list = aupInspectMapper.selectAupInspectCount(new AupInspect());
        Integer alreadyCount = aupInspectMapper.selectAlreadyCurMonthCount();
        Integer total = aupInspectMapper.selectTotal();
        JSONObject.JSONArray jsonArray = new JSONObject.JSONArray();


        String wx = "0";
        String zx = "0";
        String yx = "0";
        for (AupPatrolCount aupPatrolCount : list) {

            if(aupPatrolCount.getStatus().equals("1")) {
                zx = aupPatrolCount.getTotal();
            } else if(aupPatrolCount.getStatus().equals("0")) {
                wx = aupPatrolCount.getTotal();
            }
        }

        if(alreadyCount != null){
            yx = alreadyCount.toString();
        }

        Integer curMonthCount = Integer.parseInt(wx) + Integer.parseInt(zx) + Integer.parseInt(yx);

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("name", "已巡");
        jsonObject1.put("value", yx);
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("name", "在巡");
        jsonObject2.put("value", zx);
        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("name", "未巡");
        jsonObject3.put("value", wx);
        JSONObject jsonObject4 = new JSONObject();
        jsonObject4.put("name", "当月");
        jsonObject4.put("value", curMonthCount);
        JSONObject jsonObject5 = new JSONObject();
        jsonObject5.put("name", "总数");
        jsonObject5.put("value", total);
        jsonArray.add(jsonObject1);
        jsonArray.add(jsonObject2);
        jsonArray.add(jsonObject3);
        jsonArray.add(jsonObject4);
        jsonArray.add(jsonObject5);


        return jsonArray;
    }
}
