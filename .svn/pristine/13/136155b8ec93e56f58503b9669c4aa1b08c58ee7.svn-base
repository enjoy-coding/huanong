package com.feather.aupipes.utils;



import com.feather.aupipes.config.CapacityConfig;
import com.feather.aupipes.config.ConfigPipe;
import com.feather.aupipes.domain.custom.Coord;
import com.feather.aupipes.domain.custom.CustomPoint;
import com.feather.common.json.JSONObject;
import com.feather.common.json.JSONObject.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 功能描述：排管分析
 *
 * @Author: fang xinliang
 * @Date: 2020/2/11 18:29
 */
@Component
public class CapacityAnalysis {
    @Autowired
    private ConfigPipe configPipe;
    @Autowired
    private CapacityConfig capacityConfig;

   // private  PipelineStandard pipelineStandard;
    @Autowired
    private  PineLineStandardDistance pineLineStandardDistance;
    /**
     *  缓冲分析
     *
     * @param buff
     * @param geometry
     */
   public String doBuffer(double buff,String geometry){
       String query =  configPipe.getBufferUrl();
       String queryParam = configPipe.getBufferUrlParam().replace("radius=","radius="+buff) +geometry;

       //radius=
       String queryFeatures = FeatureServer.DoPost2GIS(query, queryParam);
       JSONObject objResults = JSONObject.parse(queryFeatures);
       String resultCode = objResults.getString("resultcode");
       if ("success".equalsIgnoreCase(resultCode)&&!objResults.getJSONObject("result").isEmpty()) {
           JSONObject features = objResults.getJSONObject("result");
           return features.toString();
       }else {
           return "";
       }
   }
    /**
     *  空间查询, 指定图层
     * @param geometry
     * @return
     */
    public List<Map<String,Object>> doQuery(String lineType,String geometry){
        List<Map<String,Object>> resultList =new ArrayList<Map<String,Object>>();
        List<Map<String, Object>> list = capacityConfig.getTemplates();
        for(Map map: list) {
            if(lineType.toLowerCase().indexOf(map.get("typeName").toString().toLowerCase()) > -1 ){
                StringBuilder sb =new StringBuilder();
                sb.append("layerId="+map.get("layerId")+"&");
                sb.append("startindex="+map.get("startindex")+"&");
                sb.append("reqcount="+map.get("reqcount")+"&");
                sb.append("geoSRS="+map.get("geoSRS")+"&");
                sb.append("outSRS="+map.get("outSRS")+"&");
                sb.append("geometry="+geometry+"&");
                sb.append("fields="+map.get("fields")+"&");
                sb.append("where="+map.get("where")+"&");
                sb.append("version="+map.get("version"));

                String query =  map.get("url").toString();
                String queryFeatures = FeatureServer.DoPost2GIS(query, sb.toString());

                JSONObject objResults = JSONObject.parse(queryFeatures);
                String resultCode = objResults.getString("resultcode");
                if ("success".equalsIgnoreCase(resultCode)&&!objResults.getJSONObject("result").isEmpty()) {
                    JSONObject resultObject = objResults.getJSONObject("result");
                    JSONArray features = resultObject.getJSONArray("features");
                    Map mapR =new HashMap();
                    mapR.put("name",map.get("name"));
                    mapR.put("typeName",map.get("typeName"));
                    mapR.put("firstResult",features);
                    resultList.add(mapR);
                }
            }
        }
        return  resultList;
    }
    /**
     *  空间查询
     * @param geometry
     * @return
     */
   public List<Map<String,Object>> doQuery(String geometry){
       List<Map<String,Object>> resultList =new ArrayList<Map<String,Object>>();
       List<Map<String, Object>> list = capacityConfig.getTemplates();
       for(Map map: list) {

           StringBuilder sb =new StringBuilder();
           sb.append("layerId="+map.get("layerId")+"&");
           sb.append("startindex="+map.get("startindex")+"&");
           sb.append("reqcount="+map.get("reqcount")+"&");
           sb.append("geoSRS="+map.get("geoSRS")+"&");
           sb.append("outSRS="+map.get("outSRS")+"&");
           sb.append("geometry="+geometry+"&");
           sb.append("fields="+map.get("fields")+"&");
           sb.append("where="+map.get("where")+"&");
           sb.append("version="+map.get("version"));

           String query =  map.get("url").toString();
           String queryFeatures = FeatureServer.DoPost2GIS(query, sb.toString());

           JSONObject objResults = JSONObject.parse(queryFeatures);
           String resultCode = objResults.getString("resultcode");
           if ("success".equalsIgnoreCase(resultCode)&&!objResults.getJSONObject("result").isEmpty()) {
               JSONObject resultObject = objResults.getJSONObject("result");
               JSONArray features = resultObject.getJSONArray("features");
               Map mapR =new HashMap();
               mapR.put("name",map.get("name"));
               mapR.put("typeName",map.get("typeName"));
               mapR.put("firstResult",features);
               resultList.add(mapR);
           }
       }
       return  resultList;
   }

