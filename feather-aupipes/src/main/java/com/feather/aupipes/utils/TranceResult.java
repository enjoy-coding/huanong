package com.feather.aupipes.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.feather.aupipes.config.ConfigPipe;
import com.feather.aupipes.config.QueryConfig;
import com.feather.aupipes.domain.query.Point;
import com.feather.common.json.JSONObject;
import com.feather.common.json.JSONObject.JSONArray;

/**
 * 功能描述：追溯分析 基于KQGIS 平台
 *
 * @Author: fang xinliang
 * @Date: 2019/12/24 13:35
 */
@Component
public class TranceResult {

    @Autowired
    private ConfigPipe configPipe;
    @Autowired
    private QueryConfig queryConfig;

    /**
     * @param queryFeatures
     * @param mode
     *            0 逆向分析 1 正向分析(向下分析，路径没有支路，还需特殊处理)
     * @return
     */
    public Map GetPath(String queryFeatures, String mode, double originX, double originY) {
        JSONObject objResults = JSONObject.parse(queryFeatures);
        String resultCode = objResults.getString("resultcode");
        if ("success".equalsIgnoreCase(resultCode) && !objResults.getJSONObject("result").isEmpty()) {

            JSONObject.JSONArray paths = objResults.getJSONObject("result").getJSONArray("paths");
            int arrRainSize = paths.size();

            List<String> startList = new ArrayList<String>();
            List<Point> listReault = new ArrayList<Point>();
            List<JSONObject> listPath = new ArrayList<JSONObject>();
            double tempx = 0;
            double tempy = 0;
            for (int i = 0; i < arrRainSize; i++) {
                JSONObject objfeature = paths.getJSONObject(i);
                JSONObject segments = objfeature.getJSONObject("segments");
                JSONObject.JSONArray features = segments.getJSONArray("features");
                // 正向分析,向下
                /*
                 * if (mode.equals("1")) { // 起点,下一个线段的终点 JSONArray coordinates
                 * = features.getJSONObject(features.size()-1).getJSONObject(
                 * "geometry").getJSONArray("coordinates");
                 * 
                 * double startX = coordinates.getJSONArray(0).getDouble(0);
                 * double startY = coordinates.getJSONArray(0).getDouble(1);
                 * startList.add(startX + "|" + startY); } else { // 记录终点 0
                 * JSONArray coordinates =
                 * features.getJSONObject(0).getJSONObject("geometry").
                 * getJSONArray("coordinates"); double endX =
                 * coordinates.getJSONArray(coordinates.size()-1).getDouble(0);
                 * double endY =
                 * coordinates.getJSONArray(coordinates.size()-1).getDouble(1);
                 * startList.add(endX + "|" + endY); }
                 */
                double ds = 0;
                for (int j = 0; j < features.size(); j++) {
                    JSONObject.JSONArray coordinates = features.getJSONObject(j).getJSONObject("geometry")
                            .getJSONArray("coordinates");

                    double startX = JSONObject.toJSONArray((Collection<Object>) coordinates.get(0)).getJSONdouble(1);
                    double startY = JSONObject.toJSONArray((Collection<Object>) coordinates.get(0)).getJSONdouble(1);
                    double endX = JSONObject.toJSONArray((Collection<Object>) coordinates.get(coordinates.size() - 1))
                            .getJSONdouble(0);
                    double endY = JSONObject.toJSONArray((Collection<Object>) coordinates.get(coordinates.size() - 1))
                            .getJSONdouble(1);

                    double ds1 = DistanceUtil.algorithm(startX, startY, originX, originY);
                    double ds2 = DistanceUtil.algorithm(endX, endY, originX, originY);
                    if (j == 0) {
                        ds = ds1;
                        startList = new ArrayList<>();
                        startList.add(startX + "|" + startY);
                    }
                    if (ds1 < ds) {
                        ds = ds1;
                        startList = new ArrayList<>();
                        startList.add(startX + "|" + startY);
                    }
                    if (ds2 < ds) {
                        ds = ds2;
                        startList = new ArrayList<>();
                        startList.add(endX + "|" + endY);
                    }

                }
                // System.out.println("开始点："+startList.get(0));
                startLoop(features, startList, listReault, listPath, mode);
            }
            Map result = new HashMap<>();
            result.put("points", listReault);
            result.put("paths", listPath);
            return result;
        }
        return null;
    }

