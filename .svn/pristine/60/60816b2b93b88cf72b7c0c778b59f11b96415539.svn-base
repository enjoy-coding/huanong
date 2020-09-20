package com.feather.aupipes.service;


import com.feather.aupipes.domain.custom.Coord;
import com.feather.aupipes.domain.custom.CustomPoint;
import com.feather.aupipes.utils.CapacityAnalysis;
import com.feather.common.json.JSONObject;
import com.feather.common.json.JSONObject.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 功能描述：横断面分析
 *
 * @Author: fang xinliang
 * @Date: 2020/7/15 10:52
 */
@Service
public class TraSectionAnalysisService {

    @Autowired
    private CapacityAnalysis capacityAnalysis;

    /**
     *  横断面分析
     * @param lineType
     * @param lineStr
     * @return
     */
    public List DoTraSectionAnalysis(String lineType,String lineStr){
        // 查询相交的线
        List<Map<String,Object>> resultList  = capacityAnalysis.doQuery(lineType,lineStr);
        if(resultList == null || resultList.size() == 0 ) {
            return null;
        }
        // 原始带高程的
        JSONArray lineOrigneArray = JSONObject.parse(lineStr).getJSONArray("coordinates");;
        // 地图绘制线坐标，转换平面坐标
        JSONObject lineObj = capacityAnalysis.DoProject(lineStr);
        if(lineObj == null){
            return  null;
        }
        // 分析结果
        List<Map<String,Object>> dataList = new ArrayList<>();
        JSONArray  lineArray = lineObj.getJSONArray("coordinates");
        for(int i=1;i<lineArray.size();i++) {
            //List<CustomPoint> coordList = new ArrayList<CustomPoint>();
            // 线段
            Coord c1 = new Coord(JSONObject.toJSONArray((Collection<Object>)lineArray.get(i - 1)).getJSONdouble(0), JSONObject.toJSONArray((Collection<Object>)lineArray.get(i - 1)).getJSONdouble(1));
            Coord c2 = new Coord(JSONObject.toJSONArray((Collection<Object>)lineArray.get(i)).getJSONdouble(0), JSONObject.toJSONArray((Collection<Object>)lineArray.get(i)).getJSONdouble(1));
            //  coordList.add(c1);
            // coordList.add(c2);
            //高程
            double heightStart = JSONObject.toJSONArray((Collection<Object>)lineOrigneArray.get(i - 1)).getJSONdouble(2);
            double heightEnd = JSONObject.toJSONArray((Collection<Object>)lineOrigneArray.get(i)).getJSONdouble(2);

            for(int j=0;j<resultList.size();j++) {
                Map rmap = resultList.get(j);
                String name = rmap.get("name").toString();
                String typeName = rmap.get("typeName").toString();
                JSONArray features = (JSONArray) (rmap.get("firstResult"));
                for (int k = 0; k < features.size(); k++) {
                    JSONObject feature = features.getJSONObject(k);

                    JSONArray lineArrayR = feature.getJSONObject("geometry").getJSONArray("coordinates");
                    // 坐标转换
                    List<CustomPoint> coordListR = new ArrayList<CustomPoint>();
                    // 线段
                    Coord c3 = new Coord(JSONObject.toJSONArray((Collection<Object>)lineArrayR.get(0)).getJSONdouble(0), JSONObject.toJSONArray((Collection<Object>)lineArrayR.get(0)).getJSONdouble(1));
                    Coord c4 = new Coord(JSONObject.toJSONArray((Collection<Object>)lineArrayR.get(1)).getJSONdouble(0), JSONObject.toJSONArray((Collection<Object>)lineArrayR.get(1)).getJSONdouble(1));
                    //coordListR.add(c3);
                    // coordListR.add(c4);
                    //起点高程
                    double heightStartR = feature.getJSONObject("properties").getDouble("START_H");
                    // 终点高程
                    double heightEndR = feature.getJSONObject("properties").getDouble("END_H");
                    // 起点埋深
                    double s_deep  = feature.getJSONObject("properties").getDouble("S_DEEP");
                    // 终点埋深
                    double e_deep  = feature.getJSONObject("properties").getDouble("E_DEEP");

                    // "D_S": "500X400",管径规格
                    String d_s = feature.getJSONObject("properties").getString("D_S");
                    // 埋设方式, 直埋，其他（很多种）
                    String d_type = feature.getJSONObject("properties").getString("D_TYPE");
                    // 水平半径
                    double h_r = CapacityAnalysis.GetPipeRadius(d_s, true);
                    // 垂直半径
                    double v_r = CapacityAnalysis.GetPipeRadius(d_s, false);
                    // 求交点坐标,投影到平面的坐标
                    Coord crossPoint = CapacityAnalysis.GetCrossPoint(c1, c2, c3, c4, true);
                    crossPoint =  Cal_Cross_H(c3,c4,heightStartR,heightEndR,s_deep,e_deep,crossPoint);
                    Map smap = new HashMap();
                    smap.put("name",name);
                    smap.put("typeName",typeName);
                    // 交点坐标
                    double[] fromto = { crossPoint.X, crossPoint.Y};
                    JSONObject  json = new JSONObject();
                    json.put("coordinates",fromto);
                    json.put("type","Point");
                    JSONObject pointObj = capacityAnalysis.DoProject("","",json.toString());
                    if ( pointObj != null ){
                        JSONObject  pointJSON = new JSONObject();
                        pointJSON.put("x", String.format("%.8f",JSONObject.toJSONArray((Collection<Object>)pointObj.get("coordinates")).getJSONdouble(0)));
                        pointJSON.put("y",String.format("%.7f",JSONObject.toJSONArray((Collection<Object>)pointObj.get("coordinates")).getJSONdouble(1)));
                        pointJSON.put("z",String.format("%.3f",crossPoint.getZ()));
                        pointJSON.put("m",String.format("%.3f",crossPoint.getM()));
                        smap.put("crossPoint",pointJSON );
                    }
                    // 原始相交的线
                    JSONObject geo_wgs84 = capacityAnalysis.DoProject("","",feature.getJSONObject("geometry").toString());
                    if(geo_wgs84 != null){
                        feature.put("geometry",geo_wgs84);
                        smap.put("feature",feature );
                        dataList.add(smap);
                    }
                }
            }
        }
        return  dataList;
    }

    /**
     *  计算交点坐标的高程
     * @param sec_sPoint
     * @param sec_ePoint
     * @param start_h
     * @param end_h
     * @param start_m
     * @param end_m
     * @param coodCross
     */
    public Coord Cal_Cross_H(Coord sec_sPoint,Coord sec_ePoint,double start_h,double end_h,double start_m,double end_m,Coord coodCross){
        double x3 = coodCross.X, y3 = coodCross.Y, z3 = 0,m3 = 0 ;
        double x1 = sec_sPoint.X, y1 = sec_sPoint.Y, z1 = start_h, m1 = start_m;
        double x2 = sec_ePoint.X, y2 = sec_ePoint.Y, z2 = end_h, m2 = end_m;
        // 利用相识三角形 原理
        double s1 = Math.sqrt((x1 - x3) * (x1 - x3) + (y1 - y3) * (y1 - y3));
        double s = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        double k = s1 / s;
        z3 = z1 + k * (z2 - z1);
        m3 = m1 + k * (m2 - m1);

     /*   Map map = new HashMap();
        map.put("x",x3);
        map.put("y",y3);
        map.put("z",z3);
        map.put("m",m3);
*/
        Coord  coord = new Coord();
        coord.setX(x3);
        coord.setY(y3);
        coord.setZ(z3);
        coord.setM(m3);
        return coord;
    }
}