    /**
     *  转平面坐标
     * @param geometry
     * @return
     */
   public JSONObject DoProject(String geometry){

       String query =  configPipe.getProjectionUrl();
       String queryParam = configPipe.getProjectionParam() +geometry;
       String queryFeatures = FeatureServer.DoPost2GIS(query, queryParam);
       JSONObject objResults = JSONObject.parse(queryFeatures);
       String resultCode = objResults.getString("resultcode");
       if ("success".equalsIgnoreCase(resultCode)&&!objResults.getJSONObject("result").isEmpty()) {
           return objResults.getJSONObject("result");
       }else {
           return null;
       }

   }

    /**
     *  2000 投影坐标 转  wgs84
     * @param fromSRS
     * @param toSRS
     * @param geometry
     * @return
     */
    public JSONObject DoProject(String fromSRS ,String toSRS, String geometry){

        String query =  configPipe.getProjectionUrl();
        String queryParam = "geoSRS=EPSG:4526&outSRS=EPSG:4326&version=2.0&data=" +geometry;
        String queryFeatures = FeatureServer.DoPost2GIS(query, queryParam);
        JSONObject objResults = JSONObject.parse(queryFeatures);
        String resultCode = objResults.getString("resultcode");
        if ("success".equalsIgnoreCase(resultCode)&&!objResults.getJSONObject("result").isEmpty()) {
            return objResults.getJSONObject("result");
        }else {
            return null;
        }

    }
    /**
     *  计算分析管线之间的距离,判定是否合格
     * @param lineType
     * @param layerOutType
     * @param radius
     * @param buff
     * @param depth
     * @param lineStr
     * @param firstResult
     * @return
     */
   public List<Map<String,Object>> GetDistance(String lineType,String layerOutType,String radius,double buff,double depth,String pressure,String lineStr,List<Map<String,Object>> firstResult){

       List<Map<String,Object>> resultList = new ArrayList<>();
       // 原始带高程的
       JSONArray lineOrigneArray = JSONObject.parse(lineStr).getJSONArray("coordinates");;
       Object on = lineOrigneArray.get(1);
       JSONArray objects = JSONObject.toJSONArray((Collection<Object>) on);
       Double o = objects.getJSONdouble(1);
       // 转换平面坐标
       JSONObject lineObj = DoProject(lineStr);
       if(lineObj==null){
           return  null;
       }
       JSONArray  lineArray = lineObj.getJSONArray("coordinates");
       for(int i=1;i<lineArray.size();i++){
           List<CustomPoint> coordList = new ArrayList<CustomPoint>() ;
          // 线段
           CustomPoint c1 =new CustomPoint(JSONObject.toJSONArray((Collection<Object>) lineArray.get(i-1)).getJSONdouble(0),JSONObject.toJSONArray((Collection<Object>) lineArray.get(i-1)).getJSONdouble(1));
           CustomPoint c2 =new CustomPoint(JSONObject.toJSONArray((Collection<Object>) lineArray.get(i)).getJSONdouble(0),JSONObject.toJSONArray((Collection<Object>) lineArray.get(i)).getJSONdouble(1));
           coordList.add(c1);
           coordList.add(c2);
           //高程
           double heightStart = JSONObject.toJSONArray((Collection<Object>) lineOrigneArray.get(i-1)).getJSONdouble(0);
           double heightEnd = JSONObject.toJSONArray((Collection<Object>) lineOrigneArray.get(i)).getJSONdouble(0);

           for(int j=0;j<firstResult.size();j++){
               Map  rmap = firstResult.get(j);
               String name = rmap.get("name").toString();
               String typeName = rmap.get("typeName").toString();
               JSONArray features = (JSONArray)(rmap.get("firstResult"));
               for(int k=0;k<features.size();k++){
                   JSONObject feature = features.getJSONObject(k);

                   JSONArray  lineArrayR = feature.getJSONObject("geometry").getJSONArray("coordinates");
                   // 坐标转换
                /*   JSONObject  lineObjR =  DoProject(feature.getJSONObject("geometry").toString()) ;
                   if(lineObjR==null){
                       continue;
                   }
                   JSONArray  lineArrayR =lineObjR.getJSONArray("coordinates");
                */
                   List<CustomPoint> coordListR = new ArrayList<CustomPoint>() ;
                   // 线段
                   CustomPoint c3 =new CustomPoint(JSONObject.toJSONArray((Collection<Object>) lineArrayR.get(0)).getJSONdouble(0),JSONObject.toJSONArray((Collection<Object>) lineArrayR.get(0)).getJSONdouble(1));

                   CustomPoint c4 =new CustomPoint(JSONObject.toJSONArray((Collection<Object>) lineArrayR.get(1)).getJSONdouble(0),JSONObject.toJSONArray((Collection<Object>) lineArrayR.get(1)).getJSONdouble(1));
                   coordListR.add(c3);
                   coordListR.add(c4);
                   //起点高程
                   double heightStartR = feature.getJSONObject("properties").getDouble("START_H");
                   // 终点高程
                   double heightEndR = feature.getJSONObject("properties").getDouble("END_H");
                   // "D_S": "500X400",管径规格
                   String d_s = feature.getJSONObject("properties").getString("D_S");
                   // 埋设方式, 直埋，其他（很多种）
                   String d_type = feature.getJSONObject("properties").getString("D_TYPE");
                   // 水平半径
                   double h_r = GetPipeRadius(  d_s ,true);
                   // 垂直半径
                   double v_r = GetPipeRadius(  d_s ,false);

                   double radiusH = GetPipeRadius(  radius ,true);
                   double radiusV = GetPipeRadius(  radius ,false);
                   // 水平距离
                   Map h_map = GetMinDistance(coordList,coordListR,h_r+radiusH);
                   double h_distance  = Double.parseDouble( h_map.get("distance").toString() );
                   // 垂直距离
                   double v_distance = GetMinVerticalDistance(  coordList,   heightStart,   heightEnd,  coordListR,   heightStartR,   heightEndR);
                   // 水平标准距离

                   double pressureStart = 0.0, pressureEnd = 0.0, radiusStart = radiusV, radiusEnd = radiusV;

                   if("低".equals(  pressure)){
                       pressureStart=0.05;
                       pressureEnd=0.05;
                   }else if("中".equals(pressure)){
                       pressureStart=0.8;
                       pressureEnd=0.8;
                   }else if("高".equals(pressure)){
                       pressureStart=1.6;
                       pressureEnd=1.6;
                   }

                   double pressureStart2=0.0, pressureEnd2=0.0, radiusStart2=h_r, radiusEnd2=h_r;
                   // 燃气压力
                   String PRESSURE2 = feature.getJSONObject("properties").getString("PRESSURE");
                   if("低".equals(PRESSURE2)){
                       pressureStart2=0.05;
                       pressureEnd2=0.05;
                   }else if("中".equals(PRESSURE2)){
                       pressureStart2=0.8;
                       pressureEnd2=0.8;
                   }else if("高".equals(PRESSURE2)){
                       pressureStart2=1.6;
                       pressureEnd2=1.6;
                   }
                   double h_s = pineLineStandardDistance.GetHorizontalSpacingStandard(lineType,layerOutType,pressureStart,  pressureEnd,  radiusStart,  radiusEnd,typeName,d_type,pressureStart2,  pressureEnd2,  radiusStart2,  radiusEnd2);
                   // 垂直标准距离

                   double v_s = pineLineStandardDistance.GetVerticalSpacingStandard(lineType,layerOutType,typeName,d_type);
                   // 判断距离
                  // DecimalFormat df = new DecimalFormat("#.000");

                   Map smap = new HashMap();
                   smap.put("name",name);
                   smap.put("typeName",typeName);
                   smap.put("h_distance",String.format("%.3f",h_distance)   );




                   smap.put("v_distance",String.format("%.3f",v_distance)   );
                   smap.put("h_s_distance",h_s);
                   smap.put("v_s_distance",v_s);

                   if(h_distance<h_s ){
                       smap.put("h_msg","水平标准距离不符合规范");
                       double[][] fromto = {
                               { ((Coord)h_map.get("from")).X, ((Coord)h_map.get("from")).Y},
                               { ((Coord)h_map.get("to")).X, ((Coord)h_map.get("to")).Y}};
                       //{"coordinates":[[114.34677421,30.4759118],[114.3472178,30.47591161]],"type":"LineString"}
                       JSONObject  json = new JSONObject();
                       json.put("coordinates",fromto);
                       json.put("type","LineString");
                       JSONObject fromtoJson =  DoProject("","",json.toString());
                       smap.put("h_fromto",fromtoJson.get("coordinates"));
                   }
                   if( v_distance<v_s){
                       smap.put("v_msg","垂直标准距离不符合规范");
                   }
                   if(smap.containsKey("h_msg")||smap.containsKey("v_msg")){
                       JSONObject geo_wgs84 = DoProject("","",feature.getJSONObject("geometry").toString());
                       if(geo_wgs84!=null){
                           feature.put("geometry",geo_wgs84);
                           smap.put("data",feature );
                       }
                       resultList.add(smap);
                   }
               }
           }
       }
       return  resultList;
   }
    /**
     *  获取水平两直线最短距离
     * @param coordList  坐标集合
     * @param coordListRe 坐标集合
     * @param pipeRadiusSub  管径减去值
     * @return
     */
    public static Map GetMinDistance(List<CustomPoint> coordList, List<CustomPoint> coordListRe, double pipeRadiusSub)
    {
        double distance = 0, distanceFromRe = 0, distanceToRe = 0, distanceFrom = 0, distanceTo = 0;
        Coord coordFrom = new Coord(), coordTo = new Coord(), coordFromRe = new Coord(), coordToRe = new Coord();


        BigDecimal b_x = new BigDecimal(coordList.get(0).getX());
        coordFrom.X = b_x.setScale(8, BigDecimal.ROUND_HALF_DOWN).doubleValue();
        BigDecimal b_y = new BigDecimal(coordList.get(0).getY());
        coordFrom.Y = b_y.setScale(8, BigDecimal.ROUND_HALF_DOWN).doubleValue();


        BigDecimal b_x1 = new BigDecimal(coordList.get(coordList.size()-1).getX());
        coordTo.X = b_x1.setScale(8, BigDecimal.ROUND_HALF_DOWN).doubleValue();
        BigDecimal b_y1 = new BigDecimal(coordList.get(coordList.size()-1).getY());
        coordTo.Y = b_y1.setScale(8, BigDecimal.ROUND_HALF_DOWN).doubleValue();


        BigDecimal b_x2 = new BigDecimal(coordListRe.get(0).getX());
        coordFromRe.X = b_x2.setScale(8, BigDecimal.ROUND_HALF_DOWN).doubleValue();
        BigDecimal b_y2 = new BigDecimal(coordListRe.get(0).getY());
        coordFromRe.Y = b_y2.setScale(8, BigDecimal.ROUND_HALF_DOWN).doubleValue();


        BigDecimal b_x3 = new BigDecimal(coordListRe.get(coordListRe.size()-1).getX());
        coordToRe.X = b_x3.setScale(8, BigDecimal.ROUND_HALF_DOWN).doubleValue();
        BigDecimal b_y3 = new BigDecimal(coordListRe.get(coordListRe.size()-1).getY());
        coordToRe.Y = b_y3.setScale(8, BigDecimal.ROUND_HALF_DOWN).doubleValue();
        //获取距离
        Map mapFromRe = new HashMap();
        Map mapToRe = new HashMap();
        Map mapFrom = new HashMap();
        Map mapTo = new HashMap();
        mapFromRe = GetMinDistance(coordFromRe, coordToRe, coordFrom);
        mapToRe = GetMinDistance(coordFromRe, coordToRe, coordTo);
        mapFrom = GetMinDistance(coordFrom, coordTo, coordFromRe);
        mapTo = GetMinDistance(coordFrom, coordTo, coordToRe);

        distanceFromRe = Double.parseDouble(mapFromRe.get("distance").toString());
        distanceToRe = Double.parseDouble(mapToRe.get("distance").toString());
        distanceFrom = Double.parseDouble(mapFrom.get("distance").toString());
        distanceTo = Double.parseDouble(mapTo.get("distance").toString());
        //比较距离，获取最小值
        distance = distanceFromRe;
        Map map = mapFromRe;
        if (distanceToRe < distance){
            distance = distanceToRe;
            map=mapToRe;
        }
        if (distanceFrom < distance) {
            distance = distanceFrom;
            map= mapFrom;
        }
        if (distanceTo < distance) {
            distance = distanceTo;
            map = mapTo;
        }
        distance = Math.abs(distance - pipeRadiusSub);
        map.put("distance",distance);
        return  map;
       // return distance;
    }