    /**
     * 查找起始 结束点
     * 
     * @param queryFeatures
     * @param mode
     * @param originX
     * @param originY
     * @return
     */
    public Map GetPathStartNode(String queryFeatures, String mode, double originX, double originY) {
        JSONObject objResults = JSONObject.parse(queryFeatures);
        String resultCode = objResults.getString("resultcode");
        if ("success".equalsIgnoreCase(resultCode) && !objResults.getJSONObject("result").isEmpty()) {

            JSONObject.JSONArray paths = objResults.getJSONObject("result").getJSONArray("paths");
            int arrRainSize = paths.size();
            Map result = new HashMap<>();

            // 正向分析,向下
            double ds0 = 0;
            for (int i = 0; i < arrRainSize; i++) {
                JSONObject objfeature = paths.getJSONObject(i);
                JSONObject segments = objfeature.getJSONObject("segments");
                JSONObject.JSONArray features = segments.getJSONArray("features");
                // 第一条线，最后一条线
                for (int j = 0; j < features.size(); j++) {

                    JSONObject.JSONArray coordinates = features.getJSONObject(j).getJSONObject("geometry")
                            .getJSONArray("coordinates");
                    // 第一条线
                    double startX = JSONObject.toJSONArray((Collection<Object>) coordinates.get(0)).getJSONdouble(0);
                    double startY = JSONObject.toJSONArray((Collection<Object>) coordinates.get(0)).getJSONdouble(1);

                    double endX = JSONObject.toJSONArray((Collection<Object>) coordinates.get(coordinates.size() - 1))
                            .getJSONdouble(0);
                    double endY = JSONObject.toJSONArray((Collection<Object>) coordinates.get(coordinates.size() - 1))
                            .getJSONdouble(1);

                    double ds1 = DistanceUtil.algorithm(startX, startY, originX, originY);
                    double ds2 = DistanceUtil.algorithm(endX, endY, originX, originY);

                    if (i == 0 && j == 0) {
                        ds0 = ds1;
                        result.put("startX", startX);
                        result.put("startY", startY);
                    } else {

                        if (ds1 < ds0) {
                            ds0 = ds1;
                            result.put("startX", startX);
                            result.put("startY", startY);
                        }
                        if (ds2 < ds0) {
                            ds0 = ds2;
                            result.put("startX", endX);
                            result.put("startY", endY);
                        }
                    }

                }

                /*
                 * if (mode.equals("1")) { // 起点,下一个线段的终点 JSONArray coordinates
                 * = features.getJSONObject(features.size()-1).getJSONObject(
                 * "geometry").getJSONArray("coordinates");
                 * 
                 * startX = coordinates.getJSONArray(0).getDouble(0); startY =
                 * coordinates.getJSONArray(0).getDouble(1);
                 * 
                 * } else { // 记录终点 0 JSONArray coordinates =
                 * features.getJSONObject(0).getJSONObject("geometry").
                 * getJSONArray("coordinates"); startX =
                 * coordinates.getJSONArray(coordinates.size()-1).getDouble(0);
                 * startY =
                 * coordinates.getJSONArray(coordinates.size()-1).getDouble(1);
                 * 
                 * }
                 */
                break;
            }

            return result;
        }
        return null;
    }

