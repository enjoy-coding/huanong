package com.feather.aupipes.utils;



import com.feather.aupipes.domain.query.Fw;
import com.feather.aupipes.domain.query.Line;
import com.feather.aupipes.domain.query.Pdf;
import com.feather.aupipes.domain.query.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tql
 * @description 集合转map工具类
 * @date 2019年12月18日22:20:49
 */
public class ListToMap {

    public static Map<String,Map<String, List<Line>>> listLineToMap(List<Line> lines){
        Map<String,List<Line>> smap = new HashMap<>();
        Map<String,List<Line>> emap = new HashMap<>();
        for(int i=0,len = lines.size();i < len;i++){
            List<Line> list = new ArrayList<>();
            Line line = lines.get(i);
            if (i ==0){
                list.add(line);
                smap.put(line.getS_point(),list);
                emap.put(line.getE_point(),list);
            }else{
                List<Line> stemp = smap.get(line.getS_point());
                List<Line> etemp = emap.get(line.getE_point());
                if (stemp != null){
                    stemp.add(line);
                }else{
                    stemp = new ArrayList<>();
                    stemp.add(line);
                }
                if(etemp != null){
                    etemp.add(line);
                }else{
                    etemp = new ArrayList<>();
                    etemp.add(line);
                }
                smap.put(line.getS_point(),stemp);
                emap.put(line.getE_point(),etemp);
            }
        }
        Map<String,Map<String,List<Line>>> list = new HashMap<>();
        list.put("smap",smap);
        list.put("emap",emap);
        return list;
    }

    public static Map<String,Point> listPointToMap(List<Point> points){
        Map<String,Point> map = new HashMap<>();
        for (int i = 0,len = points.size();i < len;i++){
            Point point = points.get(i);
            map.put(point.getExp_no(),point);
        }
        return map;
    }

    public static  Map<String,String> ListPdfToMap(List<Pdf> list){
        Map<String,String> map = new HashMap<>();
        for(int i=0,len = list.size();i < len;i++){
            Pdf pdf = list.get(i);
            map.put(pdf.getCxid(),pdf.getPdfid());
        }
        return map;
    }

    public static Map<String,String> ListFwToMap(List<Fw> fws){
        Map<String,String> map = new HashMap<>();
        for(int i =0,len = fws.size();i < len;i++){
            Fw fw = fws.get(i);
            map.put(fw.getFwid(),fw.getJxid());
        }
        return map;
    }



}