    /**
     *  获取点到线段的最短距离
     * @param coordFrom 线段起点坐标
     * @param coordTo 线段终点坐标
     * @param coordRe  参考点坐标
     * @return
     */
    public static Map<String, Object> GetMinDistance(Coord coordFrom, Coord coordTo, Coord coordRe)
    {
        double distance = 0, a, b, c;
        Map<String,Object> map = new HashMap<>();
        // 线段的长度
        a = LineDistance(coordFrom.X, coordFrom.Y, coordTo.X, coordTo.Y);

        // coordRefFrom到点的距离
        b = LineDistance(coordFrom.X, coordFrom.Y, coordRe.X, coordRe.Y);

        //coordRefTo到点的距离
        c = LineDistance(coordTo.X, coordTo.Y, coordRe.X, coordRe.Y);
        if (c + b == a)
        {
            // 点在线段上
            distance = 0;
            map.put("from",coordRe);
            map.put("to",coordFrom);
            map.put("distance",distance);
           // return distance;
            return  map;
        }
        if (c * c >= a * a + b * b)
        {
            // 组成直角三角形或钝角三角形，投影在point1延长线上，
            distance = b;
            map.put("from",coordRe);
            map.put("to",coordFrom);
            map.put("distance",distance);
            return  map;
           // return distance;
        }
        if (b * b >= a * a + c * c)
        {
            // 组成直角三角形或钝角三角形，投影在point2延长线上，
            distance = c;
            map.put("from",coordRe);
            map.put("to",coordTo);
            map.put("distance",distance);
           // return distance;
            return  map;
        }

        // 组成锐角三角形，则求三角形的高

        // 半周长
        double p = (a + b + c) / 2;

        // 海伦公式求面积
        double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));

        // 返回点到线的距离（利用三角形面积公式求高）
        distance =  2 * s / a;


        // 斜率
        double A = (coordFrom.Y-coordTo.Y)/(coordFrom.X-coordTo.X);
        //
        double B = coordFrom.Y-A*coordFrom.X;
        double m = coordRe.X+A*coordRe.Y;
        Coord coordP = new Coord();
        coordP.X = (m-A*B)/(A*A+1);
        coordP.Y = A*coordP.X+B;


        map.put("from",coordRe);
        map.put("to",coordP);
        map.put("distance",distance);
       // return distance;
        return  map;
    }
    /**
     *  获取垂直间距
     * @param coordList  坐标集合
     * @param heightStart  起点高程
     * @param heightEnd  终点高程
     * @param coordListRe  坐标集合
     * @param heightStartRe  要素起点高程
     * @param heightEndRe  要素终点高程
     * @return
     */
    private static double GetMinVerticalDistance(List<CustomPoint> coordList, double heightStart, double heightEnd, List<CustomPoint> coordListRe, double heightStartRe, double heightEndRe)
    {
        double minVerticalDistance = 0;
        Coord coordFrom = new Coord(), coordTo = new Coord(), coordFromRe = new Coord(), coordToRe = new Coord(), coordCross = new Coord();

        BigDecimal b_x = new BigDecimal(coordList.get(0).getX());
        coordFrom.X = b_x.setScale(8, BigDecimal.ROUND_HALF_DOWN).doubleValue();
        BigDecimal b_y = new BigDecimal(coordList.get(0).getY());
        coordFrom.Y = b_y.setScale(8, BigDecimal.ROUND_HALF_DOWN).doubleValue();

        BigDecimal b_x1 = new BigDecimal(coordList.get(coordList.size()-1).getX());
        coordTo.X = b_x1.setScale(8, BigDecimal.ROUND_HALF_DOWN).doubleValue();
        BigDecimal b_y1 = new BigDecimal(coordList.get(coordList.size()-1).getY());
        coordTo.Y = b_y1.setScale(8, BigDecimal.ROUND_HALF_DOWN).doubleValue();

        BigDecimal b_x2 = new BigDecimal(coordListRe.get(0).getX());
        coordFromRe.X = b_x2.setScale(8, BigDecimal.ROUND_HALF_DOWN).doubleValue();
        BigDecimal b_y2 = new BigDecimal(coordListRe.get(0).getY());
        coordFromRe.Y = b_y2.setScale(8, BigDecimal.ROUND_HALF_DOWN).doubleValue();

        BigDecimal b_x3 = new BigDecimal(coordListRe.get(coordListRe.size()-1).getX());
        coordToRe.X = b_x3.setScale(8, BigDecimal.ROUND_HALF_DOWN).doubleValue();
        BigDecimal b_y3 = new BigDecimal(coordListRe.get(coordListRe.size()-1).getY());
        coordToRe.Y = b_y3.setScale(8, BigDecimal.ROUND_HALF_DOWN).doubleValue();
        //获取交点
        coordCross = GetCrossPoint(coordFrom, coordTo, coordFromRe, coordToRe, true);

        //如果不平行
        if (coordCross == null) {
            minVerticalDistance = heightStart - heightStartRe;
        } else
        {
            double crossHeight = 0;
            double tanA = Math.abs(heightStart - heightEnd) / Math.abs(coordTo.X - coordFrom.X);
            if (coordFrom.X <= coordTo.X && heightStart <= heightEnd){
                crossHeight = tanA * (coordCross.X - coordFrom.X) + heightStart;
            }
            else if (coordFrom.X >= coordTo.X && heightStart <= heightEnd) {
                crossHeight = tanA * (coordFrom.X - coordCross.X) + heightStart;
            } else if (coordFrom.X >= coordTo.X && heightStart >= heightEnd){
                crossHeight = tanA * (coordFrom.X - coordCross.X) + heightEnd;
            }
            else if (coordFrom.X <= coordTo.X && heightStart >= heightEnd) {
                crossHeight = tanA * (coordCross.X - coordFrom.X) + heightEnd;
            }

            double crossHeightRe = 0;
            double tanARe = Math.abs(heightStartRe - heightEndRe) / Math.abs(coordToRe.X - coordFromRe.X);
            if (coordFromRe.X <= coordToRe.X && heightStartRe <= heightEndRe){
                crossHeightRe = tanARe * (coordCross.X - coordFromRe.X) + heightStartRe;
            }
            else if (coordFromRe.X >= coordToRe.X && heightStartRe <= heightEndRe) {
                crossHeightRe = tanARe * (coordFromRe.X - coordCross.X) + heightStartRe;
            }
            else if (coordFromRe.X >= coordToRe.X && heightStartRe >= heightEndRe){
                crossHeightRe = tanARe * (coordFromRe.X - coordCross.X) + heightEndRe;
            }
            else if (coordFromRe.X <= coordToRe.X && heightStartRe >= heightEndRe) {
                crossHeightRe = tanARe * (coordCross.X - coordFromRe.X) + heightEndRe;
            }

            minVerticalDistance = crossHeight - crossHeightRe;
        }

        return Math.abs(minVerticalDistance);
    }


    /**
     *  验证是否忽略
     * @param id 管线id
     * @param idRe  参考管线id
     * @param idS  管线起点id
     * @param idE  管线终点id
     * @param idSRe  参考管线起点id
     * @param idERe  参考管线终点id
     * @return
     */
    private static boolean CheckIgnore(String id, String idRe, String idS, String idE, String idSRe, String idERe)
    {
        boolean isIgnored = false;
        if (id.equals(idRe) || idS.equals(idSRe) || idS.equals(idERe) || idE.equals(idSRe) || idE.equals(idERe)) {
            isIgnored = true;
        }
        return isIgnored;
    }
    /**
     *  判断参考直线是否与直线相交
     * @param coordFrom  坐标起点
     * @param coordTo  坐标终点
     * @param coordFromRe   参考坐标起点
     * @param coordToRe 参考坐标终点
     * @return
     */
    private static boolean CheckCrose(Coord coordFrom, Coord coordTo, Coord coordFromRe, Coord coordToRe)
    {
        Coord coordV1 = new Coord(), coordV2 = new Coord(), coordV3 = new Coord();

        //赋值
        coordV1.X = coordFrom.X - coordToRe.X;
        coordV1.Y = coordFrom.Y - coordToRe.Y;
        coordV2.X = coordTo.X - coordToRe.X;
        coordV2.Y = coordTo.Y - coordToRe.Y;
        coordV3.X = coordFromRe.X - coordToRe.X;
        coordV3.Y = coordFromRe.Y - coordToRe.Y;

        return (CrossMul(coordV1, coordV3) * CrossMul(coordV2, coordV3) <= 0);
    }

    /**
     * 计算两个向量的叉乘
     * @param coord1
     * @param coord2
     * @return
     */
    private static double CrossMul(Coord coord1, Coord coord2)
    {
        return coord1.X * coord2.Y - coord1.Y * coord2.X;
    }


    /**
     * 判断直线是否相交
     * @param coordList 坐标集合
     * @param coordListRe  参考坐标集合
     * @return
     */
    public static boolean CheckTwoLineCrose(List<CustomPoint> coordList, List<CustomPoint> coordListRe)
    {
        Coord coordFrom = new Coord(), coordTo = new Coord(), coordFromRe = new Coord(), coordToRe = new Coord();

        BigDecimal b_x = new BigDecimal(coordList.get(0).getX());
        coordFrom.X = b_x.setScale(8, BigDecimal.ROUND_HALF_DOWN).doubleValue();
        BigDecimal b_y = new BigDecimal(coordList.get(0).getY());
        coordFrom.Y = b_y.setScale(8, BigDecimal.ROUND_HALF_DOWN).doubleValue();

        BigDecimal b_x1 = new BigDecimal(coordList.get(coordList.size()-1).getX());
        coordTo.X = b_x1.setScale(8, BigDecimal.ROUND_HALF_DOWN).doubleValue();
        BigDecimal b_y1 = new BigDecimal(coordList.get(coordList.size()-1).getY());
        coordTo.Y = b_y1.setScale(8, BigDecimal.ROUND_HALF_DOWN).doubleValue();

        BigDecimal b_x2 = new BigDecimal(coordListRe.get(0).getX());
        coordFromRe.X = b_x2.setScale(8, BigDecimal.ROUND_HALF_DOWN).doubleValue();
        BigDecimal b_y2 = new BigDecimal(coordListRe.get(0).getY());
        coordFromRe.Y = b_y2.setScale(8, BigDecimal.ROUND_HALF_DOWN).doubleValue();

        BigDecimal b_x3 = new BigDecimal(coordListRe.get(coordListRe.size()-1).getX());
        coordToRe.X = b_x3.setScale(8, BigDecimal.ROUND_HALF_DOWN).doubleValue();
        BigDecimal b_y3 = new BigDecimal(coordListRe.get(coordListRe.size()-1).getY());
        coordToRe.Y = b_y3.setScale(8, BigDecimal.ROUND_HALF_DOWN).doubleValue();

        return CheckCrose(coordFrom, coordTo, coordFromRe, coordToRe) && CheckCrose(coordFromRe, coordToRe, coordFrom, coordTo);
    }



    /**
     * 获取管径半径
     * @param pipeRadiusString 管径数据
     * @param isH  是否水平
     * @return
     */
    public static double GetPipeRadius(String pipeRadiusString, boolean isH)
    {
        double pipeRadius = 0;
        if (StringUtils.isNotBlank(pipeRadiusString))
        {
            String matchString = "^\\d+$";

            //如果是有宽X高
            if (pipeRadiusString.toUpperCase().contains("X"))
            {
                String[] splitString = pipeRadiusString.toUpperCase().split("X");
                if (splitString.length == 2)
                {
                    String width = splitString[0];
                    String height = splitString[1];

                    //验证
                    if (Pattern.matches(matchString,width ) && Pattern.matches(matchString, height ))
                    {
                        //转换
                        if (isH)
                        {
                            pipeRadius= Double.parseDouble(width);
                            pipeRadius = pipeRadius / 2000;
                        }
                        else
                        {
                            pipeRadius= Double.parseDouble(height);
                            pipeRadius = pipeRadius / 2000;
                        }
                    }
                }
            }
            else
            {
                //验证
                if (Pattern.matches(pipeRadiusString, matchString))
                {
                    //转换
                    pipeRadius= Double.parseDouble(pipeRadiusString);
                    pipeRadius = pipeRadius / 2000;
                }
            }
        }
        return pipeRadius;
    }


    /**
     * 获取两直线交点坐标
     * @param coordFrom
     * @param coordTo
     * @param coordFromRe
     * @param coordToRe
     * @param isFormat
     * @return
     */
    public static Coord GetCrossPoint(Coord coordFrom, Coord coordTo, Coord coordFromRe, Coord coordToRe, boolean isFormat)
    {
        Coord coord = new Coord();
        double x = 0, y = 0;

        //求系数
        double a, b, c;
        if (coordFrom.X == coordTo.X)
        {
            a = 1;
            b = 0;
            c = -(coordFrom.X);
        }
        else
        {
            a = -(coordTo.Y - coordFrom.Y) / (coordTo.X - coordFrom.X);
            b = 1;
            c = coordFrom.X * (coordTo.Y - coordFrom.Y) / (coordTo.X - coordFrom.X) - coordFrom.Y;
        }
        double aRe, bRe, cRe;
        if (coordFromRe.X == coordToRe.X)
        {
            aRe = 1;
            bRe = 0;
            cRe = -(coordFromRe.X);
        }
        else
        {
            aRe = -(coordToRe.Y - coordFromRe.Y) / (coordToRe.X - coordFromRe.X);
            bRe = 1;
            cRe = coordFromRe.X * (coordToRe.Y - coordFromRe.Y) / (coordToRe.X - coordFromRe.X) - coordFromRe.Y;
        }

        //两直线都无斜率，平行
        if (b == 0 && bRe == 0) {
            return null;
        }
        else if (b == 0)
        {
            //其中一条无斜率
            x = c;
            y = -(aRe * x + cRe) / bRe;
        }
        else if (bRe == 0)
        {
            //其中一条无斜率
            x = -cRe;
            y = -(a * x + c) / b;
        }
        else  //两条都有斜率，所以bRef 都自动为1
        {
            if ((a / b) == (aRe / bRe)) {
                return null;
            }
            else
            {
                x = (c - cRe) / (aRe - a);
                y = -(a * x) - c;
            }
        }

        if (isFormat)
        {
            BigDecimal b_x = new BigDecimal(x);
            coord.X = b_x.setScale(8, BigDecimal.ROUND_HALF_DOWN).doubleValue();

            BigDecimal b_y = new BigDecimal(y);
            coord.Y = b_y.setScale(8, BigDecimal.ROUND_HALF_DOWN).doubleValue();
        }
        else
        {
            coord.X = x;
            coord.Y = y;
        }
        return coord;
    }
      /**
     * 获取两点间最短距离
     * @param x1 点1X坐标
     * @param y1  点1Y坐标
     * @param x2 点2X坐标
     * @param y2  点2Y坐标
     * @return
     */
    private static double LineDistance(double x1, double y1, double x2, double y2)
    {
        double lineLength = 0;
        lineLength = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        return lineLength;
    }
}