    /**
     * 向下分析后 再向上分析
     * 
     * @param queryFeatures
     * @param mode
     * @return
     */
    /*
     * public Map GetPath_0(String queryFeatures, String mode,double x,double y)
     * { JSONObject objResults = JSONObject.parseObject(queryFeatures); String
     * resultCode = objResults.getString("resultcode"); if
     * ("success".equalsIgnoreCase(resultCode)&&!objResults.getJSONObject(
     * "result").isEmpty()) {
     * 
     * JSONArray paths =
     * objResults.getJSONObject("result").getJSONArray("paths"); int arrRainSize
     * = paths.size();
     * 
     * 
     * JSONObject objfeature = paths.getJSONObject(0); JSONObject segments =
     * objfeature.getJSONObject("segments"); JSONArray features =
     * segments.getJSONArray("features"); JSONArray coordinates =
     * features.getJSONObject(0).getJSONObject("geometry").getJSONArray(
     * "coordinates"); double endX =
     * coordinates.getJSONArray(coordinates.size()-1).getDouble(0); double endY
     * = coordinates.getJSONArray(coordinates.size()-1).getDouble(1);
     * List<String> startList = new ArrayList<String>();
     * startList.add(endX+","+endY); List<Point> listReault = new
     * ArrayList<Point>(); List<JSONObject> listPath = new
     * ArrayList<JSONObject>(); for (int i = 0; i < arrRainSize; i++) {
     * JSONObject objfeature1 = paths.getJSONObject(i); JSONObject segments1 =
     * objfeature.getJSONObject("segments"); JSONArray features1 =
     * segments.getJSONArray("features"); // 正向分析,向下 startLoop(features1,
     * startList, listReault,listPath, mode); } Map result =new HashMap<>();
     * result.put("points",listReault); result.put("paths",listPath); return
     * result; } return null; }
     * 
     */
    /**
     * 向下分析比较特殊，没有找支流，倒转过来 收尾点交换 * @param queryFeatures
     * 
     * @param mode
     * @return
     */
    public Map GetPathEndNode(String queryFeatures, String mode) {
        JSONObject objResults = JSONObject.parse(queryFeatures);
        String resultCode = objResults.getString("resultcode");
        if ("success".equalsIgnoreCase(resultCode) && !objResults.getJSONObject("result").isEmpty()) {

            JSONObject.JSONArray paths = objResults.getJSONObject("result").getJSONArray("paths");
            JSONObject objfeature = paths.getJSONObject(0);
            JSONObject segments = objfeature.getJSONObject("segments");
            JSONObject.JSONArray features = segments.getJSONArray("features");
            // 起点,下一个线段的终点
            JSONObject.JSONArray coordinates = features.getJSONObject(0).getJSONObject("geometry")
                    .getJSONArray("coordinates");
            // 变成障碍点
            // double startX = coordinates.getJSONArray(0).getDouble(0);
            // double startY = coordinates.getJSONArray(0).getDouble(1);
            // 结束点变成 起点
            // coordinates =
            // features.getJSONObject(features.size()-1).getJSONObject("geometry").getJSONArray("coordinates");
            double endX = JSONObject.toJSONArray((Collection<Object>) coordinates.get(coordinates.size() - 1))
                    .getJSONdouble(0);
            double endY = JSONObject.toJSONArray((Collection<Object>) coordinates.get(coordinates.size() - 1))
                    .getJSONdouble(1);

            Map result = new HashMap<>();

            result.put("endX", endX);
            result.put("endY", endY);
            return result;
        }
        return null;
    }

    /**
     * 递归依次检索下一步长
     * 
     * @param features
     * @param startList
     * @param listReault
     * @param mode
     */
    public void startLoop(JSONArray features, List<String> startList, List<Point> listReault, List<JSONObject> ListPath,
            String mode) {
        List<String> nextStartList = new ArrayList<String>();

        for (int j = 0; j < features.size(); j++) {
            // 不管方向，方向无法确定
            JSONObject.JSONArray coordinates = features.getJSONObject(j).getJSONObject("geometry")
                    .getJSONArray("coordinates");
            // 起点
            double startX = JSONObject.toJSONArray((Collection<Object>) coordinates.get(0)).getJSONdouble(0);
            double startY = JSONObject.toJSONArray((Collection<Object>) coordinates.get(0)).getJSONdouble(1);
            // 终点,
            double endX = JSONObject.toJSONArray((Collection<Object>) coordinates.get(coordinates.size() - 1))
                    .getJSONdouble(0);
            double endY = JSONObject.toJSONArray((Collection<Object>) coordinates.get(coordinates.size() - 1))
                    .getJSONdouble(1);
            // 出现特殊的一个点
            if (startX == endX && startY == endY) {
                nextStartList.add(startX + "|" + startY);
                features.remove(j);
                j--;
                if (j < 0) {
                    j = 0;
                }
                continue;
            }
            if (startList.indexOf(endX + "|" + endY) > -1) { // 与上一个节点相连
                List<Point> tempList = QueryPointByTrace(
                        features.getJSONObject(j).getJSONObject("geometry").toString());
                if (tempList != null && tempList.size() > 0) {
                    listReault.addAll(tempList);
                } else {
                    if (nextStartList.indexOf(startX + "|" + startY) == -1) {
                        nextStartList.add(startX + "|" + startY);
                    }
                }
                JSONObject clone = JSONObject.parse(features.getJSONObject(j).getJSONObject("geometry").toString());
                ListPath.add(clone);

            } else if (startList.indexOf(startX + "|" + startY) > -1) {
                List<Point> tempList = QueryPointByTrace(
                        features.getJSONObject(j).getJSONObject("geometry").toString());
                // JSONArray tempList =
                // QueryPointByTrace(features.getJSONObject(j).getJSONObject("geometry").toString());
                // 第一个节点
                if (tempList != null && tempList.size() > 0) {
                    listReault.addAll(tempList);

                } else {
                    if (nextStartList.indexOf(endX + "|" + endY) == -1) {
                        nextStartList.add(endX + "|" + endY);
                    }
                }
                JSONObject clone = JSONObject.parse(features.getJSONObject(j).getJSONObject("geometry").toString());
                ListPath.add(clone);
            }
            features.remove(j);
            j--;
            if (j < 0) {
                j = 0;
            }

        }

        if (nextStartList.size() > 0 && features.size() > 0) {
            startLoop(features, nextStartList, listReault, ListPath, mode);
        }
    }

    /**
     * 根据路径找点
     * 
     * @param geometry
     * @return
     */
    public List<Point> QueryPointByTrace(String geometry) {

        // 查下点
        List<Point> list = new ArrayList<Point>();
        // JSONArray list = new JSONArray();
        String query = configPipe.getJxdUrl();
        String queryParam = configPipe.getJxdParam() + geometry;
        String queryFeatures = FeatureServer.DoPost2GIS(query, queryParam);
        JSONObject objResults = JSONObject.parse(queryFeatures);
        String resultCode = objResults.getString("resultcode");
        if ("success".equalsIgnoreCase(resultCode) && !objResults.getJSONObject("result").isEmpty()) {
            JSONArray features = objResults.getJSONObject("result").getJSONArray("features");
            for (int i = 0; i < features.size(); i++) {
                JSONObject feature = features.getJSONObject(i);
                JSONObject properties = feature.getJSONObject("properties");
                String SUBSID = properties.getString("SUBSID");
                if ("阀门".equals(SUBSID) || "阀门井".equals(SUBSID)) {
                    Point point = new Point();
                    point.setExp_no(properties.getString("EXP_NO"));
                    point.setFeature(properties.getString("FEATURE"));
                    point.setGeometry(feature.getJSONObject("geometry").toString());
                    point.setSubsid(SUBSID);
                    if (properties.containsKey("FID")) {
                        point.setOid(properties.getInteger("FID")); // KQ数据库 OID
                                                                    // SHAPE FID
                                                                    // arcgis
                                                                    // OBJECTID
                    } else if (properties.containsKey("OID")) {
                        point.setOid(properties.getInteger("OID")); // KQ数据库 OID
                                                                    // SHAPE FID
                                                                    // arcgis
                                                                    // OBJECTID
                    } else if (properties.containsKey("OBJECTID")) {
                        point.setOid(properties.getInteger("OBJECTID")); // KQ数据库
                                                                         // OID
                                                                         // SHAPE
                                                                         // FID
                                                                         // arcgis
                                                                         // OBJECTID
                    }
                    list.add(point);
                    /*
                     * JSONObject obj = new JSONObject();
                     * obj.put("oid",properties.getString("OID"));
                     * obj.put("exp_no",properties.getString("EXP_NO"));
                     * obj.put("feature",properties.getString("FEATURE"));
                     * obj.put("subsid",SUBSID);
                     * obj.put("geometry",feature.getJSONObject("geometry"));
                     * list.add(obj);
                     */
                    break;// 一条线找到一个即跳出
                }
            }
        }
        return list;
    }

    /**
     * 根据oid 查询点信息
     * 
     * @param where
     * @return
     */
    public JSONArray QueryPointByOID(String where) {

        // 查下点
        JSONArray list = new JSONArray();
        String query = configPipe.getJsPointQuery();
        String queryParam = configPipe.getJsPointQueryParam() + where;
        String queryFeatures = FeatureServer.DoPost2GIS(query, queryParam);
        JSONObject objResults = JSONObject.parse(queryFeatures);
        String resultCode = objResults.getString("resultcode");
        if ("success".equalsIgnoreCase(resultCode) && !objResults.getJSONObject("result").isEmpty()) {
            JSONArray features = objResults.getJSONObject("result").getJSONArray("features");
            for (int i = 0; i < features.size(); i++) {
                JSONObject feature = features.getJSONObject(i);
                JSONObject properties = feature.getJSONObject("properties");
                String SUBSID = properties.getString("SUBSID");
                if ("阀门".equals(SUBSID) || "阀门井".equals(SUBSID)) {

                    // "oid":0,"exp_no":"JS032586","feature":"","subsid":"阀门井","x":0.0,"y":0.0,"geometry
                    /*
                     * JSONObject obj = new JSONObject();
                     * obj.put("oid",properties.getString("OID"));
                     * obj.put("exp_no",properties.getString("EXP_NO"));
                     * obj.put("feature",properties.getString("FEATURE"));
                     * obj.put("subsid",SUBSID);
                     * obj.put("geometry",feature.getJSONObject("geometry"));
                     */
                    list.add(feature);
                    /*
                     * Point point = new Point();
                     * point.setExp_no(properties.getString("EXP_NO"));
                     * point.setFeature(properties.getString("FEATURE"));
                     * point.setGeometry(feature.getJSONObject("geometry").
                     * toString()); point.setSubsid(SUBSID); list.add(point);
                     */

                }
            }
        }
        return list;
    }

    /**
     * 根据编号 查找线路
     * 
     * @param where
     * @return
     */
    public JSONArray QueryLintByOID(String where) {

        // 查下点

        JSONArray lines = new JSONArray();
        String query = configPipe.getJsLintQuery();
        String queryParam = configPipe.getJsLintQueryParam() + where;
        String queryFeatures = FeatureServer.DoPost2GIS(query, queryParam);
        JSONObject objResults = JSONObject.parse(queryFeatures);
        String resultCode = objResults.getString("resultcode");
        if ("success".equalsIgnoreCase(resultCode) && !objResults.getJSONObject("result").isEmpty()) {
            JSONArray features = objResults.getJSONObject("result").getJSONArray("features");
            /*
             * for (int i = 0; i < features.size(); i++) { JSONObject
             * coordinates =
             * features.getJSONObject(i).getJSONObject("geometry");
             * lines.add(coordinates); }
             */
            lines.addAll(features);
        }
        return lines;
    }

    /**
     * 空间查询， 根据 geojson 、where sql 查询
     * 
     * @param geometry
     * @return
     */
    public JSONArray doQuery(String layerName, String geometry, String whereStr) {
        List<Map<String, Object>> list = queryConfig.getTemplates();
        // System.out.println("图层个数："+list.size());
        for (Map map : list) {
            if (map.get("name").equals(layerName)) {
                StringBuilder sb = new StringBuilder();
                sb.append("layerId=" + map.get("layerId") + "&");
                sb.append("startindex=" + map.get("startindex") + "&");
                sb.append("reqcount=" + map.get("reqcount") + "&");
                sb.append("geoSRS=" + map.get("geoSRS") + "&");
                sb.append("outSRS=" + map.get("outSRS") + "&");
                sb.append("geometry=" + geometry + "&");
                sb.append("fields=" + map.get("fields") + "&");
                sb.append("where=" + whereStr + "&");
                sb.append("version=" + map.get("version"));

                String query = map.get("url").toString();
                String queryFeatures = FeatureServer.DoPost2GIS(query, sb.toString());

                JSONObject objResults = JSONObject.parse(queryFeatures);
                String resultCode = objResults.getString("resultcode");
                // System.out.println("分析图层返回状态："+resultCode);
                if ("success".equalsIgnoreCase(resultCode) && !objResults.getJSONObject("result").isEmpty()) {
                    JSONObject resultObject = objResults.getJSONObject("result");
                    JSONArray features = resultObject.getJSONArray("features");
                    return features;
                }
            }
        }
        return null;
    }
}
